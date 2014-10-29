package controllers;

import models.ArticleTags;
import play.libs.Images;

import java.util.List;

import static controllers.WrapRender.*;
/**
 * Created by uttp on 14-10-12.
 */
public class Home extends CommonRender{
    public static void captche(){
        Images.Captcha captcha = Images.captcha();
    }

    public static void showTags(){
        List<ArticleTags> listArticleTags = ArticleTags.selectHotTags();
        RenderSuccess(listArticleTags);
    }
}
