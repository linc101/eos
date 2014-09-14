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
                        article.init.container.find(".article-wrap .all-reviews").empty();
                        article.show.showAllReviews();
                        container.find(".review-textarea").val("");
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
                        if(EOS.userId != null){
                            dataJson.res[index].isLogin = true;
                            if(dataJson.res[index].reviewer == EOS.userName){
                                dataJson.res[index].isOwn = true;
                            }else{
                                dataJson.res[index].isOwn = false;
                            }
                        }else{
                            dataJson.res[index].isLogin = false;
                            dataJson.res[index].isOwn =false;
                        }

                    })
                    var markup = '<div class="item-view" id="${id}">'+
                    '{{if isChildReview}}'+
                    '回复&nbsp;'+
                    '<span >${reviewed}</span>:</br>' +
                    '{{/if}}' +
                    '<span>${content}</span>'+
                    '<div class="reviewer-meta">'+
                    '<span style="color:#017e66;" class="reviewed">${reviewer}</span>'+ "&nbsp;&nbsp;"+
                    '<span>${createTs}</span>' +
                    '{{if isOwn}}'+
                     '&nbsp;·&nbsp;' + '<span class="delete-review" style="color:#017e66;cursor:pointer;">删除</span>' +
                    '{{/if}}'+
                    '<div style="float:right;">'+
                    '{{if isLogin}}'+
                    '{{if isOwn==false}}' +
                    '<span class="replay-review" style="color:#017e66;cursor:pointer;">回复</span>' + "&nbsp;&nbsp;"+
                    '{{/if}}' +
                    '{{/if}}'+
                    '<span style="color:#999;" >#${floor}</span></div>' +
                    '</div>'+
                    '</div>';
                    $.template('reviewTmp', markup);
                    $.tmpl('reviewTmp', dataJson.res).appendTo(".all-reviews");
                    if(EOS.userId != null)
                        article.show.showReplayReview();
                }
            })
        },
        showReplayReview:function(){
            var container = article.init.container;
            container.find(".article-wrap .all-reviews .replay-review").toggle(function(){
                var thisObj = $(this);
                thisObj.html("取消回复");
                thisObj.parent().parent().parent().append('<div class="add-review-reply">' +
                    '<h4>你的评论</h4>'+
                    '<textarea rows="5" class="review-textarea"></textarea>'+
                    '<span class="submit_btn" style="margin-top:10px;">提交评论</span>' +
                    '</div>');
                container.find(".article-wrap .add-review").addClass("hidden");
                article.show.initReviewReplay(thisObj.parent().parent().parent());
            }, function (){
                var thisObj = $(this);
                thisObj.html("回复");
                thisObj.parent().parent().parent().find(".add-review-reply").remove();
                container.find(".article-wrap .add-review").removeClass("hidden");
            });
        },
        deleteReview:function(container){
            
        }, initReviewReplay:function(container){

            $(container).find(".submit_btn").unbind().click(function(){
                var reviewed = $(container).find(".reviewed").text();
                var content = $(container).find(".review-textarea").val();
                console.log("enter submit");
                console.log(reviewed);
                console.log(EOS.userName);
                console.log(article.init.articleId);
                console.log(content);
                $.ajax({
                    type:'GET',
                    url:'/Article/saveReviewReply',
                    data:{
                        reviewed:reviewed,
                        reviewer:EOS.userName,
                        expId:article.init.articleId,
                        content:content
                    },
                    success:function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message);
                            return;
                        }
                        article.init.container.find(".article-wrap .all-reviews").empty();
                        article.show.showAllReviews();
                        article.init.container.find(".article-wrap .add-review").removeClass("hidden");
                    }
                })
            });
        }
    });
})(jQuery, window));