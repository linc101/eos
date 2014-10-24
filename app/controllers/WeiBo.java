package controllers;

import models.User;
import models.WeiboUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

import java.io.IOException;

import static controllers.WrapRender.*;

/**
 * Created by yehuizhang on 14/10/23.
 */
public class WeiBo extends CommonRender{
    private static Logger log = LoggerFactory.getLogger(WeiBo.class);

    public static void index(){
        render("/weibo/index.html");
    }

    /*
     *用户授权后的回调函数
     *即用户成功授权后将会调用这个函数
     */
    public static void WeiboAuthCallBack(String code){
        try {
            setWeiboUserInfoByCode(code);
        }catch(WeiboException e){
            log.info(e.getMessage(), e);
        }
    }

    /*
     *微博第三方登陆入口
     * 授权机制是采用的oahth2
     * client_id：应用的appkey
     * redirect_uri：授权成功后返回的uri
     * 然后根据redirect_uri的返回code值
     */
    public static void userLoginByweibo(){
        redirect("https://api.weibo.com/oauth2/authorize?client_id=2359627633&" +
                "redirect_uri=http://www.sharexperience11.com/weiboAuth&response_type=code");
    }

    /*
     *微博成功授权后得到cde然后得到access_token来获取用户的信息
     */
    protected static void setWeiboUserInfoByCode(String code) throws WeiboException {
        Oauth oauth = new Oauth();
        oauth.authorize("code");
        AccessToken token = oauth.getAccessTokenByCode(code);
        
        String accessToken = token.getAccessToken();
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
