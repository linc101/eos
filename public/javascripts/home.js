/**
 * Created by yehuizhang on 14-9-30.
 */
var EOS = EOS || {};

((function($, window){
    EOS.home = EOS.home || {};

    var home = EOS.home;

    home.init = home.init || {};

    $.extend(home.init,{
        doInit:function(container){
            home.init.container = container;
            home.event.changeTab();
            container.find(".tab-head .new-article-head").click();
            home.event.showHotTags();
        }
    })

    home.event = home.event || {};

    $.extend(home.event, {
        nowPage:1,
        changeTab:function(){
            var container = home.init.container;
            container.find(".tab-head .article-head").unbind().click(function(){
                var thisObj = $(this);
                var tarDiv = thisObj.attr("tarDiv");
                container.find(".tab-head .article-head").removeClass("selected");
                thisObj.addClass("selected");
                container.find(".home-body .article-content").addClass("hidden");
                if(tarDiv == "new-article"){
                    container.find(".new-article").removeClass("hidden");
                    home.event.nowPage = 1;
                    home.event.showArticle(".new-article");
                }else if(tarDiv == "hot-article"){
                    container.find(".hot-article").removeClass("hidden");
                    home.event.nowPage = 1;
                    home.event.showArticle(".hot-article");
                }
            })
        },
        showArticle:function(tarDiv){
//            tarDiv = tarDiv;
            var field = null;
            if(tarDiv == ".new-article"){
                field = "createTs";
            }else if(tarDiv == ".hot-article"){
                field = "scanTimes";
            }
            $(tarDiv).find(".pag").pagination({
                currPage: home.event.nowPage,
                pageSize: 10,
                pageCount: 1,
                ajax: {
                    on: true,
                    dataType: 'json',
                    param:{
                        field:field,
                        isDesc:true
                    },
                    url: "/UserCenter/showAllExps",
                    callback: function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message);
                            return;
                        }

                        home.event.nowPage = dataJson.pn;
                        $(tarDiv).find(tarDiv + "-content").empty();
                        $.each(dataJson.res, function(index, elem){
                            var time =dataJson.res[index].createTs;
                            dataJson.res[index].createTs = EOS.util.dealDate(time);
                        })

                        var html = "<section style=\"border-bottom: 1px solid #eee;margin-bottom:10px;margin-left:5px;\">" +
                            "<div class=\"article-rank\">" +
                            "<div class=\"article-comment\">${reviewTimes}" +
                            "<small>回复</small>"+
                            "</div>" +
                            "<div class=\"article-scan\">${scanTimes}" +
                            "<small>浏览</small>"+
                            "</div>" +
                            "<div style=\"display:inline-block;\">" +
                            "<div style=\"text-align:left;font-size:13px;\">" +
                            "${userName}&nbsp;•&nbsp;${createTs}"+
                            "</div>"+
                            "<div>" +
                            "<a href=\"/article/${id}\" style=\"text-decoration: none;color:#333;font-size:16px;font-weight:bold;\">${title}</a>"+
                            "</div>"+
                            "</div>"+
                            "</div>" +
                            "</section>";
                        $.tmpl(html, dataJson.res).appendTo($(tarDiv).find(tarDiv + "-content"));

                    }
                }
            })
        },
        showHotTags:function(){
            var container = home.init.container;
            $.ajax({
                type:'POST',
                url:'/Home/showTags',
                success:function(dataJson){
                    if(!dataJson.success){
                        alert("热门标签获取失败！");
                    }
                    var html = "<span class=\"hot-tag\">" +
                        "${tagName}" +
                        "</span>" + " ";
                    $.tmpl(html, dataJson.res).appendTo(container.find(".hot-tags"));
                }


            })
        }
    })

})(jQuery,window));