package controllers;

import models.Experience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by yehuizhang on 14-9-9.
 */
public class Article extends CommonRender{
    private static final Logger log = LoggerFactory.getLogger(Article.class);

    public static void article(final long articleId){
        Experience exp = Experience.findExpById(articleId);
        log.info("------------exp:" + exp);
        if(exp == null){
            RenderFailed("访问的文章不存在");
        }
        RenderSuccess(exp);
    }

}
