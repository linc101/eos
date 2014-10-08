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
                console.log(tarDiv);
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
            tarDiv = tarDiv;
            console.log("test");
            console.log(tarDiv);
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

                        var html = "<section>" +
                            "<div class=\"article-rank\">" +
                            "<div class=\"article-comment\"></div>" +
                            "</div>" +
                            "</section>";
//                        myExp.show.nowPage = dataJson.pn;
//                        container.find(".my-all-exps").empty();
//                        $.each(dataJson.res, function(index, elem){
//                            var time = dataJson.res[index].createTs;
//                            dataJson.res[index].createTs = EOS.util.dealDate(time);
//                        })
//                        container.find(".my-all-exps").html("<h3>共分享了<span style=\"color:#009196;\">" + dataJson.count +"</span>篇文章</h3>");
//                        var html = "<article class=\"all-exps-detail\">" +
//                            "<div class=\"scan-times\">" +
//                            "<span>${scanTimes}</span>浏览"+
//                            "</div>" +
//                            "<div class=\"exps-brief\">"+
//                            "<h3><a href=\"/article/${id}\" style=\"text-decoration: none;color:#333\">${title}</a></h3>" +
//
//                            "<div> " +
//                            "${createTs}&nbsp;•&nbsp;" +
//                            "${reviewTimes}条评论" +
//                            "<div class=\"operation\" style=\"float:right;color:#017e66;\">" +
//                            "<span class=\"edit\" style=\"cursor:pointer;\">编辑</span>&nbsp;•&nbsp;" +
//                            "<span class=\"delete\" style=\"cursor:pointer;\" id=\"${id}\">删除</span>" +
//                            "</div>" +
//                            "</div>"+
//
//                            "</div>" +
//                            "</article>";
//
//                        $.tmpl(html, dataJson.res).appendTo(container.find(".my-all-exps"));
//                        myExp.show.deleteExp();
                    }
                }
            })
        }

    })



})(jQuery,window));