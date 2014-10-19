package controllers;

import models.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;
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

        User user = User.userLogin(email, password);

        log.info("email :" + email + "  password:" + password);

        if(user == null){
            RenderFailed("邮箱不存在或者密码错误！");
        }

        successEnter(user.getId().toString(), user.getUserName());

        RenderSuccess();
    }

    public static void userLogout(){
        User user = getUser();
        removeSession();
        RenderSuccess();
    }

    public static void main(String [] args) throws WeiboException, IOException{
        Oauth oauth = new Oauth();
        BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
        System.out.println(oauth.authorize("code"));
        System.out.print("Hit enter when it's done.[Enter]:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();
        log.info("code: " + code);
        try{
            System.out.println(oauth.getAccessTokenByCode(code));
        } catch (WeiboException e) {
            if(401 == e.getStatusCode()){
                log.info("Unable to get the access token.");
            }else{
                e.printStackTrace();
            }
        }
    }

}
