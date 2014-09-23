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
            myMsg.show.doShow();
        }
    })

    myMsg.show = myMsg.show || {};
    $.extend(myMsg.show, {
        doShow:function(){
            var container = myMsg.init.container;
            container.find(".msg-type-head .msg-head").unbind().click(function(){
                var thisObj = $(this);
                if(thisObj.hasClass("selected")) return;
                container.find(".msg-type-head .msg-head").removeClass("selected");
                thisObj.addClass("selected");
                var tarDiv = thisObj.attr("tarDiv");
                if(tarDiv == "comment-msg"){
                    console.log(0);
                }else if(tarDiv == "system-msg"){
                    console.log(1);
                }
            })
        },
        showMsg:function(){

        }
    })

})($, window));
