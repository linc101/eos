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

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by yehuizhang on 14-9-7.
 */
@Entity(name = Experience.TABLE_NAME)
public class Experience extends Model implements PolicySQLGenerator{
    private static final Logger logger = LoggerFactory.getLogger(Experience.class);

    public static final String TABLE_NAME = "EXP";

    @Lob
    @Column(name="article")
    private String article;

    @Column(name="title")
    private String title;

    @Column(name="scanTimes")
    private long scanTimes;

    @Column(name="domain")
    int domain;

    private static class Domain{
        public static final int LIVE = 1;      //生活

        public static final int STUDY = 1<<1;  //学习

        public static final int WORK = 1 <<2;  //工作
    }

    @Column(name="userName")
    String userName;

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

    public Experience(){

    }

    public Experience(String userName, String title, String article){
        this.userName = userName;
        this.title = title;
        this.article = article;
        this.scanTimes = 0;
        this.domain = Domain.LIVE;
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

    private static final Experience _instance = new Experience();

    public static DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

    public boolean insert(){
        String query = "insert into " + TABLE_NAME + " (`userName`, `title`, `article`, `domain`, `scanTimes`) values (?,?,?,?,?)";
        long res = dp.singleLongQuery(query, this.userName, this.title, this.article, this.domain, this.scanTimes);
        if(res <= 0 ){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(){
        String query = "update " + TABLE_NAME + " set `userName` = ?, `title` = ?, `article` = ?, `domain` = ?, `scanTimes` = ?";
        long res = dp.singleLongQuery(query, this.userName, this.title, this.article, this.domain, this.scanTimes);
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

    public


}
