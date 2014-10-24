package controllers;

import models.User;
import models.WeiboUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

import static controllers.WrapRender.*;

/**
 * Created by yehuizhang on 14/10/23.
 */
public class WeiBo extends CommonRender{
    private static Logger log = LoggerFactory.getLogger(WeiBo.class);

    public static void index(){
        render("/weibo/index.html");
    }

    protected static void setWeiboUserInfoByCode(String code) throws WeiboException {
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
            long userId = weiboUser.getUserId();
            log.error("userId:" + userId);
            if(userId > 0L){
                User user = User.findUserById(userId);
                successEnter(""+userId, user.getUserName());
                redirect("/Application/index");
            }

            redirect("/Register/thirdPartRegister?uid="+uid);
            return;
        }
        weiboUser = new WeiboUser(token, weibo_user.getScreenName());
        boolean isSuccess = weiboUser.jdbcSave();
        if(!isSuccess){
            RenderFailed("微博三方登陆用户失败");
        }
        redirect("/Register/thirdPartRegister?uid="+uid);
    }

    protected static void updateWeiboUser(AccessToken token, weibo4j.model.User weibo_user, WeiboUser weiboUser){
        weiboUser.setAccessToken(token.getAccessToken());
        weiboUser.setWeiboUsername(weibo_user.getScreenName());
        weiboUser.setUpdateTs(System.currentTimeMillis());
        weiboUser.setExpiresTs(Long.parseLong(token.getExpireIn()));
        boolean isSuccess = weiboUser.update();
        if(!isSuccess){
            RenderFailed("更新微博登陆信息失败！");
        }
    }
}
