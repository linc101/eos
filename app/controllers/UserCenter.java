package controllers;

import models.User;
import models.Experience;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import util.PageOffset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yehuizhang on 14-9-6.
 */
public class UserCenter extends CommonRender {
    public static Logger logger = LoggerFactory.getLogger(UserCenter.class);

    public static void userCenter(){
        render("/usercenter/userartical.html");
    }

    public static void addExperience(){
        render("/usercenter/addexperience.html");
    }

    public static void doAddExperience(String title, String article){
        title = trimString(title);
        article = trimString(article);

        User user = getUser();
        if(StringUtils.isEmpty(title)){
            RenderFailed("请输入经验标题");
        }

        if(StringUtils.length(title) < 6){
            RenderFailed("请最少输入6个");
        }

        if(StringUtils.isEmpty(article)){
            RenderFailed("请输入经历内容");
        }

        long articleId = new Experience(user.getUserName(), title, article).firstSave();

        if(articleId <= 0){
            RenderFailed("数据库异常");
        }else{
            RenderSuccess(articleId);
        }
    }

    public static void showAllMyExps(final long userId, final int pn, final int ps){
        if(userId <= 0){
            RenderFailed("用户Id不合法！");
        }

        PageOffset offset = new PageOffset(pn, ps);

        List<Experience> exps = Experience.findExpByUserId(userId, offset);




    }

    private static String trimString(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }

        return StringUtils.trim(str);
    }
}
