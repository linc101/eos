package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;

import thirdpartlogin.ThirdpartLoginFactory;
/**
 * Created by yehuizhang on 14/10/25.
 */
public class ThirdpartLogin extends CommonRender {
    private static Logger log = LoggerFactory.getLogger(ThirdpartLogin.class);

    private static final String WEIBO = "weibo";

    private static final String DOUBAN = "douban";

    private static final ThirdpartLoginFactory thirdpartLoginFactory = ThirdpartLoginFactory.getInstance();

    public static void index(){

    }

    public static void thirdpartLogin(String loginType){
       if(StringUtils.equalsIgnoreCase(loginType, WEIBO)){
            WeiboLogin weiboLogin = thirdpartLoginFactory.createThirdpartLogin(WeiboLogin.class);

       }
    }
}
