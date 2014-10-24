package models;
import General.Result;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
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

@JsonAutoDetect
@Entity(name = User.TABLE_NAME)
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
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

    @Column(name="picPath")
    private String picPath;

    @Column(name="briefIntroduction")
    private String briefIntroduction;

    public String getPicPath(){
        return this.picPath;
    }

    public void setPicPath(String picPath){
        this.picPath = picPath;
    }

    public String getBriefIntroduction(){
        return this.briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction){
        this.briefIntroduction = briefIntroduction;
    }

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
		this.password = password;
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
        String query = "insert into " + TABLE_NAME + "(`userName`,`email`, `password`,`createTs`, picPath, briefIntroduction) values (?,?,?,?,?,?)";
        long res = dp.insert(query, this.userName, this.email, this.password, this.createTs, this.picPath, this.briefIntroduction);
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
		String query = "insert into " + TABLE_NAME + "(`userName`,`email`, `password`,`createTs`, picPath,briefIntroduction) values (?,?,?,?,?,?)";
		long res = dp.insert(query, this.userName, this.email, this.password, this.createTs, this.picPath, this.briefIntroduction);
		if(res <= 0){
			log.error("insert error userName:" + this.userName );
			return false;
		}else {
			return true;
		}
	}
	
	public boolean update(){
		String query = "update " + TABLE_NAME + " set `userName` = ? , `email` = ?, `password` = ?, `createTs` = ?, picPath = ?, briefIntroduction = ? where userName = ?" ;
		long res = dp.update(query, this.userName, this.email, this.password, this.createTs, this.picPath, this.briefIntroduction, this.userName);
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
		this.password = encryptPassword(password);
		this.createTs = System.currentTimeMillis();
	}

    public  static String encryptPassword(String password){
        return Codec.hexMD5(password);
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
        long res = dp.singleLongQuery(query, email);
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
                String picPath = rs.getString(6);
                String briefIntroduction = rs.getString(7);
                User user = new User();
                user.setId(id);
                user.setUserName(userName);
                user.setEmail(email);
                user.setPassword(password);
                user.setCreateTs(createTs);
                user.setPicPath(picPath);
                user.setBriefIntroduction(briefIntroduction);
                return user;
            }else{
                return null;
            }
        }catch(SQLException e){
            log.warn(e.getMessage(), e);
        }
        return null;
    }
    private static final String AllProperty = " id, userName, email, password, createTs, picPath, briefIntroduction ";
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

    public static User findUserById(long userId){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where id = ?";
//        Long userIdL  = Long.valueOf(userId);
        return new JDBCBuilder.JDBCExecutor<User>(query, userId){
            @Override
            public User doWithResultSet(ResultSet rs) throws SQLException{
                return doWithResult(rs);
            }
        }.call();
    }

    public static User finUserById(String userId){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where id = ?";
        Long userIdL  = Long.valueOf(userId);
        return new JDBCBuilder.JDBCExecutor<User>(query, userId){
            @Override
            public User doWithResultSet(ResultSet rs) throws SQLException{
                return doWithResult(rs);
            }
        }.call();
    }

    @Override
    public String toString(){
        return "[username: " + this.userName+" useremail:" +this.email + " password:" + this.password +"]";
    }

    public static boolean isMatching(String email, String password){
        password = encryptPassword(password);
        String query = "select id from " + TABLE_NAME + " where email = ? and password = ? ";
        long id = dp.singleLongQuery(query, email, password);
        if(id <= 0){
            return false;
        }else{
            return true;
        }
    }
    public static boolean resetPicPath(String picPath, long id){
        String query = "update " + TABLE_NAME +" set picPath=? where id=?";
        long res = dp.update(query, picPath, id);
        if(res <= 0)return false;
        else return true;
    }

    public static boolean resetBriefIntroduction(String briefIntroduction, long id){
        String query = "update " + TABLE_NAME + " set briefIntroduction = ? where id = ?";
        long res = dp.update(query, briefIntroduction, id);
        if(res <= 0)return false;
        else return true;
    }

    public static boolean changePW(String newpassword, long id){
        String query = "update " + TABLE_NAME + " set password = ? where id = ?";
        newpassword = encryptPassword(newpassword);
        long res = dp.update(query, newpassword, id);
        if(res <= 0)return false;
        else return true;
    }

    public static boolean changeInfo(long id,String picPath, String briefIntroduction,String email){
        String query = "update " + TABLE_NAME + " set picPath = ? , briefIntroduction = ?, email = ? where id=?";
        long res = dp.update(query, picPath, briefIntroduction, email, id);
        if(res <= 0)return false;
        return true;
    }

    public static boolean changeInfo(long id , String briefIntroduction, String email){
        String query = "update " + TABLE_NAME + " set briefIntroduction = ?, email = ? where id=?";
        long res = dp.update(query, briefIntroduction, email, id);
        if(res <= 0)return false;
        return true;
    }
}
