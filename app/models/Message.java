package models;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import play.db.jpa.Model;
import transaction.DBBuilder;
import transaction.JDBCBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Column(columnDefinition = "enum('SYSTEM_MES','COMMENT_MES')")
    @Enumerated(EnumType.STRING)
    private Type type;

    private long expId;

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

    public long getExpId(){
        return this.expId;
    }

    public void setExpId(long expId){
        this.expId = expId;
    }

    public Type getType(){
        return this.type;
    }

    public void setType(Type type){
        this.type = type;
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
        this.expId = 0L;
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
        this.id = id;
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
        String query = "insert into " + TABLE_NAME +" (fromUser, toUser, msg, type, createTs, isRead, expId) values (?,?,?,?,?,?,?)";
        long res = dp.insert(query, this.fromUser, this.toUser, this.msg, this.type, this.createTs, this.isRead, this.expId);
        if(res <= 0)return false;
        else return true;
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set fromUser = ?, toUser = ?, msg = ?, type = ?, createTs = ?, isRead = ?, expId = ?";
        long res = dp.insert(query, this.fromUser, this.toUser, this.msg, this.type, this.createTs, this.isRead, this.expId);
        if(res <= 0)return false;
        else return true;
    }

    @Override
    public String getIdName() {
        return null;
    }

    public static Message parseMessage(ResultSet res){
        try{
            Message msg = new Message();
            msg.setId(res.getLong(1));
            msg.setFrom(res.getString(2));
            msg.setTo(res.getString(3));
            msg.setMsg(res.getString(4));
            msg.setCreateTs(res.getLong(5));
            msg.setIsRead(res.getBoolean(6));
            String type = res.getString(7);
            if(StringUtils.equals(type, "SYSTEM_MES")){
                msg.setType(Type.SYSTEM_MES);
            }else if(StringUtils.equals(type, "COMMENT_MES")){
                msg.setType(Type.COMMENT_MES);
            }
            msg.setExpId(res.getLong(8));
            return msg;
        }catch(SQLException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    public static final String AllProperty = " id, fromUser, toUser, msg, createTs, isRead, type, expId ";

    public static List<Message> findAllMessageById(String toUser, Type type){
        log.info("--------database type:" + type.name());
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where toUser = ? and type = ? and isRead = false order by createTs asc";

        return new JDBCBuilder.JDBCExecutor<List<Message>>(query, toUser, type){
            @Override
            public List<Message> doWithResultSet(ResultSet res) throws SQLException{
                List<Message> msgs = new ArrayList<Message>();
                while(res.next()){
                    Message msg = parseMessage(res);
                    if(msg != null)
                        msgs.add(msg);
                }
                return msgs;
            }
        }.call();
    }
}
