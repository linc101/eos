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
    private long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "expires_in")
    private String expiresin;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "create_ts")
    private Long createTs;

    @Column(name = "doubanuser_id")
    private String doubanUserId;

    public DoubanUser(){

    }

    public static final DoubanUser _instance = new DoubanUser();

    public static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public DoubanUser(String accessToken, String doubanUserId, String username, String expiresin, String refreshToken){
        this.accessToken = accessToken;
        this.username = username;
        this.userId = -1L;
        this.expiresin = expiresin;
        this.refreshToken = refreshToken;
        this.createTs = System.currentTimeMillis();
        this.doubanUserId = doubanUserId;
    }



    public String getDoubanUserId(){
        return this.doubanUserId;
    }

    public void setDoubanUserId(String doubanUserId){
        this.doubanUserId = doubanUserId;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
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

    public static final String ALLPROPERTY = " id, access_token, user_id, username, expires_in, refresh_token, create_ts, doubanuser_id ";

    public long findIfExistedByDoubanUserId(String doubanUserId){
        String query = "select id from " + TABLE_NAME + " where doubanuser_id = ? ";
        return dp.singleLongQuery(query, doubanUserId);
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set access_token = ?, user_id = ?, username = ?, expires_in = ?, refresh_token = ?, create_ts = ? where doubanuser_id = ? ";
        long res = dp.update(query, this.accessToken, this.userId, this.username, this.expiresin, this.refreshToken, this.createTs, this.doubanUserId);
        if(res <= 0){
            log.error("update error!");
            return false;
        }else{
            return true;
        }
    }

    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " (" + ALLPROPERTY + ") values (?,?,?,?,?,?,?,?)";
        long res = dp.insert(query, this.id, this.accessToken, this.userId, this.username, this.expiresin, this.refreshToken, this.createTs, this.doubanUserId);
        if(res <= 0){
            log.error("insert failed!");
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean jdbcSave() {
        long id = findIfExistedByDoubanUserId(this.doubanUserId);
        if(id <= 0){
            return insert();
        }else{
            return update();
        }
    }

    @Override
    public String getIdName() {
        return null;
    }
}
