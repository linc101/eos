package controllers;

import config.Config;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends CommonRender {

    public static void index() {
        render();
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