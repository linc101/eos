/**
 * Created by uttp on 14-9-10.
 */

var EOS = EOS ||  {};

((function($, window){
    EOS.article = EOS.article || {};

    var article = EOS.article;

    article.init = article.init || {};
    article.init = $.extend(article.init, {
        doInit:function(container, articleId){
            console.log(articleId);
            article.init.container = container;
            article.init.articleId = articleId;
            article.show.showArticle();
            article.show.submitReview();
            article.init.initReview();
        },
        initReview:function(){
            var container = article.init.container;
            if(EOS.userId != null){

                container.find(".article-wrap .add-review").removeClass("hidden");
            }else{
                container.find(".article-wrap .not-login").removeClass("hidden");
            }
        }
    })

    article.show = article.show || {};
    article.show = $.extend(article.show, {
        showArticle:function(){
            var container = article.init.container;
            var title = container.find(".article-title .article-title-h1");
            var content = container.find(".article-content");
            var articleUsername = container.find(".article-username");
            var bigTitle = container.find(".big-title");
            var dateTime = container.find(".article-wrap .article-meta .datetime");
            var scanTime = container.find(".article-wrap .scan-times");
            $.ajax({
                type:"GET",
                url:'/Article/getArticleById',
                data:{articleId:article.init.articleId},
                success:function(dataJson){
                    if(!dataJson.success){
                        window.location.href = "404page.html";
                        return;
                    }
                    content.html(dataJson.res.article);
                    title.html(dataJson.res.title);
                    articleUsername.html(dataJson.res.userName);
                    bigTitle.html("经验分享之----" + dataJson.res.title);
                    var nowDate = new Date();
                    var nowYear = nowDate.getFullYear();
                    var thatDate = new Date(dataJson.res.createTs);
                    var thatYear = thatDate.getFullYear();
                    if(nowYear == thatYear)
                        dateTime.html(thatDate.Format("MM月dd日"));
                    else{
                        dateTime.html(thatDate.Format("yyyy年MM月dd日"));
                    }
                    scanTime.html(dataJson.res.scanTimes + "&nbsp;浏览");
                }
            })
//            title.html();
//            content.html(article.init.articleId);
        },
        submitReview:function(){
            var container = article.init.container.find(".add-review");
            container.find(".submit_btn").unbind().click(function(){
                var content = container.find(".review-textarea").val().trim();
                if(content == null || content == ""){
                    EOS.util.UIAssert("评论内容为空，请填写评论内容");
                    return;
                }

                var reviewer = EOS.userName;
                $.ajax({
                    type:'GET',
                    url:'/Article/saveReviewByDefault',
                    data:{
                        expId:article.init.articleId,
                        reviewer:EOS.userName,
                        content:content
                    },
                    success:function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message);
                            window.location.reload();
                            return;
                        }
                    }
                })
            })
        }
    });
})(jQuery, window));