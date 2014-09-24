package controllers;

import models.Experience;
import models.Message;
import models.Review;
import models.Message.Type;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ContentCovert;

import java.util.List;
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

    public static void saveReviewByDefault(final String reviewer, final long expId, final String content){
        if(StringUtils.isEmpty(reviewer)){
            RenderFailed("评论人信息不存在！");
        }

        if(StringUtils.isEmpty(content)){
            RenderFailed("评论为空，请填写评论内容！");
        }

        if(expId <= 0L){
            RenderFailed("id异常！");
        }

        long reviewId = new Review(expId, reviewer, content).firstInsert();

        if(reviewId <= 0L){
            RenderFailed("数据库异常！");
        }

        boolean isIncReviewSuccess = Experience.increaseReviewTimes(expId);

        if(isIncReviewSuccess){
            String reviewed = Experience.getExperienceUsernameById(expId);
            Message msg = new Message(reviewer, reviewed, content, Type.COMMENT_MES);
            msg.setExpId(expId);
            msg.insert();
        }

        if(!isIncReviewSuccess){
            RenderFailed("数据库异常");
        }

        RenderSuccess();
    }

    public static void saveReviewReply(final String reviewed, final String reviewer, final long expId, final String content){
        if(StringUtils.isEmpty(reviewed)){
            RenderFailed("回复被评论人的信息不存在！");
        }

        if(StringUtils.isEmpty(reviewer)){
            RenderFailed("评论人信息不存在！");
        }

        if(StringUtils.isEmpty(content)){
            RenderFailed("评论为空，请填写评论内容！");
        }

        if(expId <= 0L){
            RenderFailed("id异常！");
        }

        long reviewId = new Review(expId, reviewed, reviewer, content).firstInsert();

        if(reviewId <= 0L){
            RenderFailed("数据库异常！");
        }
        boolean isIncReviewSuccess = Experience.increaseReviewTimes(expId);

        if(isIncReviewSuccess){
//            String reviewed = Experience.getExperienceUsernameById(expId);
            Message msg = new Message(reviewer, reviewed, content, Type.COMMENT_MES);
            msg.setExpId(expId);
            msg.insert();
        }

        if(!isIncReviewSuccess){
            RenderFailed("数据库异常");
        }

        RenderSuccess();
    }

    public static void deleteReview(final long reviewId, final long expId){
        if(reviewId <= 0L){
            RenderFailed("评论id异常");
        }

        if(expId <= 0L){
            RenderFailed("文章id异常");
        }

        boolean isSuccess = Review.deleteReview(reviewId, expId);
        if(isSuccess){
            boolean isDecreaseSuccess = Experience.decreaseReviewTimes(expId);
            if(!isDecreaseSuccess){
                RenderFailed("数据库异常");
            }
            RenderSuccess();
        }else{
            RenderFailed("数据库异常！");
        }
    }

    public static void showAllReviewByExpId(final long expId){
        if(expId <= 0L){
            RenderFailed("id异常！");
        }
        List<Review> reviews = Review.findAllReviewByExp(expId);
        RenderSuccess(reviews);

    }

}
