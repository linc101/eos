package controllers;

import General.Result;

import net.sf.json.JSONObject;

import play.mvc.Controller;

import config.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by yehuizhang on 14-9-3.
 */
public class CommonRender extends Controller {
    Logger logger = LoggerFactory.getLogger(CommonRender.class);

    protected static void RenderFailed(String message){
        Result res = new Result(message);
        renderJSON(JSONObject.fromObject(res));
    }

    protected static void RenderSuccess(){
        Result res = new Result();
        renderJSON(JSONObject.fromObject(res));
    }

    protected static void successRegister(final String userName){
        session.put(Config.USER_NAME, userName);
        response.setCookie(Config.USER_NAME, userName);
    }
}
