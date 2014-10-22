package controllers;

import config.Config;
import org.apache.commons.lang.StringUtils;
import play.*;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import models.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

public class Application extends CommonRender {
    private static Logger log = LoggerFactory.getLogger(Application.class);


    public static void index(String code) {
        if(StringUtils.isEmpty(code))
            render();
        else{
            try {
                setWeiboUserInfoByCode(code);
            }catch(WeiboException e){
                log.info(e.getMessage(), e);
            }
            redirect("/Application/index");
        }
    }

    private static void setWeiboUserInfoByCode(String code) throws WeiboException{
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
            return;
        }
        weiboUser = new WeiboUser(token, weibo_user.getScreenName());
        boolean isSuccess = weiboUser.jdbcSave();
        if(!isSuccess){
            RenderFailed("微博三方登陆用户失败");
        }
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

    public static void test(){
        render("/test.html");
    }

    public static void findCountMsg(){
        User user = getUser();

        int count = Message.findCountUnreadMsg(user.getUserName());

        RenderSuccess(count);
    }
}