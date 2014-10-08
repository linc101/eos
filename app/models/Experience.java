package models;

import General.Result;
import net.sf.json.JSONObject;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
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
import util.PageOffset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by yehuizhang on 14-9-7.
 */
@JsonAutoDetect
@Entity(name = Experience.TABLE_NAME)
@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
public class Experience extends Model implements PolicySQLGenerator{
    private static final Logger logger = LoggerFactory.getLogger(Experience.class);

    public static final String TABLE_NAME = "exp";

    @Lob
    @Column(name="article")
    private String article;

    @Column(name="title")
    private String title;

    @Column(name="scanTimes")
    private long scanTimes;

    @Column(name="domain")
    int domain;

    long createTs;

    private static class Domain{
        public static final int LIVE = 1;      //生活

        public static final int STUDY = 1<<1;  //学习

        public static final int WORK = 1 <<2;  //工作
    }

    @Column(name="userName")
    String userName;

    private int reviewTimes;

    private long updateTs;

    public long getUpdateTs(){
        return this.updateTs;
    }

    public void setUpdateTs(long updateTs){
        this.updateTs = updateTs;
    }

    public int getReviewTimes(){
        return this.reviewTimes;
    }

    public void setReviewTimes(int reviewTimes){
        this.reviewTimes = reviewTimes;
    }

    public String getArticle(){
        return this.article;
    }

    public void setArticle(String article){
        this.article = article;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public long getScanTimes(){
        return this.scanTimes;
    }

    public void setScanTimes(long scanTimes){
        this.scanTimes = scanTimes;
    }

    public int getDomain(){
        return this.domain;
    }

    public void setDomain(int domain){
        this.domain = domain;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public long getCreateTs(){
        return this.createTs;
    }

    public void setCreateTs(long createTs){
        this.createTs = createTs;
    }

    public Experience(){
        super();
    }

    public Experience(String userName, String title, String article){
        this.userName = userName;
        this.title = title;
        this.article = article;
        this.scanTimes = 0;
        this.domain = Domain.LIVE;
        this.createTs = System.currentTimeMillis();
        this.updateTs = System.currentTimeMillis();
        this.reviewTimes = 0;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
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
    public String getIdName() {
        return null;
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

    public long firstSave(){
        String query = "insert into " + TABLE_NAME + " (`userName`, `title`, `article`, `domain`, `scanTimes`, createTs, updateTs, reviewTimes) values (?,?,?,?,?,?,?,?)";
        long res = dp.insert(query, this.userName, this.title, this.article, this.domain, this.scanTimes, this.createTs, this.updateTs, this.reviewTimes);
        return res;
    }

    private static final Experience _instance = new Experience();

    public static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " (`userName`, `title`, `article`, `domain`, `scanTimes`, createTs, updateTs, reviewTimes) values (?,?,?,?,?,?,?,?)";
        long res = dp.insert(query, this.userName, this.title, this.article, this.domain, this.scanTimes, this.createTs,this.updateTs, this.reviewTimes);
        logger.info("insert res is:" + res);
        if(res <= 0 ){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set `userName` = ?, `title` = ?, `article` = ?, `domain` = ?, `scanTimes` = ?, createTs = ?, updateTs = ?, reviewTimes = ? where id = ?";
        long res = dp.update(query, this.userName, this.title, this.article, this.domain, this.scanTimes,this.createTs, this.updateTs, this.reviewTimes, this.id);
        if(res <= 0){
            return false;
        }else{
            return true;
        }

    }

    public long findIfExistedById(long id){
        String query = "select id from " + TABLE_NAME + " where id = ? ";
        return dp.singleLongQuery(query, id);
    }

    private static final String AllProperty = " id, userName, title, article, scanTimes, domain, createTs, updateTs, reviewTimes ";

    private static Experience parseExperience(ResultSet res){
        Experience exp = new Experience();
        try {
            long id = res.getLong(1);
            logger.info("---------------exp id:" + id);
            exp.setId(res.getLong(1));
            exp.setUserName(res.getString(2));
            exp.setTitle(res.getString(3));
            exp.setArticle(res.getString(4));
            exp.setScanTimes(res.getLong(5));
            exp.setDomain(res.getInt(6));
            exp.setCreateTs(res.getLong(7));
            exp.setUpdateTs(res.getLong(8));
            exp.setReviewTimes(res.getInt(9));
            return exp;
        }catch(SQLException e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<Experience> findAllExp(PageOffset offset){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " limit ?,?";
        return new JDBCBuilder.JDBCExecutor<List<Experience>>(query, offset.getOffset(),offset.getPs()){
            @Override
            public List<Experience> doWithResultSet(ResultSet res) throws SQLException{
                List<Experience> exps = new ArrayList<Experience>();
                while(res.next()){
                    Experience exp = parseExperience(res);
                    if(exp != null)
                        exps.add(exp);
                }
                return exps;
            }
        }.call();
    }

    public static List<Experience> findExpByUsername(String userName, PageOffset offset){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where userName = ? limit ?,?";
        return new JDBCBuilder.JDBCExecutor<List<Experience>>(query, userName, offset.getOffset(),offset.getPs()){
            @Override
            public List<Experience> doWithResultSet(ResultSet res) throws SQLException{
                List<Experience> exps = new ArrayList<Experience>();
                while(res.next()){
                    Experience exp = parseExperience(res);
                    if(exp != null)
                        exps.add(exp);
                }
                return exps;
            }
        }.call();
    }

    public static Experience findExpById(long id){
        String query = "select " + AllProperty + " from " + TABLE_NAME + " where id = ?";
        return new JDBCBuilder.JDBCExecutor<Experience>(query, id){
            @Override
            public Experience doWithResultSet(ResultSet res) throws SQLException{
                if(res.next())
                    return parseExperience(res);
                else{
                    return null;
                }
            }
        }.call();
    }

    public static int countExpByUsername(String userName){
        String query = "select count(*) from " + TABLE_NAME + " where userName = ?";
        return (int)dp.singleLongQuery(query, userName);
    }

    public static int countAllExp(){
        String query = "select count(*) from " + TABLE_NAME ;
        return (int)dp.singleLongQuery(query);
    }

    @Override
    public String toString(){
        return "[userName:"+ this.getUserName() +  " title:"+ this.getTitle() + " article: "+ this.getArticle() +"]";
    }

    public static boolean incTime(long id){
        Experience exp = findExpById(id);
        if(exp == null){
            return false;
        }
        exp.setScanTimes(exp.getScanTimes() + 1);
        return exp.jdbcSave();
    }

    public static boolean increaseReviewTimes(long id){
        Experience exp = findExpById(id);
        if(exp == null){
            return false;
        }

        exp.setReviewTimes(exp.getReviewTimes() + 1);
        return exp.jdbcSave();
    }

    public static boolean decreaseReviewTimes(long id){
        Experience exp = findExpById(id);

        if(exp == null){
            return false;
        }

        long reviewTimes = exp.getReviewTimes();
        if(reviewTimes >=1){
            exp.setScanTimes(reviewTimes - 1);
            return exp.jdbcSave();
        }
        return false;
    }

    public static boolean deleteById(long id){
        String query = "delete from " + TABLE_NAME + " where id = ?";
        long res = dp.update(query, id);
        if(res <= 0 ){
            return false;
        }
        return  true;
    }

    public static String getExperienceUsernameById(long id){
        String query = "select userName from " + TABLE_NAME + " where id = ? ";
        return dp.singleStringQuery(query, id);
    }

    public static List<Experience> findAllExpByField(String field, boolean isDesc, PageOffset offset){
        StringBuilder query = new StringBuilder("select " + AllProperty + " from " + TABLE_NAME + " order by ? ");
        if(isDesc == true){
            query.append(" desc ");
        }else{
            query.append(" asc ");
        }
        query.append(" limit ?,?");
        String q = query.toString();
        return new JDBCBuilder.JDBCExecutor<List<Experience>>(q, field, offset.getOffset(),offset.getPs()){
            @Override
            public List<Experience> doWithResultSet(ResultSet res) throws SQLException{
                List<Experience> exps = new ArrayList<Experience>();
                while(res.next()){
                    Experience exp = parseExperience(res);
                    if(exp != null)
                        exps.add(exp);
                }
                return exps;
            }
        }.call();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("--------test only test for Json----------------------- ");
        List<Experience> listExps = new ArrayList<Experience>();
        Experience exp = new Experience("1", "2", "3");
        listExps.add(exp);
        Experience expTemp = new Experience("4", "5", "6");
        listExps.add(expTemp);
        ObjectMapper mapper = new ObjectMapper();
        Result<List<Experience>> res = new Result<List<Experience>>(listExps);

        String s = mapper.writeValueAsString(res);
        System.out.println(s);
        JSONObject jsonObject = JSONObject.fromObject(s);

        Date date = new Date();
        System.out.println("------------------------date time:" + date.getTime());
    }


}
