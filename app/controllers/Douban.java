package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.LoadConfig;
/**
 * Created by yehuizhang on 14/10/25.
 */
public class Douban extends CommonRender{
    public static Logger log = LoggerFactory.getLogger(Douban.class);

    public static void doubanAuthCallBack(String code){

    }

    public static void userLoginByDouban(){
        StringBuffer sb = new StringBuffer(LoadConfig.getValue("douban_auth"));
        sb.append("?client_id=");
        sb.append(LoadConfig.getValue("douban_client_id"));
        sb.append("&redirect_uri=");
        sb.append(LoadConfig.getValue("douban_redirect_url"));
        sb.append("&response_type=token");
        redirect(sb.toString());
    }
}
