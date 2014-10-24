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

import static controllers.WrapRender.*;
import static controllers.WeiBo.*;

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