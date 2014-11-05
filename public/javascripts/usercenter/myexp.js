/**
 * Created by uttp on 14-9-15.
 */
var EOS = EOS || {};

((function($,window){
    EOS.myExp = EOS.myExp || {};
    var myExp = EOS.myExp ;
    myExp.init = myExp.init || {};

    myExp.init = $.extend(myExp.init, {
        doInit:function(container){
            myExp.init.container = container;
            myExp.show.showAllMyExps();
        }
    });

    myExp.show = myExp.show ||{};
    myExp.show = $.extend(myExp.show,{
        nowPage:1,
        showAllMyExps:function(){
            var container = myExp.init.container;
            container.find(".pag").pagination({
                currPage: myExp.show.nowPage,
                pageSize: 10,
                pageCount: 1,
                ajax: {
                    on: true,
                    dataType: 'json',
                    url: "/UserCenter/showAllMyExps",
                    callback: function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message);
                            return;
                        }
                        var container = myExp.init.container;
                        if(dataJson.res.length == 0){
                            container.find(".pag").hide();
//                            return;
                        }
                        myExp.show.nowPage = dataJson.pn;
                        container.find(".my-all-exps").empty();
                        $.each(dataJson.res, function(index, elem){
                            var time = dataJson.res[index].createTs;
                            dataJson.res[index].createTs = EOS.util.dealDate(time);
                        })
                        container.find(".my-all-exps").html("<h3>共分享了<span style=\"color:#009196;\">" + dataJson.count +"</span>篇文章</h3>");
                        var html = "<article class=\"all-exps-detail\">" +
                                   "<div class=\"scan-times\">" +
                                   "<span>${scanTimes}</span>浏览"+
                                   "</div>" +
                                   "<div class=\"exps-brief\">"+
                                   "<h3><a href=\"/article/${id}\" style=\"text-decoration: none;color:#333\">${title}</a></h3>" +

                                   "<div> " +
                                   "${createTs}&nbsp;•&nbsp;" +
                                   "${reviewTimes}条评论" +
                                   "<div class=\"operation\" style=\"float:right;color:#017e66;\">" +
                                   "<span class=\"edit\" style=\"cursor:pointer;\">编辑</span>&nbsp;•&nbsp;" +
                                   "<span class=\"delete\" style=\"cursor:pointer;\" id=\"${id}\">删除</span>" +
                                   "</div>" +
                                   "</div>"+

                                   "</div>" +
                                   "</article>";

                        $.tmpl(html, dataJson.res).appendTo(container.find(".my-all-exps"));
                        myExp.show.deleteExp();
                    }
                }
            })
        },
        deleteExp:function(){
            var container = myExp.init.container;
            container.find(".all-exps-detail .delete").unbind().click(function(){
                var thisObj = $(this);
                var thisId = thisObj.attr('id');
                console.log(thisId);
                if(confirm("是否删除？")) {
                    $.ajax({
                        type: 'POST',
                        url: '/UserCenter/deleteExpById',
                        data: {expId: thisId},
                        success: function (dataJson) {
                            if (!dataJson.success) {
                                EOS.util.UIAssert(dataJson.message);
                                return;
                            }
                            myExp.show.showAllMyExps();
                        }
                    })
                }
            })
        }
    })
})(jQuery, window));
