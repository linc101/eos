package controllers;

import play.mvc.Controller;

/**
 * Created by yehuizhang on 14-9-1.
 */
public class Register extends Controller {
    public static void registerPage(){
        render("Register/register.html");
    }
}
