package controllers;

import General.Result;

import config.Config;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import play.mvc.Controller;
import play.mvc.Before;

import static config.Config.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;
import play.mvc.Http;
import util.PageOffset;

import java.util.Date;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yehuizhang on 14-9-3.
 */
public class CommonRender extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(CommonRender.class);

    protected static void RenderFailed(String message){
        Result res = new Result(message);
        renderJSON(JSONObject.fromObject(res));
    }

    @Before(only = {"UserCenter.userCenter","UserCenter.addExperience", "Login.userLogout",
            "UserCenter.showAllMyExps"})
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

    protected static void RenderSuccess(){
        Result res = new Result();
        renderJSON(JSONObject.fromObject(res));
    }

    protected static void RenderSuccess(Object obj){
        Result<Object> res = new Result<Object>(obj);
        renderJSON(JSONObject.fromObject(res));
    }

    protected static void RenderSuccess(List list, int count, PageOffset offset){
        Result<List> res = new Result<List>(list,count,offset);
        renderJSON(JSONObject.fromObject(res));
    }

    protected static void RenderSuccess(List<Object> listObj){
        Result<List<Object>> res = new Result<List<Object>>();
        renderJSON(JSONObject.fromObject(res));
    }

    protected static void successEnter(final String userId, final String userName){
        session.put(USER_ID, userId);
        response.setCookie(USER_ID, userId, "1d");
        response.setCookie(USER_NAME, URLEncoder.encode(userName), "14d");
    }

    protected static void removeSession(){
        session.remove(USER_ID);
        response.removeCookie(USER_ID);
    }

    protected static User getUser(){

        User user = (User)request.args.get(USER);

        logger.info("------------------------------------------user:" + user);
        if(user == null){
            RenderFailed("数据库异常，请检查数据库");
        }
        return user;
    }
}
