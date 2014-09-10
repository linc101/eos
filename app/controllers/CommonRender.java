package controllers;

import General.Result;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import play.mvc.Controller;
import play.mvc.Before;

import static config.Config.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;

import java.util.Date;
/**
 * Created by yehuizhang on 14-9-3.
 */
public class CommonRender extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(CommonRender.class);

    protected static void RenderFailed(String message){
        Result res = new Result(message);
        renderJSON(JSONObject.fromObject(res));
    }

    @Before(only = {"UserCenter.userCenter","UserCenter.addExperience"})
    public static void checkAccess(){
        String userName = session.get(USER_NAME);
        logger.info("checkAccess------userName:" + userName );
        if(StringUtils.isEmpty(userName)){
            redirect("/Login/login");
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

    protected static void successRegister(final String userName){
        session.put(USER_NAME, userName);
        response.setCookie(USER_NAME, userName, "1d");
    }

    protected static void successLogin(final String userName){
        session.put(USER_NAME, userName);
        response.setCookie(USER_NAME, userName, "1d");
    }

    protected static void removeSession(final String userName){
        session.remove(USER_NAME);
        response.removeCookie(USER_NAME);
    }

    protected static User getUser(){
        String userName = session.get(USER_NAME);
        logger.info("-----------session userName:" + userName);
        if(StringUtils.isEmpty(userName)){
            RenderFailed("");
        }
        User user = User.finUserByName(userName);

        if(user == null){
            RenderFailed("数据库异常，请检查数据库");
        }
        return user;
    }
}
