package models;

import codegen.CodeGenerator;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import play.db.jpa.Model;

import javax.persistence.Entity;
/**
 * Created by uttp on 14-9-21.
 */
@JsonAutoDetect
@Entity(name = Message.TABLE_NAME)
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
public class Message  extends Model implements CodeGenerator.PolicySQLGenerator{
    private static final Logger log = LoggerFactory.getLogger(Message.class);

    public static final String TABLE_NAME = "message";

    private String from;

    private String to;

    private String mes;

    private long createTs;

    private boolean isRead;
    

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

    @Override
    public boolean jdbcSave() {
        return false;
    }

    @Override
    public String getIdName() {
        return null;
    }
}
