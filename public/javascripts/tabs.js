/**
 * Created by yehuizhang on 14/10/24.
 */

var EOS = EOS || {};

function initTab(container){
    container.find(".tab .tabs-head .tab-nav").click(function(){
        var currentObj = $(this);
        var tarDiv = currentObj.attr("tarDiv");
        container.find(".tabs-body .tab-body").removeClass("current");
        container.find(".tabs-body ." + tarDiv).addClass("current");
    });
    container.find(".tab .tabs-head li:first-child").click();
}