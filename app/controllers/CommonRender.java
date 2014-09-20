package controllers;

import General.Result;

import config.Config;
import models.Experience;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Controller;
import play.mvc.Before;

import static config.Config.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;
import play.mvc.Http;
import util.PageOffset;

import java.io.IOException;
import java.util.ArrayList;
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
        renderJSON(wrapObject(res));
    }

    @Before(only = {"UserCenter.userCenter","UserCenter.addExperience", "Login.userLogout",
            "UserCenter.showAllMyExps", "UserCenter.doAddExperience", "AccountSetting.changePW",
            "AccountSetting.picUpload"})
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
        logger.info("----------common render :" + user.toString());
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
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(Object obj){
        Result<Object> res = new Result<Object>(obj);
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(List list, int count, PageOffset offset){
        Result<List> res = new Result<List>(list,count,offset);
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(List<Object> listObj){
        Result<List<Object>> res = new Result<List<Object>>();
        renderJSON(wrapObject(res));
    }

    protected static JSONObject wrapObject(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String s = mapper.writeValueAsString(obj);
            JSONObject jsonObject = JSONObject.fromObject(s);
            return jsonObject;
        } catch(IOException e){
            logger.error(e.getMessage(), e);
            return new JSONObject();
        }
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

    public static void Test(){
        System.out.println("--------test only test for Json----------------------- ");
        List<Experience> listExps = new ArrayList<Experience>();
        Experience exp = new Experience("1", "2", "3");
        listExps.add(exp);
        Experience expTemp = new Experience("4", "5", "6");
        listExps.add(expTemp);
        ObjectMapper mapper = new ObjectMapper();
        Result<List<Experience>> res = new Result<List<Experience>>(listExps);

        String s = null;
        try {
            s = mapper.writeValueAsString(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        JSONObject jsonObject = JSONObject.fromObject(s);
        renderJSON(new JSONObject());
    }
}
