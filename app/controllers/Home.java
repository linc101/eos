package controllers;

import play.libs.Images;
/**
 * Created by uttp on 14-10-12.
 */
public class Home extends CommonRender{
    public static void captche(){
        Images.Captcha captcha = Images.captcha();
    }
}
