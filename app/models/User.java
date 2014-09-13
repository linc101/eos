package models;
import General.Result;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import play.libs.Codec;

import transaction.DBBuilder.DataSrc;
import transaction.JDBCBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

@Entity(name = User.TABLE_NAME)
public class User extends Model implements PolicySQLGenerator{
	private static final Logger log = LoggerFactory.getLogger(User.class);
	
	public static final String TABLE_NAME = "user";
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="createTs")
	private long createTs;
	
	public String getUserName(){
		return this.userName;
	}

    public void setUserName(String userName){
        this.userName = userName;
    }
	
	public String getEmail(){
		return this.email;
	}

    public void setEmail(String email){
        this.email = email;
    }

	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		encryptPassword(password);
	}
	
	public long getCreateTs(){
		return this.createTs;
	}

    public void setCreateTs(long createTs){
        this.createTs = createTs;
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

    public long firstSave(){
        String query = "insert into " + TABLE_NAME + "(`userName`,`email`, `password`,`createTs`) values (?,?,?,?)";
        long res = dp.insert(query, this.userName, this.email, this.password, this.createTs);
        return res;
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
		String query = "select id from " + TABLE_NAME + " where userName =? ";
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
		encryptPassword(password);
		this.createTs = System.currentTimeMillis();
	}

    private  void encryptPassword(String password){
        this.password = Codec.hexMD5(password);
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

    public static long findEmailExisted(String email){
        String query = "select id from " + TABLE_NAME + " where email = ?";
        log.info("email query:" + query + " email:" + email);
        long res = dp.singleLongQuery(query, email);
        log.info("res:" + res);
        return res;
    }

    private static User doWithResult(ResultSet rs){
        try {
            if (rs.next()) {
                long id = rs.getLong(1);
                String userName = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);
                Long createTs = rs.getLong(5);
                User user = new User();
                user.setId(id);
                user.setUserName(userName);
                user.setEmail(email);
                user.setPassword(password);
                user.setCreateTs(createTs);
                return user;
            }else{
                return null;
            }
        }catch(SQLException e){
            log.warn(e.getMessage(), e);
        }
        return null;
    }
    private static final String AllProperty = " id, userName, email, password, createTs ";
    public static User finUserByName(String userName){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where userName = ?";

        return new JDBCBuilder.JDBCExecutor<User>(query, userName){
            @Override
            public User doWithResultSet(ResultSet rs) throws SQLException{
                return doWithResult(rs);
            }
        }.call();
    }

    public static User userLogin(String email, String password){
        password = Codec.hexMD5(password);
        String query = "select "+ AllProperty +" from " + TABLE_NAME + " where email = ? and password= ?";
        return new JDBCBuilder.JDBCExecutor<User>(query, email, password){
            @Override
            public User doWithResultSet(ResultSet rs) throws SQLException{
                return doWithResult(rs);
            }
        }.call();

    }

    public static User finUserById(String userId){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where id = ?";
        Long userIdL  = Long.valueOf(userId);
        return new JDBCBuilder.JDBCExecutor<User>(query, userIdL){
            @Override
            public User doWithResultSet(ResultSet rs) throws SQLException{
                return doWithResult(rs);
            }
        }.call();
    }

    @Override
    public String toString(){
        return "[username: " + this.userName+" useremail:" +this.email + "]";
    }
}
