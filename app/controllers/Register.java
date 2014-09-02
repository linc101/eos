package controllers;

import General.Result;

import net.sf.json.JSON;
import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

import net.sf.json.JSONObject;

import models.User;

/**
 * Created by yehuizhang on 14-9-1.
 */
public class Register extends Controller {
    public static void registerPage(){
        render("Register/register.html");
    }

    public static void isEmailRegister(String email){
        if(StringUtils.isEmpty(email)){
            Result res = new Result("邮箱号为空，请输入邮箱号！");
            renderJSON(JSONObject.fromObject(res));
        }

        if(User.isEmailExisted(email)){
            Result res = new Result("邮箱已存在，请输入其它邮箱号！");
            renderJSON(JSONObject.fromObject(res));
        }

        Result res = new Result();
        renderJSON(JSONObject.fromObject(res));

    }

    public static void doRegister(){

    }
}

