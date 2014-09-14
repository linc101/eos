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
            article.show.showAllReviews();
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
                type:"POST",
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
                    dateTime.html(EOS.util.dealDate(dataJson.res.createTs));
                    scanTime.html(dataJson.res.scanTimes + "&nbsp;浏览");
                }
            })
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
                            return;
                        }
                        article.show.showAllReviews();
                    }
                })
            })
        },
        showAllReviews:function(){
            $.ajax({
                type:'GET',
                url:'/Article/showAllReviewByExpId',
                data:{expId:article.init.articleId},
                success:function(dataJson){
                    if(!dataJson.success){
                        EOS.util.UIAssert(dataJson.message);
                        return;
                    }
                    var container = article.init.container;
                    container.find(".all-reviews .count-reviews").html("共有"+dataJson.res.length + "条评论");
                    $.each(dataJson.res,function(index, value){
                        dataJson.res[index].createTs = EOS.util.dealDate(dataJson.res[index].createTs);
                    })
                    var markup = '<div class="item-view">'+
                    '{{if isChildReview}}'+
                    '回复&nbsp;'+
                    '<span >${reviewed}</span>' +
                    '{{/if}}' +
                    '<span>${content}</span>'+
                    '<div class="reviewer-meta">'+
                    '<span style="color:#017e66;">${reviewer}</span>'+ "&nbsp;&nbsp;"+
                    '<span>${createTs}</span><div style="float:right;">'+
                    '<span class="replay-review" style="color:#017e66;cursor:pointer;">回复</span>' + "&nbsp;&nbsp;"+
                    '<span style="color:#999;" >#${floor}</span></div>' +
                    '</div>'+
                    '</div>';
                    $.template('reviewTmp', markup);
                    $.tmpl('reviewTmp', dataJson.res).appendTo(".all-reviews");
                    article.show.showReplayReview();
                }
            })
        },
        showReplayReview:function(){
            var container = article.init.container;
            console.log("why!");
            container.find(".article-wrap .all-reviews .replay-review").toggle(function(){

            }, function (){
//                var thisObj = $(this);
//                thisObj.html("回复");
//                thisObj.parent().parent().parent().remove(".add-review-reply");
//                container.find(".article-wrap .add-review").removeClass("hidden");
            });


        }
    });
})(jQuery, window));