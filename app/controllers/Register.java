package controllers;

import models.DoubanUser;
import models.WeiboUser;
import org.apache.commons.lang.StringUtils;

import models.Message;
import models.User;
import models.Message.Type;

import play.mvc.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Codec;
import play.cache.Cache;

import static controllers.WrapRender.*;

/**
 * Created by yehuizhang on 14-9-1.
 */
public class Register extends CommonRender {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    public static void registerPage(){
        String randomID = Codec.UUID();
        render("Register/register.html", randomID);
    }

    public static void thirdPartRegister(String uid, int type){
        render("Register/thirdpartregister.html", uid, type);
    }

    public static void isEmailRegister(final String email){
        emptyEmail(email);
        existedEmail(email);
        RenderSuccess();
    }

    public static void doRegister(final String code, final String randomID, final String email, final String userName,final String password){
        String randomCode = (String)Cache.get(randomID);
        logger.error("randomCode:" + randomCode + "  code:" + code);
        if(StringUtils.isEmpty(randomCode) || !code.equalsIgnoreCase(randomCode)){
            RenderFailed("验证码输入错误");
        }
        emptyEmail(email);
        existedEmail(email);

        emptyUserName(userName);
        existedUserName(userName);

        lengthPassword(password);

        User user = new User(userName, email, password);

        Long userId = user.firstSave();

        if(userId > 0L) {
            successEnter(userId.toString(), userName);
            Message sysMsg = new Message("系统消息", user.getUserName(), "恭喜，您已经成功注册！", Type.SYSTEM_MES);
            sysMsg.insert();
            RenderSuccess();
        }else{
            RenderFailed("用户数据插入出错，数据库异常！");
        }
    }

    public static void doRegisterThirdPart(final String email, final String password, final String uid, int type){
        emptyEmail(email);

        lengthPassword(password);

        User user = User.userLogin(email, password);

        if(user != null){
            successEnter(user.getId().toString(), user.getUserName());
            RenderSuccess();
        }

        user = new User(email, email, password);

        Long userId = user.firstSave();
        logger.info("userID:" + userId +"   uid:" + uid);
        if(userId > 0L) {
            boolean isSuccess = false;
            logger.info("type:" + type);
            switch(type){
                case 1:
                    logger.info("login 1");
                    isSuccess = WeiboUser.setUserId(userId.toString(), uid);
                    break;
                case 2:
                    logger.info("login 2");
                    isSuccess = DoubanUser.setUserId(userId.toString(), uid);
                    break;
            }

            if(!isSuccess){
                RenderFailed("三方登陆用户插入对应的用户id失败");
            }
            successEnter(userId.toString(), email);
            Message sysMsg = new Message("系统消息", user.getUserName(), "恭喜，您已经成功注册！", Type.SYSTEM_MES);
            sysMsg.insert();
            RenderSuccess();
        }else{
            RenderFailed("用户数据插入出错，数据库异常！");
        }
    }

    private static void lengthPassword(final String password){
        if(password.length() < 6){
            RenderFailed("输入的密码太短，请输入密码的长度大于6个字符");
        }
        return ;
    }

    private static void lengthUserName(final String userName){
        if(userName.length()< 2 || userName.length() > 16){
            RenderFailed("用户名长度不满足要求，请确保输入的用户名长度在2-16字符之间!");
        }
        return;
    }

    private static void emptyUserName(final String userName){
        if(StringUtils.isEmpty(userName)){
            RenderFailed("用户名为空，请输入用户名！");
        }
        return;
    }

    private static void existedUserName(final String userName){
        long id = User.findIfExistedByUserName(userName);
        if(id > 0){
            RenderFailed("用户名已存在，请输入其它用户名！");
        }
        return ;
    }

    private static void emptyEmail(final String email){
        if(StringUtils.isEmpty(email)){
            RenderFailed("邮箱号为空，请输入邮箱号！");
        }
        return;
    }

    private static void existedEmail(final String email){
        long id = User.findEmailExisted(email);
        if(id > 0){
            RenderFailed("邮箱已存在，请输入其它邮箱号");
        }
        return;
    }

    @Before(only = {"Register.thirdPartRegister"})
    public static void checkThirdpartRegister(){
        logger.error("check third part login");
        String sign = flash.get("thirdpart_register");
        if(StringUtils.isEmpty(sign)){
            redirect("/Application/index");
        }
    }

}

