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
//                if(thisObj.hasClass("selected")) return;
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
            container.find(".msg-type-head .selected").click();
        },
        showMsg:function(){
            myMsg.show.showCommentMsg();
        },
        showCommentMsg:function(){
            var container = myMsg.init.container;
            console.log("test!");
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
                    var html = "<div expId = ${id}>" +
                            "<section>" +
                            "<div>${from}回复了:" +
                            "<span class='msg-location' expId = ${id}>${msg}</span>" +
                            "<span>${createTs}</span>" +
                            "</div>" +
                            "</section>" +
                            "</div>";
                    $.template("commentMsgTmp", html);
                    $.tmpl("commentMsgTmp", dataJson.res).appendTo(".msg-body .comment-msg");
                }

            })
        },
        showSystemMsg:function(){
            var container = myMsg.init.container;

        }
    })

})($, window));
