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
        }
    });

    myExp.show = myExp.show ||{};
    myExp.show = $.extend(myExp.show,{
        showAllMyExps:function(){
            $.find(".pag").tmpage({
                currPage:1,
                pageSize:10,
                pageCount:1,
                ajax:{
                    param:{

                    }
                }
            })
        }
    })
})(jQuery, window));
