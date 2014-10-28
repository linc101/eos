package models;

import codegen.CodeGenerator.PolicySQLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Column;

/**
 * Created by uttp on 14-10-28.
 */
@Entity(name = ArticleTags.TABLE_NAME)
public class ArticleTags extends Model implements PolicySQLGenerator {
    private static final Logger log = LoggerFactory.getLogger(ArticleTags.class);

    public static final String TABLE_NAME = "article_tags";

    private String tagName;

    private long count;

    private long createTs;

    public ArticleTags(){

    }

    public ArticleTags(String tagName){
        this.tagName = tagName;
        this.count = 1;
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

    @Override
    public boolean jdbcSave() {
        return false;
    }

    @Override
    public String getIdName() {
        return null;
    }
}
