package models;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity(name = User.TABLE_NAME)
public class User extends Model{
	private static final Logger log = LoggerFactory.getLogger(User.class);
	
	public static final String TABLE_NAME = "user";
	
	@Column(name="userName", unique=true, length=512)
	private String userName;
	
	@Column(name="email", unique=true, length=512)
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
	
}
