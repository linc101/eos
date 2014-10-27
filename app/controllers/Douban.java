package controllers;

import models.DoubanUser;
import models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.LoadConfig;
import douban.HttpClientPost;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import douban.AccessToken;

import static controllers.WrapRender.*;

/**
 * Created by yehuizhang on 14/10/25.
 */
public class Douban extends CommonRender{
    public static Logger log = LoggerFactory.getLogger(Douban.class);

    public static void doubanAuthCallBack(String code) {
        if(StringUtils.isEmpty(code)){
            redirect("/");
        }else{
            Map<String, String> params = new HashMap<String, String>();
            params.put("client_id", LoadConfig.getValue("douban_client_id"));
            params.put("client_secret", LoadConfig.getValue("douban_secret"));
            params.put("redirect_uri", LoadConfig.getValue("douban_redirect_url"));
            params.put("grant_type", "authorization_code");
            params.put("code", code);

            try {
                String res = new HttpClientPost(LoadConfig.getValue("douban_token"), params).sendPost();
                log.error("res:" + res);
                if(StringUtils.isEmpty(res)){
                    redirect("/");
                }else{
                    JSONObject json = JSONObject.fromObject(res);
                    String doubanUserId = json.getString("douban_user_id");
                    String accessToken = json.getString("access_token");
                    String doubanUsername = json.getString("douban_user_name");
                    String expiresIn = json.getString("expires_in");
                    String refreshToken = json.getString("refresh_token");
                    log.error(json.toString());
                    //查看本地数据库是否有豆瓣用户
                    DoubanUser dbUser = DoubanUser.finDoubanUserById(doubanUserId);
                    if(dbUser == null){
                        //如果没有则插入到本地数据库
                        DoubanUser doubanUser = new DoubanUser(accessToken, doubanUserId, doubanUsername, expiresIn, refreshToken);
                        doubanUser.insert();
                        flash.put("thirdpart_register","true");
                        redirect("/Register/thirdPartRegister?uid=" + doubanUserId + "&type=2");
                    }else{
                        //如果有则取出用户id
                        long userId = dbUser.getUserId();
                        log.error("id:" + userId);
                        if(userId <= 0){
                            //如果用户不存在则进行三方登陆，来验证用户
                            flash.put("thirdpart_register","true");
                            redirect("/Register/thirdPartRegister?uid=" + doubanUserId + "&type=2");
                        }else{
                            //如果用户存在则更新本地用户
                            dbUser.setAccessToken(accessToken);
                            dbUser.setUsername(doubanUsername);
                            dbUser.setExpirsein(expiresIn);
                            dbUser.setRefreshToken(refreshToken);
                            boolean isSuccess = dbUser.update();
                            if(isSuccess == false){
                               RenderFailed("豆瓣三方插入本地用户失败!");
                            }

                            //查找对应的用户
                            User user = User.findById(userId);

                            if(user == null){
                                //如果对应的用户不存在，则继续进行注册
                                flash.put("thirdpart_register","true");
                                redirect("/Register/thirdPartRegister?uid=" + doubanUserId + "&type=2");
                            }else{
                                //如果存在则直接登陆
                                successEnter(""+userId, user.getUserName());
                                redirect("/Application/index");
                            }
                        }
                    }


                }
            } catch (Exception e) {
                log.error(e.getMessage(), "请求发送出错");
                redirect("/");
            } finally{

            }
        }
    }

    private static void setAccessToken(JSONObject json, AccessToken accessToken){
        accessToken.setAccessToken(json.getString("access_token"));
        accessToken.setDoubanUserId(json.getString("douban_user_id"));
        accessToken.setDoubanUsername(json.getString("douban_user_name"));
        accessToken.setExpiresIn(json.getString("expires_in"));
        accessToken.setRefreshToken(json.getString("refresh_token"));
    }

    public static void userLoginByDouban(){
        StringBuffer sb = new StringBuffer(LoadConfig.getValue("douban_auth"));
        sb.append("?client_id=");
        sb.append(LoadConfig.getValue("douban_client_id"));
        sb.append("&redirect_uri=");
        sb.append(LoadConfig.getValue("douban_redirect_url"));
        sb.append("&response_type=code");
        redirect(sb.toString());
    }
}
