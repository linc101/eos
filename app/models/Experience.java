package models;

import play.db.jpa.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Lob;
import javax.persistence.Entity;
import javax.persistence.Column;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import transaction.DBBuilder.DataSrc;
import transaction.JDBCBuilder;

/**
 * Created by yehuizhang on 14-9-7.
 */
@Entity(name = Experience.TABLE_NAME)
public class Experience extends Model implements PolicySQLGenerator{
    private static final Logger logger = LoggerFactory.getLogger(Experience.class);

    public static final String TABLE_NAME = "EXP";

    @Lob
    @Column(name="artical")
    private String artical;

    @Column(name="title")
    private String title;

    


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
