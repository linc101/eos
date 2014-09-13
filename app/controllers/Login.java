package controllers;

import models.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by yehuizhang on 14-9-4.
 */
public class Login extends CommonRender {
    private static final Logger log = LoggerFactory.getLogger(Login.class);

    public static void login(){
        render("/Login/login.html");
    }
    public static void userLogin(final String email, final String password){
        if(StringUtils.isEmpty(email)){
            RenderFailed("请输入用户邮箱！");
        }

        if(StringUtils.isEmpty(password)){
            RenderFailed("请输入用户密码");
        }

        String res = User.userLogin(email, password);

        log.info("email :" + email + "  password:" + password + "    res:" + res);

        if(StringUtils.isEmpty(res)){
            RenderFailed("邮箱不存在或者密码错误！");
        }

        successLogin(email, password);

        RenderSuccess();
    }

    public static void userLogout(){
        User user = getUser();
        removeSession(user.getUserName());
        RenderSuccess();
    }

}
