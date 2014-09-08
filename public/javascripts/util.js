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
})(jQuery, window));
