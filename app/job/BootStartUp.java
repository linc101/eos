package job;

import models.ArticleTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yehuizhang on 14/10/29.
 */
@OnApplicationStart
public class BootStartUp extends Job {
    private static final Logger log = LoggerFactory.getLogger(BootStartUp.class);

    private static List<String> listTags = new ArrayList<String>(Arrays.asList("购物"
    , "电影", "餐厅", "旅游", "骑行", "游戏", "情感", "读书"));

    @Override
    public void doJob(){
        for(String tag : listTags){
            boolean isExisted = ArticleTags.findIfExistedByTagName(tag);
            if(!isExisted){
                new ArticleTags(tag).insert();
            }
        }
    }
}
