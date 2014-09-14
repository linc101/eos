/**
 * Created by uttp on 14-9-8.
 */

var EOS = EOS || {};

((function($, window){
    EOS.util = EOS.util || {};

    var util = EOS.util;
    util.UIAssert = function(text){
        var html = "<div style='text-align:center;line-height: 120px;'>" + text +"</div>";
        $(html).dialog({
            title:'提示',
            modal:true,
            closeText: "hide",
            width: 300,
            height:250,
            buttons:[
                {text:'确定',click:function(){
                    $(this).dialog('close');
                }}
            ]
        })
    }

    util.UIConfirm = function(text, callback){
        var html = "<div style='text-align:center;line-height: 120px;'>" + text +"</div>";
        $(html).dialog({
            title:'确认',
            modal:true,
            closeText: "hide",
            width: 300,
            height:250,
            buttons:[
                {text:'确定',click:function(){
//                    $(this).dialog('close');
                    callback;
                }},{
                    text:'取消',click:function(){
                        $(this).dialog('close');
                    }
                }
            ]
        })
    }

    util.dealDate = function(createTs){
        if(createTs == null){
            return null;
        }

        var nowDate = new Date();
        var nowYear = nowDate.getFullYear();

        var thatDate = new Date(createTs);
        var thatYear = thatDate.getFullYear();

        if(nowYear == thatYear)
            return thatDate.FormatMD();
        else{
            return thatDate.FormatYMD();
        }
    }
})(jQuery, window));
