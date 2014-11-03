package models;

import play.db.jpa.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import javax.persistence.Entity;
import javax.persistence.Column;



import codegen.CodeGenerator.DBDispatcher;
import codegen.CodeGenerator.PolicySQLGenerator;
/**
 * Created by yehuizhang on 14/11/3.
 */

@Entity(name=Picture.TABLE_NAME)
public class Picture extends Model implements PolicySQLGenerator{
    private static final Logger log = LoggerFactory.getLogger(Picture.class);
    public static final String TABLE_NAME = "picture";

    @Column(name="picPath")
    private String picPath;

    private String describe;

    private long createTs;

    private long count;

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
