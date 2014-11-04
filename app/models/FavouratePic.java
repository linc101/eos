package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonAutoDetect;
/**
 * Created by yehuizhang on 14/11/4.
 */
@Entity(name = FavouratePic.TABLE_NAME)
public class FavouratePic extends Model  {
    public static final String TABLE_NAME = "favourate_pic";

    private static final Logger log = LoggerFactory.getLogger(FavouratePic.class);

    @Column(name = "userId")
    private long userId;

    private long picId;

    private boolean isFavourate;

    public FavouratePic(long userId, long picId, boolean isFavourate){
        this.userId = userId;
        this.picId = picId;
        this.isFavourate = isFavourate;
    }

    public long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    public long getPicId(){
        return this.picId;
    }

    public void setPicId(long picId){
        this.picId = picId;
    }

    public boolean getIsFavourate(){
        return this.isFavourate;
    }

    public void setIsFavourate(boolean isFavourate){
        this.isFavourate = isFavourate;
    }

}
