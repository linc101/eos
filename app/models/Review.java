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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private boolean isChildReview;

    private int floor;

    public Review(long expId, String reviewer, String content){
        this.expId = expId;
        this.reviewer = reviewer;
        this.content = content;
        this.createTs = System.currentTimeMillis();
        this.isChildReview = false;
        this.floor = findMaxFloor(expId);
    }

    public Review(){

    }

    public Review(long expId, String reviewed, String reviewer, String content){
        this.expId = expId;
        this.reviewed = reviewed;
        this.reviewer = reviewer;
        this.content = content;
        this.createTs = System.currentTimeMillis();
        this.isChildReview = true;
        this.floor = findMaxFloor(expId);
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

    public boolean getIsChildReview(){
        return this.isChildReview;
    }

    public void setIsChildReview(boolean isChildReview){
        this.isChildReview = isChildReview;
    }

    public int getFloor(){
        return this.floor;
    }

    public void setFloor(int floor){
        this.floor = floor;
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
        String query = "select id form " + getMapTableName(this.expId) + " where id=?";
        return dp.singleLongQuery(query, id);
    }

    public static int findMaxFloor(long expId){
        String query = "select floor from " + getMapTableName(expId) + " where expId = ? order by floor desc limit 1";
        int res =  (int)dp.singleLongQuery(query, expId) + 1;
        return res;
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

    public static String getMapTableName(long expId){
        StringBuilder sb = new StringBuilder("review");
        sb.append(expId%32);
        return sb.toString();
    }

    public boolean insert(){
        String query = "insert into " + getMapTableName(this.expId) + " (expId, reviewed, reviewer, content, createTs, isChildReview, floor) values (?,?,?,?,?,?,?)";
        long res = dp.insert(query, this.expId, this.reviewed, this.reviewer, this.content, this.createTs, this.isChildReview, this.floor);
        if(res <= 0){
            return false;
        }else{
            return true;
        }
    }

    public long firstInsert(){
        String query = "insert into " + getMapTableName(this.expId) + " (expId, reviewed, reviewer, content, createTs, isChildReview, floor) values (?,?,?,?,?,?,?)";
        return dp.insert(query, this.expId, this.reviewed, this.reviewer, this.content, this.createTs, this.isChildReview,this.floor);
    }

    public boolean update(){
        String query = "update " + getMapTableName(this.expId) + " set expId=?, reviewed=?, reviewer=?, content=?, createTs=?, isChildReview=?, floor=? where id = ?";
        long res = dp.update(query, this.expId, this.reviewed, this.reviewer, this.content, this.createTs, this.isChildReview, this.floor,this.id);
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

    @Override
    public String toString(){
        return String.format("[id = %d,expId = %d, reviewer = %s,content = %s, createTs = %d, isChildReview = %b, floor = %d ]",this.id,this.expId,this.reviewer, this.content, this.createTs, this.isChildReview, this.floor);
    }

    public static Review parseReview(ResultSet res){
        try{
            Review review = new Review();
            review.setExpId(res.getLong(1));
            review.setReviewed(res.getString(2));
            review.setReviewer(res.getString(3));
            review.setContent(res.getString(4));
            review.setCreateTs(res.getLong(5));
            review.setIsChildReview(res.getBoolean(6));
            review.setFloor(res.getInt(7));
            review.setId(res.getLong(8));
            return review;
        }catch(SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static final String AllProperty = "expId, reviewed, reviewer, content, createTs, isChildReview, floor ,id";

    public static List<Review> findAllReviewByExp(long expId){
        String query = "select " + AllProperty + " from " + getMapTableName(expId) + " where expId = ? order by floor asc";

        return new JDBCBuilder.JDBCExecutor<List<Review>>(query, expId){
            @Override
            public List<Review> doWithResultSet(ResultSet res) throws SQLException{
                List<Review> reviews = new ArrayList<Review>();
                while(res.next()){
                    Review review = parseReview(res);
                    if(review != null)
                        reviews.add(review);
                }
                return reviews;
            }
        }.call();
    }

    public static boolean deleteReview(long reviewId, long expId){
        String query = "delete from " + getMapTableName(expId) + " where id=?";
        long res = dp.update(query, reviewId);
        if(res <= 0)return false;
        else return true;
    }

}
