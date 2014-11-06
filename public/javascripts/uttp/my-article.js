/**
 * Created by uttp on 14-11-6.
 */
var UTTP = UTTP || {};
((function($, window){
    UTTP.myArticle = UTTP.myArticle || {};

    var myArticle = UTTP.myArticle;

    myArticle.init = myArticle.init || {};
    $.extend(myArticle.init, {
        doInit:function(container){
            myArticle.init.container = container;


        },
        showAllMyArticle:function(){
            var container = myArticle.init.container;
            $.ajax({
                type:'GET',
                url:'/Uttp/findAllMyExp',
                success:function(dataJson){
                    if(!dataJson.success){
                        alert(dataJson.message);
                        return;
                    }
                    container.find("#my-article").tmpl
                }
            })
        }
    });
})(jQuery, window));
