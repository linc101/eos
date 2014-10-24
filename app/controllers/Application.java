package controllers;

import models.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static controllers.WrapRender.*;

public class Application extends CommonRender {
    private static Logger log = LoggerFactory.getLogger(Application.class);

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