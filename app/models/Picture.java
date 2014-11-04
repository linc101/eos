package models;

//import org.hibernate.annotations.Cascade;
//import play.data.validation.Required;
//import play.db.jpa.Model;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.codehaus.jackson.annotate.JsonAutoDetect;
//import org.hibernate.annotations.Index;
//
//import javax.persistence.*;
//
//import codegen.CodeGenerator.DBDispatcher;
//import codegen.CodeGenerator.PolicySQLGenerator;
//import transaction.DBBuilder.DataSrc;
//
//import java.util.ArrayList;
//import java.util.List;

/**
 * Created by yehuizhang on 14/11/3.
 */
import java.util.*;
import javax.persistence.*;

import play.data.binding.*;
import play.data.validation.*;
import play.db.jpa.Model;
//@JsonAutoDetect
@Entity//(name=Picture.TABLE_NAME)
//@JsonIgnoreProperties(value = {"entityId", "idColumn", "idName","persistent" ,"tableHashKey", "tableName"})
public class Picture extends Model{
//    private static final Logger log = LoggerFactory.getLogger(Picture.class);
//    public static final String TABLE_NAME = "picture";

    @Required
    @ManyToOne
    public User user;

    @Required
    @Column(name="picPath")
    public String picPath;

    @Required
    public String describe;

    @Required
    public long createTs;

    @Required
    public long count;

    @Required
    @OneToMany(mappedBy="picture", cascade=CascadeType.ALL)
    public List<FavouratePic> favouratePics;

//    public Picture(){
//
//    }

    public Picture(User user, String picPath, String describe){
        this.user = user;
        this.picPath = picPath;
        this.describe = describe;
        this.createTs = System.currentTimeMillis();
        this.count = 0;
        this.favouratePics = new ArrayList<FavouratePic>();
    }

//    private static final Picture _instance = new Picture();

//    private static final DBDispatcher dp = new DBDispatcher(DataSrc.BASIC, _instance);

//    public List<FavouratePic> getFavouratePic(){
//        return this.favouratePics;
//    }
//
//    public void setFavouratePic(List<FavouratePic> favouratePic){
//        this.favouratePics = favouratePic;
//    }
//
//    public long getUserId(){
//        return this.userId;
//    }
//
//    public void setUserId(long userId){
//        this.userId = userId;
//    }
//
//    public String getPicPath(){
//        return this.picPath;
//    }
//
//    public void setPicPath(String picPath){
//        this.picPath = picPath;
//    }
//
//    public String getDescribe(){
//        return this.describe;
//    }
//
//    public void setDescribe(String describe){
//        this.describe = describe;
//    }
//
//    public long getCreateTs(){
//        return this.createTs;
//    }
//
//    public void setCreateTs(long createTs){
//        this.createTs = createTs;
//    }
//
//    public long getCount(){
//        return this.count;
//    }
//
//    public void setCount(long count){
//        this.count = count;
//    }
//
//    public List<FavouratePic> getFavouratePics(){
//        return this.favouratePics;
//    }
//
//    public void setFavouratePics(List<FavouratePic> favouratePics){
//        this.favouratePics = favouratePics;
//    }

//    @Override
//    public String getTableName() {
//        return null;
//    }
//
//    @Override
//    public String getTableHashKey() {
//        return null;
//    }
//
//    @Override
//    public String getIdColumn() {
//        return null;
//    }
//
//    @Override
//    public void setId(Long id) {
//
//    }
//
//    @Override
//    public boolean jdbcSave() {
//        return false;
//    }
//
//    @Override
//    public String getIdName() {
//        return null;
//    }
}
