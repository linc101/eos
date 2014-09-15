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
        showAllMyExps:function(){
            var container = myExp.init.container;
            container.find(".pag").tmpage({
                currPage: 1,
                pageSize: 10,
                pageCount: 1,
                ajax: {
                    on: true,
                    dataType: 'json',
                    url: "/UserCenter/showAllMyExps",
                    callback: function(data){
                        console.log("test");
                    }
                }
            })
        }
    })
})(jQuery, window));
