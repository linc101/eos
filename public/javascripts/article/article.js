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
            article.init.container = container;
            article.init.articleId = articleId;
            article.show.showArticle();
        }
    })

    article.show = article.show || {};
    article.show = $.extend(article.show, {
        showArticle:function(){
            var container = article.init.container;
            var title = container.find(".article-title");
            var content = container.find(".article-content");
            title.html(article.init.articleId);
            content.html(article.init.articleId);
        }
    });
})(jQuery, window));