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
                        dateTime.html(thatDate.Format("MM-dd"));
                    else{
                        dateTime.html(thatDate.Format("yyyy-MM-dd"));
                    }
                    scanTime.html(dataJson.res.scanTimes + "&nbsp;浏览");
                }
            })
//            title.html();
//            content.html(article.init.articleId);
        },
        showArticleMeta:function(){
            $.ajax({
                type:'GET',
                url:'/Review/'
            })
        }
    });
})(jQuery, window));