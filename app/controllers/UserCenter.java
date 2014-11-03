package controllers;

import models.ArticleTags;
import models.User;
import models.Experience;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import play.cache.Cache;
import util.PageOffset;

import java.util.*;

import static controllers.WrapRender.*;

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

    public static void doAddExperience(String title, String article, String tags){
        title = trimString(title);
        article = trimString(article);

        User user = getUser();
        if(StringUtils.isEmpty(title)){
            RenderFailed("请输入经验标题");
        }

        if(StringUtils.length(title) < 6){
            RenderFailed("请最少输入6个");
        }

        if(StringUtils.length(title) > 40){
            RenderFailed("请输入标题的字数最多不超过40个字");
        }

        if(StringUtils.isEmpty(article)){
            RenderFailed("请输入经历内容");
        }

        long articleId = new Experience(user.getUserName(), title, article, tags).firstSave();
        logger.info("--------------------userinfo:" + user);
        if(articleId <= 0){
            RenderFailed("数据库异常");
        }else{
            //如果文章成功的插入数据库则tag进行保存
            List<String> tagsList = dealWithTags(tags);
            saveTags(tagsList);
            RenderSuccess(articleId);
        }
    }

    private static void saveTags(List<String> tagsList){
        if(tagsList == null || tagsList.size() == 0){
            return;
        }
        ArticleTags.saveTagsBatch(tagsList);
    }

    private static List<String> dealWithTags(String tags){
        if(StringUtils.isEmpty(tags)){
            return null;
        }

        String[] tagss = tags.split(" ");

        if(0 == tagss.length){
            return null;
        }

        Set<String> setString = new HashSet<String>(Arrays.asList(tagss));
        return new ArrayList<String>(setString);
    }

    public static void showAllMyExps(final int pn, final int ps){
        User user = getUser();

        PageOffset offset = new PageOffset(pn, ps);

        List<Experience> exps = Experience.findExpByUsername(user.getUserName(), offset);

        int count = Experience.countExpByUsername(user.getUserName());

        RenderSuccess(exps, count, offset);
    }

    public static void showAllExps(final int pn, final int ps, String field, boolean isDesc){
        if(StringUtils.isEmpty(field)){
            RenderFailed("显示的字段不正确");
        }
        logger.error("pn:" + pn +" ps:" + ps);
        PageOffset po = new PageOffset(pn, ps);
        List<Experience> exps = Experience.findAllExpByField(field, isDesc,po);
        int count = Experience.countAllExp();
        RenderSuccess(exps, count, po);
    }

    public static void deleteExpById(final long expId){
        boolean isSuccess = Experience.deleteById(expId);
        if(!isSuccess){
            RenderFailed("数据库异常，删除失败！");
        }

        RenderSuccess();
    }

    private static String trimString(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }

        return StringUtils.trim(str);
    }

    public static void main(String[] args){
        String s1 = "";
        String s2 = "test1";
        String s3 = "test1 test2 test3";
        String[] ss1 = s1.split(" ");
//        System.out.println(s1);
        printString(ss1);
        String[] ss2 = s2.split(" ");
//        System.out.println(ss2);
        printString(ss2);
        String[] ss3 = s3.split(" ", 2);
//        System.out.println(ss3);
        printString(ss3);
    }

    private static void printString(String[] ss){
        for(String s : ss){
            if(StringUtils.isEmpty(s)){
                System.out.println("is empty!");
                continue;
            }
            System.out.println(s);
        }

    }
}
