package controllers;

import models.User;
import models.WeiboUser;

import thirdpartlogin.IThirdpartLogin;

import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static controllers.WrapRender.RenderFailed;

/**
 * Created by yehuizhang on 14/10/25.
 */
public class WeiboLogin extends CommonRender implements IThirdpartLogin {
    private static Logger log = LoggerFactory.getLogger(WeiboLogin.class);

    @Override
    public void auth(String url) {
        redirect(url);
    }

    @Override
    public void setThirdpartInfo(String authCode) throws WeiboException{
        Oauth oauth = new Oauth();
        oauth.authorize("code");
        AccessToken token = oauth.getAccessTokenByCode(authCode);

        //得到授权的微博用户信息
        String accessToken = token.getAccessToken();
        String uid = token.getUid();
        Users um = new Users(accessToken);
        weibo4j.model.User weibo_user = um.showUserById(uid);

        //查找此用户是否授权过且数据保存在本地
        WeiboUser weiboUser = WeiboUser.finWeibouserByUID(token.getUid());

        //如果授权过，且数据保存下来了
        if(weiboUser != null){
            //更新登陆微博信息
            updateWeiboUser(token, weibo_user, weiboUser);
            //是否关联了用户名
            long userId = weiboUser.getUserId();
            log.error("userId:" + userId);
            //如果已经关联了用户
            if(userId > 0L){
                User user = User.findUserById(userId);
                if(user != null) {
                    successEnter("" + userId, user.getUserName());
                    redirect("/Application/index");
                }
            }
            flash.put("thirdpart_register","true");
            redirect("/Register/thirdPartRegister?uid="+uid);
            return;
        }

        weiboUser = new WeiboUser(token, weibo_user.getScreenName());
        boolean isSuccess = weiboUser.jdbcSave();
        if(!isSuccess){
            RenderFailed("微博三方登陆存储用户信息到本地失败！");
        }
        flash.put("thirdpart_register","true");
        redirect("/Register/thirdPartRegister?uid="+uid);
    }

    //跟新用户数据主要是accesstoken更新，微博用户名的更新以及时间的更新
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
