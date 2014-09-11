package controllers;

import models.Experience;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ContentCovert;
/**
 * Created by yehuizhang on 14-9-9.
 */
public class Article extends CommonRender{
    private static final Logger log = LoggerFactory.getLogger(Article.class);

    public static void article(final long articleId){

        if(articleId <= 0){
            render("404page.html");
        }
        render("/article/article.html", articleId);
    }

    public static void getArticleById(final long articleId){
        if(articleId <= 0){
            render("404page.html");
        }
        Experience exp = Experience.findExpById(articleId);
        if(exp == null){
            render("404page.html");
        }
        exp.setScanTimes(exp.getScanTimes() + 1);
        boolean isIncreaseSuccess = exp.jdbcSave();
        if(isIncreaseSuccess == false){
            log.error("increase scan time failed!");
        }
        String newArticle = ContentCovert.convertNewLineCharToBR(exp.getArticle());
        exp.setArticle(newArticle);
        RenderSuccess(exp);
    }



}
