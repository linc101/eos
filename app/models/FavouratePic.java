package models;

//import play.db.jpa.Model;
//import play.data.validation.Required;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.codehaus.jackson.annotate.JsonAutoDetect;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
/**
 * Created by yehuizhang on 14/11/4.
 */
@Entity//(name = FavouratePic.TABLE_NAME)
public class FavouratePic extends Model  {
//    public static final String TABLE_NAME = "favourate_pic";
//
//    private static final Logger log = LoggerFactory.getLogger(FavouratePic.class)
    @Required
    public long userId;

    @ManyToOne
    @Required
    public Picture picture;

    @Required
    public boolean isFavourate;

    public FavouratePic(Picture picture, long userId, boolean isFavourate){
        this.picture = picture;
        this.userId = userId;
        this.isFavourate = isFavourate;
    }

//    public long getUserId(){
//        return this.userId;
//    }
//
//    public void setUserId(long userId){
//        this.userId = userId;
//    }
//
//    public boolean getIsFavourate(){
//        return this.isFavourate;
//    }
//
//    public void setIsFavourate(boolean isFavourate){
//        this.isFavourate = isFavourate;
//    }
//
//    public Picture getPic(){
//        return this.pic;
//    }
//
//    public void setPic(Picture pic){
//        this.pic = pic;
//    }
}
