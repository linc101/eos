package models;

import codegen.CodeGenerator.PolicySQLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import play.db.jpa.Model;

/**
 * Created by yehuizhang on 14/10/26.
 */

@JsonAutoDetect
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
@Entity(name = DoubanUser.TABLE_NAME)
public class DoubanUser extends Model implements PolicySQLGenerator{
    private static final Logger log = LoggerFactory.getLogger(DoubanUser.class);

    public static final String TABLE_NAME = "douban_user";

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
