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

import weibo4j.Weibo;
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

    @Column(name="user_id")
    private Long userId;

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

    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
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
        String query = "select id from " + TABLE_NAME + " where weibo_uid=? ";
        return dp.singleLongQuery(query, uid);
    }

    @Override
    public boolean jdbcSave() {
        long id = findIfExistedByUID(this.weiboUID);
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

    public WeiboUser(){

    }

    public WeiboUser(AccessToken at, String weiboUsername){
        this.weiboUID = at.getUid();
        this.accessToken = at.getAccessToken();
        this.weiboUsername = weiboUsername;
        this.createTs = this.updateTs = System.currentTimeMillis();
        this.expiresTs = Long.parseLong(at.getExpireIn());
        this.userId = -1L;
    }


    public static final WeiboUser _instance = new WeiboUser();

    public static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " (" + ALLPROPERTY + ") values (?,?,?,?,?,?,?,?)";
        long res = dp.insert(query,this.id, this.weiboUID, this.accessToken, this.weiboUsername, this.createTs, this.updateTs, this.expiresTs, this.userId);
        if(res <= 0){
            log.error("insert falied!");
            return false;
        }else{
            return true;
        }
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set weibo_uid = ?, access_token = ?, weibo_username = ?, update_ts = ?, expires_ts = ?, user_id = ? where weibo_uid = ?";
        long res = dp.update(query, this.weiboUID, this.accessToken, this.weiboUsername, this.updateTs, this.expiresTs, this.userId, this.weiboUID);
        if(res <= 0){
            log.error("update failed!");
            return false;
        }else{
            return true;
        }
    }

    public boolean updateUserId(Long userId, String weiboUID){
        String query = "update " + TABLE_NAME + " set user_id = ? where weibo_uid = ?";
        long res = dp.update(query, userId, weiboUID);
        if(res <=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean findIfConnectUser(String weiboUID){
        String query = "select user_id from " + TABLE_NAME + " where weibo_uid = ?";
        long userId = dp.singleLongQuery(query, weiboUID);
        if(userId <= 0){
            return false;
        }else{
            return true;
        }
    }

    public static String ALLPROPERTY = " id, weibo_uid, access_token, weibo_username, create_ts, update_ts, expires_ts, user_id ";

    private static WeiboUser doWithResult(ResultSet rs){
        try {
            if (rs.next()) {
                long id = rs.getLong(1);
                String weiboUID = rs.getString(2);
                String accessToken = rs.getString(3);
                String weiboUsername = rs.getString(4);
                Long createTs = rs.getLong(5);
                Long updateTs = rs.getLong(6);
                Long expiresTs = rs.getLong(7);
                Long userId = rs.getLong(8);
                WeiboUser user = new WeiboUser();
                user.setId(id);
                user.setWeiboUID(weiboUID);
                user.setAccessToken(accessToken);
                user.setWeiboUsername(weiboUsername);
                user.setCreateTs(createTs);
                user.setUpdateTs(updateTs);
                user.setExpiresTs(expiresTs);
                user.setUserId(userId);
                return user;
            }else{
                return null;
            }
        }catch(SQLException e){
            log.warn(e.getMessage(), e);
        }
        return null;
    }

    public static WeiboUser finWeibouserByUID(String weiboUID){
        String query = "select " + ALLPROPERTY + " from " + TABLE_NAME + " where weibo_uid = ?";

        return new JDBCBuilder.JDBCExecutor<WeiboUser>(query, weiboUID){
            @Override
            public WeiboUser doWithResultSet(ResultSet rs) throws SQLException{
                return doWithResult(rs);
            }
        }.call();
    }

    public static boolean setUserId(String userId, String weiboUID){
        log.info("-------------------userId:" + userId + "   weiboUID:" + weiboUID);
        String query = "update " + TABLE_NAME + " set user_id = ? where weibo_uid = ?";
        long id =  dp.update(query, userId, weiboUID);
        if(id <= 0){
            return false;
        }else{
            return true;
        }
    }

}
