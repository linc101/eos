package controllers;

import models.Review;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.User;
import models.Message;
import models.Message.Type;

import play.Play;
import play.libs.Codec;
import play.libs.Files;

import util.UsageFunction;
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

    public static void showUserInfo(){
        User user = getUser();

        RenderSuccess(user);
    }

    public static void picUpload(File pic , final String briefIntroduction, String email){
//        if(pic == null){
//            flash.put("error", "图片上传错误");
////            RenderFailed("图片上传错误");
//            completeInfo();
//            return;
//        }
        String picPath = null;
        User user = getUser();
        if(pic != null) {
            Date date = new Date();
            picPath = "/public/images/userheadimages/" + user.id + "_" + date.getTime() + "_head." + UsageFunction.getExtensionName(pic.getName());

            Files.copy(pic, Play.getFile(picPath));
        }

        boolean isEmail = UsageFunction.isEmail(email);
        if(!isEmail){
            flash.put("error","邮箱格式不对");
            log.info("-----------------modify email:" + email);
//            RenderFailed("邮箱格式不对");
            completeInfo();
            return;
        }

        long id = User.findEmailExisted(email);
        if(id > 0  && id != user.getId()){
            flash.put("error" ,"已经存在，请使用其它的邮箱号");
//            RenderFailed("email 已经存在，请使用其它的邮箱号");
            completeInfo();
            return;
        }

        boolean isSuccess = false;
        if(picPath != null) {
            isSuccess = User.changeInfo(user.getId(), picPath, briefIntroduction, email);
        }else{
            isSuccess = User.changeInfo(user.getId(), briefIntroduction, email);
        }

        if(isSuccess){
            render("/Application/index.html");
        }else{
            flash.put("error", "数据库异常！");
            completeInfo();
        }
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

    public static void showMsg(Type type){
        log.info("------------------type:" + type.name());
        User user = getUser();
        List<Message> msgs = new ArrayList<Message>();
        msgs = Message.findAllMessageById(user.getUserName(), type);
        RenderSuccess(msgs);
    }

    public static void markReaded(long msgId){
        if(msgId <= 0){
            RenderFailed("message id 不合法！");
        }

        boolean isSuccess = Message.markReaded(msgId);
        if(isSuccess){
            RenderSuccess();
        }else{
            RenderFailed("数据库异常！");
        }
    }



}
