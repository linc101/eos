package controllers;

import models.User;
import models.WeiboUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.Users;
import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

import static controllers.WrapRender.*;

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

    public static void userLoginByweibo() throws WeiboException, IOException{
        redirect("https://api.weibo.com/oauth2/authorize?client_id=2359627633&redirect_uri=http://www.sharexperience11.com/Application/index&response_type=code");
    }

    public static void getWeiboUserInfoByCode(String code) throws WeiboException, IOException{
        Oauth oauth = new Oauth();
        oauth.authorize("code");
        AccessToken token = oauth.getAccessTokenByCode(code);

        String accessToken = token.getAccessToken();
        log.info("access token:" + token.toString());
        String uid = token.getUid();
        Users um = new Users(accessToken);
        weibo4j.model.User weibo_user = um.showUserById(uid);
        //获取微博登陆用户信息
        WeiboUser weiboUser = WeiboUser.finWeibouserByUID(token.getUid());
        //如果数据库中存在微博登陆信息
        if(weiboUser != null){
            //更新登陆微博信息
            updateWeiboUser(token, weibo_user, weiboUser);
            RenderSuccess(weiboUser);
        }
        weiboUser = new WeiboUser(token, weibo_user.getScreenName());
        boolean isSuccess = weiboUser.jdbcSave();
        if(isSuccess){
            RenderSuccess(weiboUser);
        }else{
            RenderFailed("微博三方登陆用户失败");
        }
        log.info("weibo user info:" + weibo_user.toString());
    }

    private static void updateWeiboUser(AccessToken token, weibo4j.model.User weibo_user, WeiboUser weiboUser){
        weiboUser.setAccessToken(token.getAccessToken());
        weiboUser.setWeiboUsername(weibo_user.getScreenName());
        weiboUser.setUpdateTs(System.currentTimeMillis());
        weiboUser.setExpiresTs(Long.parseLong(token.getExpireIn()));
        boolean isSuccess = weiboUser.update();
        if(!isSuccess){
            RenderFailed("跟新微博登陆信息失败！");
        }
    }

    public static void findUserConnectedCount(){

    }
    public static void main(String [] args) throws WeiboException, IOException{
        Oauth oauth = new Oauth();
        oauth.publishWeibo("2.00mKFyvBNwkgZC44c006dc1903pEOc", "test only for test!!!!!");
    }

}
