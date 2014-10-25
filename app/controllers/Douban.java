package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.LoadConfig;

import org.apache.commons.lang.StringUtils;

/**
 * Created by yehuizhang on 14/10/25.
 */
public class Douban extends CommonRender{
    public static Logger log = LoggerFactory.getLogger(Douban.class);

    public static void doubanAuthCallBack(String code) {
        if(StringUtils.isEmpty(code)){
            redirect("/");
        }else{

        }
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
