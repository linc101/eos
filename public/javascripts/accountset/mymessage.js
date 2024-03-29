/**
 * Created by hgqput on 14-9-22.
 */

var EOS = EOS || {};

((function(jQuery, window){
    EOS.myMsg = EOS.myMsg || {};
    var myMsg = EOS.myMsg;

    myMsg.init = myMsg.init || {};
    $.extend(myMsg.init, {
        doInit:function(container){
            myMsg.init.container = container;
            myMsg.show.showMsg();
            myMsg.show.doShow();
        }
    })

    myMsg.show = myMsg.show || {};
    $.extend(myMsg.show, {
        doShow:function(){
            var container = myMsg.init.container;
            container.find(".msg-type-head .msg-head").click(function(){
                var thisObj = $(this);
                container.find(".msg-type-head .msg-head").removeClass("selected");
                thisObj.addClass("selected");
                var tarDiv = thisObj.attr("tarDiv");
                container.find(".msg-body .msg").addClass("hidden");
                if(tarDiv == "comment-msg"){
                    container.find(".msg-body .comment-msg").removeClass("hidden");
                }else if(tarDiv == "system-msg"){
                    container.find(".msg-body .system-msg").removeClass("hidden");
                }
            })
            var msgType = container.find(".msg-type-head").attr("msgType");
            if(msgType == "COMMENT_MES")
                container.find(".msg-type-head .comment-msg-head").click();
            else if(msgType == "SYSTEM_MES"){
                container.find(".msg-type-head .system-msg-head").click();
            }
        },
        showMsg:function(){
            myMsg.show.showCommentMsg();
        },
        showCommentMsg:function(){
            var container = myMsg.init.container;
            var noneHtml = "<div style='text-align: center;margin-top:20px;'>" +
                "<span >暂时没有消息!</span>"+
                "</div>";
            var html = "<div expId = '${expId}' style='margin-bottom:20px;border-bottom: 1px solid #D5E4E1;padding-bottom:5px; '>" +
                "<section>" +
                "<div><div><span style='color:#017e66;'>${from}</span>&nbsp;&nbsp;回复了:</div>" +
                "<div><span class='msg-location' style='margin-left:30px;' expId = '${expId}'><a href='/article/${expId}' style='text-decoration: none;color:#017e66;'>${msg}</a></span></div>" +
                "<div style='margin-left:85%;'>" +
                "<span>${createTs}</span>&nbsp;&nbsp;" +
                "<span style='color:#017e66;cursor:pointer;' id='${id}' class='mark-readed'>标记为已读</span>" +
                "</div>" +
                "</div>" +
                "</section>" +
                "</div>";
            $.ajax({
                type:'GET',
                data:{
                    type:'COMMENT_MES'
                },
                url:'/AccountSetting/showMsg',
                success:function(dataJson){
                    if(!dataJson.success){
                        return;
                    }
                    container.find(".msg-body .comment-msg").empty();
                    if(dataJson.res.length == 0){
                        container.find(".msg-body .comment-msg").append(noneHtml);
                        return;
                    }

                    $.each(dataJson.res, function(index, element){
                        dataJson.res[index].createTs = EOS.util.dealDate(dataJson.res[index].createTs);
                    });

                    $.template("commentMsgTmp", html);
                    $.tmpl("commentMsgTmp", dataJson.res).appendTo(".msg-body .comment-msg");
                    myMsg.show.markMsgReaded();
                }
            })

            $.ajax({
                type:'GET',
                data:{
                    type:'SYSTEM_MES'
                },
                url:'/AccountSetting/showMsg',
                success:function(dataJson){
                    if(!dataJson.success){
                        return;
                    }
                    container.find(".msg-body .system-msg").empty();
                    if(dataJson.res.length == 0){
                        container.find(".msg-body .system-msg").append(noneHtml);
                        return;
                    }

                    $.each(dataJson.res, function(index, element){
                        dataJson.res[index].createTs = EOS.util.dealDate(dataJson.res[index].createTs);
                    });

                    $.template("systemMsgTmp", html);
                    $.tmpl("systemMsgTmp", dataJson.res).appendTo(".msg-body .system-msg");
                    myMsg.show.markMsgReaded();
                }
            })
        },
        markMsgReaded:function(){
            var container = myMsg.init.container;
            container.find(".msg-body .mark-readed").unbind().click(function(){
                var thisObj = $(this);
                var msgId = thisObj.attr("id");
                $.ajax({
                    type:'POST',
                    url:'/AccountSetting/markReaded',
                    data:{
                        msgId:msgId
                    },
                    success:function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message, function(){
                                window.location.reload();
                            });
                        }
                        window.location.reload();
                    }
                })
            })
        },
        showSystemMsg:function(){
            var container = myMsg.init.container;

        }
    })

})($, window));
