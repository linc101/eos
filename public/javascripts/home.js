/**
 * Created by yehuizhang on 14-9-30.
 */
var EOS = EOS || {};

((function($, window){
    EOS.home = EOS.home || {};

    EOS.home.init = EOS.home.init || {};

    $.extend(EOS.home.init, {
        doInit:function(container){
            EOS.home.init.container = container;
        },

        tabChange:function(){
            var container = EOS.home.init.container;
            container.find(".article-head").unbind().click(function(){

            });
        }
    })

})(jQuery,window));