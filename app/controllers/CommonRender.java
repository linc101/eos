package controllers;

import play.libs.Codec;
import play.mvc.Controller;
import play.mvc.Before;
import play.cache.Cache;
import play.libs.Images;

import static config.Config.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;
import play.mvc.Http;
import play.mvc.Http.Cookie;

import java.net.URLEncoder;
import java.net.URLDecoder;

import java.util.Map;
import java.util.Set;

import static controllers.WrapRender.*;

/**
 * Created by yehuizhang on 14-9-3.
 */
public class CommonRender extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(CommonRender.class);

    /*
     *@Before是在每次请求这些url的时候，需要先调用checkAccess()方法。
     *判断请求request的args是否有USER参数，如果没有则存起来，方便需要用户名的时候得到用户信息
     */
    @Before(only = {"UserCenter.userCenter","UserCenter.addExperience", "Login.userLogout",
            "UserCenter.showAllMyExps", "UserCenter.doAddExperience", "AccountSetting.changePW",
            "AccountSetting.picUpload", "AccountSetting.showUserInfo", "AccountSetting.showMsg",
            "Application.findCountMsg", "AccountSetting.myMessage", "AccountSetting.changeEmail",
            "AccountSetting.changeBriefIntroduction", "AccountSetting.completeInfo", "AccountSetting.getPicPath",
            "AccountSetting.changePic"})
    public static void checkAccess(){
        User user = (User)request.args.get(USER);

        if(user!= null){
            return;
        }

        String userId = getUserIdFromCookie();
        if(userId == null){
            redirect("/Login/login");
        }

        user = User.finUserById(userId);
        request.args.put(USER, user);
    }

    /*
     *每次请求时都会从客户端得到session和cookie的值， 故可以得到每次请求的cookie和session的值
     */
    private static String getUserIdFromCookie(){
        String userId = session.get(USER_ID);
        if(userId != null){
            return userId;
        }
        Http.Cookie cookie = request.cookies.get(USER_ID);
        if(cookie != null){
            return cookie.value;
        }else{
            return null;
        }
    }

    /*
     *每此登陆成功后设置cookie的值，这里的cookie值包括userid and username
     */
    protected static void successEnter(final String userId, final String userName){
        session.put(USER_ID, userId);
        response.setCookie(USER_ID, userId, "1d");
        response.setCookie(USER_NAME, URLEncoder.encode(userName), "14d");
    }

    protected static void removeSession(){
        session.remove(USER_ID);
        response.removeCookie(USER_ID);
        response.removeCookie(PAGE_SIZE);
    }

    protected static User getUser(){
        User user = (User)request.args.get(USER);
        if(user == null){
            RenderFailed("获取user出错！数据库异常，请检查数据库");
        }
        return user;
    }

    /*
     *产生验证码
     *
     *实现的原理是得到唯一的编码，然后得到的验证码字符串保存在cache中
     *这样就可以与传回来的唯一编码进行比较
     *
     */
    public static void getCaptcha(String randomID){
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#FF0C19");
        Cache.set(randomID, code, "10mn");
        renderBinary(captcha);
    }

    public static void changeCaptcha(){
        String randomID = Codec.UUID();
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#FF0C19");
        Cache.set(randomID, code, "10mn");
        RenderSuccess(randomID);
    }

    @Before
    public static void Test(){
        Map<String, Cookie> cookiesMap = request.cookies;
        Set<Map.Entry<String, Cookie>> entrys = cookiesMap.entrySet();
        for(Map.Entry<String, Cookie> entry:entrys){
            String cookieValue = entry.getValue().value;
            entry.getValue().value = URLDecoder.decode(entry.getValue().value);
        }
    }
}
