package models;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import play.db.jpa.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Column;

import weibo4j.http.AccessToken;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import transaction.DBBuilder.DataSrc;
import transaction.JDBCBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

@JsonAutoDetect
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
@Entity(name = WeiboUser.TABLE_NAME)
public class WeiboUser extends Model implements PolicySQLGenerator {
    private static final Logger log = LoggerFactory.getLogger(WeiboUser.class);

    public static final String TABLE_NAME = "weibo_user";

    @Column(name="weibo_uid")
    private String weiboUID;

    @Column(name="access_token")
    private String accessToken;

    @Column(name="weibo_username")
    private String weiboUsername;

    @Column(name="create_ts")
    private Long createTs;

    @Column(name="update_ts")
    private Long updateTs;

    @Column(name="expires_ts")
    private Long expiresTs;

    @Column(name="auth_count")
    private int authCount;

    public String getWeiboUID(){
        return this.weiboUID;
    }

    public void setWeiboUID(String weiboUID){
        this.weiboUID = weiboUID;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getWeiboUsername(){
        return this.weiboUsername;
    }

    public void setWeiboUsername(String weiboUsername){
        this.weiboUsername = weiboUsername;
    }

    public void setCreateTs(Long createTs){
        this.createTs = createTs;
    }

    public Long getCreateTs(){
        return this.createTs;
    }

    public void setUpdateTs(Long updateTs){
        this.updateTs = updateTs;
    }

    public Long getUpdateTs(){
        return this.updateTs;
    }

    public Long getExpiresTs(){
        return this.expiresTs;
    }

    public void setExpiresTs(Long expiresTs){
        this.expiresTs = expiresTs;
    }

    public void setAuthCount(int authCount){
        this.authCount = authCount;
    }

    public int getAuthCount(){
        return this.authCount;
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
    public String getIdColumn() {
        return null;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static long findIfExistedByUID(String uid){
        String query = "select id from " + TABLE_NAME + " where uid=? ";
        return dp.singleLongQuery(query, uid);
    }

    @Override
    public boolean jdbcSave() {
        return false;
    }

    @Override
    public String getIdName() {
        return null;
    }

    public WeiboUser(){

    }

    public WeiboUser(AccessToken at, String weiboUsername){
        this.weiboUID = at.getUid();
        this.accessToken = at.getAccessToken();
        this.weiboUsername = weiboUsername;
        this.createTs = this.updateTs = System.currentTimeMillis();
        this.expiresTs = Long.parseLong(at.getExpireIn());
        this.authCount = 1;
    }

    public static String ALLPROPERTY = " weibo_uid, access_token, weibo_username, create_ts, update_ts, expires_ts, auth_count ";

    public static final WeiboUser _instance = new WeiboUser();

    public static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " (" + ALLPROPERTY + ") values (?,?,?,?,?,?,?)";
        long res = dp.insert(query,this.weiboUID, this.accessToken, this.weiboUsername, this.createTs, this.updateTs, this.expiresTs, this.authCount);
        if(res <= 0){
            log.error("insert falied!");
            return false;
        }else{
            return true;
        }
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set weibo_uid = ?, access_token = ?, weibo_username = ?, create_ts = ?, update_ts = ?, expires_ts = ?, auth_count = ?";
        long res = dp.update(query, this.weiboUID, this.accessToken, this.weiboUsername, this.createTs, this.updateTs, this.expiresTs, this.authCount);
        if(res <= 0){
            log.error("update failed!");
            return false;
        }else{
            return true;
        }
    }
}
