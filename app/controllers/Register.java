package controllers;

import General.Result;
import org.apache.commons.lang.StringUtils;
import play.mvc.Controller;
import net.sf.json.JSONObject;

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

    }
}

