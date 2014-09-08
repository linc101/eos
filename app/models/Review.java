package models;


import codegen.CodeGenerator;
import play.db.jpa.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Column;
/**
 * Created by yehuizhang on 14-9-8.
 */
@Entity(name=Review.TABLE_NAME)
public class Review extends Model implements CodeGenerator.PolicySQLGenerator {
    private static final Logger logger = LoggerFactory.getLogger(Review.class);

    public static final String TABLE_NAME = "review";

    @Column(name="expId")
    private Long expId;


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
