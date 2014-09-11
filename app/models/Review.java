package models;

import play.db.jpa.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Lob;

import codegen.CodeGenerator.PolicySQLGenerator;
import codegen.CodeGenerator.DBDispatcher;

import transaction.DBBuilder.DataSrc;
import transaction.JDBCBuilder;
/**
 * Created by yehuizhang on 14-9-8.
 */
@Entity(name=Review.TABLE_NAME)
public class Review extends Model implements PolicySQLGenerator {
    private static final Logger logger = LoggerFactory.getLogger(Review.class);

    public static final String TABLE_NAME = "review";

    private static final Review _instance = new Review();

    private static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    @Column(name="expId")
    private Long expId;

    @Column(name="reviewer")
    private String reviewer;

    @Column(name="reviewed")
    private String reviewed;

    @Lob
    @Column(name="content")
    private String content;

    private Long createTs;

    private Long childReviewId;

    public Review(){

    }

    public Review(long expId, String reviewed, String reviewer, String content){
        this.expId = expId;
        this.reviewed = reviewed;
        this.reviewer = reviewer;
        this.content = content;
        this.createTs = System.currentTimeMillis();
        this.childReviewId = 0L;
    }

    public long getExpId(){
        return this.expId;
    }

    public void setExpId(long expId){
        this.expId = expId;
    }


    public String getReviewer(){
        return this.reviewer;
    }

    public void setReviewer(String reviewer){
        this.reviewer = reviewer;
    }

    public String getReviewed(){
        return this.reviewed;
    }

    public void setReviewed(String reviewed){
        this.reviewed = reviewed;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public long getCreateTs(){
        return this.createTs;
    }

    public void setCreateTs(long createTs){
        this.createTs = createTs;
    }

    public long getChildReviewId(){
        return this.childReviewId;
    }

    public void setChildReviewId(long childReviewId){
        this.childReviewId = childReviewId;
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

    public long findIfExistedById(long id){
        String query = "select id form " + TABLE_NAME + " where id=?";
        return dp.singleLongQuery(query, id);
    }

    @Override
    public boolean jdbcSave() {
        long id = findIfExistedById(this.id);
        if(id <= 0){
            return insert();
        }else{
            return update();
        }
    }

    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " (expId, reviewed, reviewer, content, createTs, childReviewId) values (?,?,?,?,?,?)";
        long res = dp.insert(query, this.expId, this.reviewed, this.reviewer, this.content, this.createTs, this.childReviewId);
        if(res <= 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set expId=?, reviewed=?, reviewer=?, content=?, createTs=?, childReviewId=? where id = ?";
        long res = dp.update(query, this.expId, this.reviewed, this.reviewer, this.content, this.createTs, this.childReviewId, this.id);
        if(res <= 0){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String getIdName() {
        return null;
    }
}
