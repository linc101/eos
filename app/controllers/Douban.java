package controllers;

import models.DoubanUser;
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
                if(StringUtils.isEmpty(res)){
                    redirect("/");
                }else{
                    JSONObject json = JSONObject.fromObject(res);
                    DoubanUser doubanUser = new DoubanUser(json.getString("access_token"), json.getString("douban_user_id"), json.getString("douban_user_name"), json.getString("expires_in"),
                            json.getString("refresh_token")
                    );
                    
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                redirect("/");
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
