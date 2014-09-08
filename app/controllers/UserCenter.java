package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
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

        if(StringUtils.isEmpty(title)){
            RenderFailed("请输入经验标题");
        }

        if(StringUtils.length(title) < 6){
            RenderFailed("请最少输入6个");
        }
    }

    private static String trimString(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }

        return StringUtils.trim(str);
    }
}
