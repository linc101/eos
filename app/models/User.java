package models;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import transaction.DBBuilder.DataSrc;

@Entity(name = User.TABLE_NAME)
public class User extends Model implements PolicySQLGenerator{
	private static final Logger log = LoggerFactory.getLogger(User.class);
	
	public static final String TABLE_NAME = "user";
	
	@Column(name="userName", unique=true)
	private String userName;
	
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="createTs")
	private long createTs;
	
	public String getUserName(){
		return this.userName;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public long getCreateTs(){
		return this.createTs;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getTableHashKey() {
		return null;
	}

	@Override
	public String getIdColumn(){
		return null;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean jdbcSave() {
		Long id = findIfExistedByUserName(this.userName);
		if(id <= 0){
			return insert();
		}else{
			return update();
		}
	}
	
	public boolean insert(){
		String query = "insert into " + TABLE_NAME + "(`userName`,`email`, `password`,`createTs`) values (?,?,?,?)";
		long res = dp.insert(query, this.userName, this.email, this.password, this.createTs);
		if(res <= 0){
			log.error("insert error userName:" + this.userName );
			return false;
		}else {
			return true;
		}
	}
	
	public boolean update(){
		String query = "update " + TABLE_NAME + " set `userName` = ? , `email` = ?, `password` = ?, `createTs` = ? where userName = ?" ;
		long res = dp.update(query, this.userName, this.email, this.password, this.createTs, this.userName);
		if(res <= 0){
			log.error("update error userName :" + this.userName);
			return false;
		}else {
			return true;
		}
	}
	
	public static long findIfExistedByUserName(String userName){
		String query = "select id from " + TABLE_NAME + " where userName = " + userName;
		return dp.singleLongQuery(query, userName);
	}
	
	@Override
	public String getIdName() {
		return "id";
	}
	
	private User(){
	}
	
	public User(String userName, String email, String password){
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.createTs = System.currentTimeMillis();
	}
	
	private static final User _instance = new User();
	
	public static DBDispatcher dp = new  DBDispatcher(DataSrc.BASIC, _instance);
	
    @Override
    public boolean equals(Object object){
    	if(!(object instanceof User)){
    		return false;
    	}
    	User user = (User)object;
    	if(user.getUserName().equals(this.userName) && user.getEmail().equals(this.email) && user.getPassword().equals(password)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    @Override
    public int hashCode(){
    	int code = 0;
    	code += this.userName.hashCode();
    	code += this.email.hashCode();
    	code += this.password.hashCode();
    	return code;
    }

    public static boolean isEmailExisted(String email){
        String query = "select id from user where email = ?";
        long res = dp.singleLongQuery(query, email);
        if(res <= 0L)return false;
        return true;
    }
}
