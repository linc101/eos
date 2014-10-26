package models;

import codegen.CodeGenerator.DBDispatcher;
import codegen.CodeGenerator.PolicySQLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import play.db.jpa.Model;

import transaction.DBBuilder.DataSrc;
import transaction.JDBCBuilder;
/**
 * Created by yehuizhang on 14/10/26.
 */

@JsonAutoDetect
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
@Entity(name = DoubanUser.TABLE_NAME)
public class DoubanUser extends Model implements PolicySQLGenerator{
    private static final Logger log = LoggerFactory.getLogger(DoubanUser.class);

    public static final String TABLE_NAME = "douban_user";

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "expires_in")
    private String expiresin;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "create_ts")
    private Long createTs;

    @Column(name = "doubanuser_id")
    private Long doubanUserId;

    public DoubanUser(){

    }

    public static final DoubanUser _instance = new DoubanUser();

    public static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public DoubanUser(String accessToken, String userId, String username, String expiresin, String refreshToken, String createTs){
        this.accessToken = accessToken;
        this.username = username;
        this.userId = userId;
        this.expiresin = expiresin;
        this.refreshToken = refreshToken;
        this.createTs = System.currentTimeMillis();
        this.doubanUserId = -1L;
    }

    public long getDoubanUserId(){
        return this.doubanUserId;
    }

    public void setDoubanUserId(long doubanUserId){
        this.doubanUserId = doubanUserId;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getExpirsein(){
        return this.expiresin;
    }

    public void setExpirsein(String expirsein){
        this.expiresin = expirsein;
    }

    public String getRefreshToken(){
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public long getCreateTs(){
        return this.createTs;
    }

    public void setCreateTs(long createTs){
        this.createTs = createTs;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getTableHashKey() {
        return null;
    }

    @Override
    public String getIdColumn() {
        return null;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean jdbcSave() {
        return false;
    }

    @Override
    public String getIdName() {
        return null;
    }
}
