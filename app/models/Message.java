package models;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import play.db.jpa.Model;
import transaction.DBBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by uttp on 14-9-21.
 */
@JsonAutoDetect
@Entity(name = Message.TABLE_NAME)
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
public class Message  extends Model implements PolicySQLGenerator {
    private static final Logger log = LoggerFactory.getLogger(Message.class);

    public static final String TABLE_NAME = "message";

    private String fromUser;

    private String toUser;

    private String msg;

    private long createTs;

    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type{
        SYSTEM_MES,COMMENT_MES
    }

    public String getFrom(){
        return fromUser;
    }

    public void setFrom(String fromUser){
        this.fromUser = fromUser;
    }

    public String getTo(){
        return this.toUser;
    }

    public void setTo(String toUser){
        this.toUser = toUser;
    }

    public String getMsg(){
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public long getCreateTs(){
        return this.createTs;
    }

    public void setCreateTs(long createTs){
        this.createTs = createTs;
    }

    public boolean getIsReag(){
        return this.isRead;
    }

    public void setIsRead(boolean isRead){
        this.isRead = isRead;
    }

    public Message(){

    }

    public Message(String fromUser, String toUser, String msg, Type type){
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
        this.type = type;
        this.createTs = System.currentTimeMillis();
        this.isRead = false;
    }
    private static final Message _instance = new Message();

    public static DBDispatcher dp = new DBDispatcher(DBBuilder.DataSrc.BASIC, _instance);



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

    }

    public long findIdExistedById(long id){
        String query = "select id from " + TABLE_NAME + " where id = ?";
        return dp.singleLongQuery(query, id);
    }

    @Override
    public boolean jdbcSave() {
        long res = findIdExistedById(this.id);
        if(res <= 0){
            return insert();
        }else{
            return update();
        }
    }

    public boolean insert(){
        String query = "insert into " + TABLE_NAME +" (fromUser, toUser, msg, type, createTs, isRead) values (?,?,?,?,?,?)";
        long res = dp.insert(query, this.fromUser, this.toUser, this.msg, this.type, this.createTs, this.isRead);
        if(res <= 0)return false;
        else return true;
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set fromUser = ?, toUser = ?, msg = ?, type = ?, createTs = ?, isRead = ?";
        long res = dp.insert(query, this.fromUser, this.toUser, this.msg, this.type, this.createTs, this.isRead);
        if(res <= 0)return false;
        else return true;
    }

    @Override
    public String getIdName() {
        return null;
    }
}
