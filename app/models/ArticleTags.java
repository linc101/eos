package models;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import transaction.JDBCBuilder;
import transaction.DBBuilder.DataSrc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.List;

/**
 * Created by uttp on 14-10-28.
 */
@Entity(name = ArticleTags.TABLE_NAME)
public class ArticleTags extends Model implements PolicySQLGenerator {
    private static final Logger log = LoggerFactory.getLogger(ArticleTags.class);

    public static final String TABLE_NAME = "article_tags";

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "count")
    private long count;

    @Column(name = "create_ts")
    private long createTs;

    public static final ArticleTags _instance = new ArticleTags();

    public static final DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public ArticleTags(){

    }

    public ArticleTags(String tagName){
        this.tagName = tagName;
        this.count = 1;
        this.createTs = System.currentTimeMillis();
    }

    public static boolean findIfExistedByTagName(String tagName){
        String query = "select id from " + TABLE_NAME + " where tag_name = ?";

        long res =  dp.singleLongQuery(query, tagName);
        if(res <= 0 ){
            return false;
        }else{
            return true;
        }
    }

    public static final String ALLPROPERTY = " id, tag_name, count, create_ts ";
    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " ( " + ALLPROPERTY + " ) values (?,?,?,?)";
        long res = dp.insert(query, this.id, this.tagName, this.count, this.createTs);
        if(res <= 0){
            log.error("insert failed!");
            return false;
        }else{
            return true;
        }
    }

    public boolean update(){
        String queryCount = "select count from " + TABLE_NAME + " where tag_name = ?";
        long cou = dp.singleLongQuery(queryCount, this.tagName);

        String query = "update " + TABLE_NAME + " set count = ? where tag_name = ?";
        long res = dp.update(query, cou + 1, this.tagName);
        if(res <= 0){
            log.error("update failed!");
            return false;
        }else{
            return true;
        }
    }

    public static void saveTagsBatch(List<String> tags){
        for(String tag:tags){
            boolean isExisted = findIfExistedByTagName(tag);
            boolean flag;
            if(isExisted){
                flag = new ArticleTags(tag).update();
            }else{
                flag = new ArticleTags(tag).insert();
            }
            if(!flag){
                log.error("insert or update tag: " + tag + " failed!");
            }

        }
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
