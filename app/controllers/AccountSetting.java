package controllers;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import models.User;
import play.Play;
import play.libs.Codec;
import play.libs.Files;

/**
 * Created by uttp on 14-9-15.
 */
public class AccountSetting extends CommonRender{
    private static final Logger log = LoggerFactory.getLogger(AccountSetting.class);

    public static void accountSetting(){
        render("/accountset/changepw.html");
    }

    //修改密码页面
    public static void changePWPage(){

    }

    //完善资料页面
    public static void completeInfo(){
        render("/accountset/completeinfo.html");
    }

    //消息页面
    public static void myMessage(){
        render("/accountset/mymessage.html");
    }

    public static void changePW(String oldPW, final String newPW, final String newPWConfirm){
        User user = getUser();
        log.info("oldPW:" + oldPW);
        oldPW = User.encryptPassword(oldPW);
        log.info(user.toString());
        log.info("------------------oldPW1:" + user.getPassword()+"---------------------oldPW2:" + oldPW);
        if(!StringUtils.equals(user.getPassword(), oldPW)){
            RenderFailed("旧密码错误！");
        }

        if(newPW.length() < 6){
            RenderFailed("密码长度太短，请重新填写！");
        }

        if(!StringUtils.equals(newPW, newPWConfirm)){
            RenderFailed("两次输入的新密码不匹配！请重新输入");
        }

        boolean isSuccess = User.changePW(newPW, user.getId());

        if(isSuccess){
            removeSession();
            RenderSuccess();
        }else{
            RenderFailed("数据库操作失败！");
        }
    }


    public static void picUpload(File pic , final String briefIntroduction){
        if(pic == null){
            RenderFailed("上传照片失败！");
        }

        User user = getUser();
        String picPath = "/public/images/userheadimages/" + pic.getName();
        Files.copy(pic, Play.getFile(picPath));
//        boolean isSuccess = User.resetPicPath(picPath, user.getId());
//        if(isSuccess)
//            RenderSuccess();
//        else{
//            RenderFailed("数据库操作失败！");
//        }
    }

    public static void changeBriefIntroduction(final String briefIntroduction){
        if(!StringUtils.isEmpty(briefIntroduction)){
            RenderFailed("自我简述不能为空！");
        }

        User user = getUser();
        boolean isSuccess = User.resetBriefIntroduction(briefIntroduction, user.getId());
        if(isSuccess){
            RenderSuccess();
        }else{
            RenderFailed("数据库操作失败！");
        }
    }

}
