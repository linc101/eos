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
            myArticle.init.showAllMyArticle();
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
                    $.each(dataJson.res, function(index){
                        dataJson.res[index].i = index;
                    })

                    var content = "<div class='${i} content'>"+
                            "<h4 class='title' style='text-align:center;'></h4>"+
                            "<div class='article'></div>"+
                        "</div>";
                    $.template('article', content);
                    $.tmpl("article", dataJson.res).appendTo(".my-article");
                    $.each(dataJson.res, function(index){
                        console.log(dataJson.res[index]);
                        container.find("."+index + " .title").html(dataJson.res[index].title);
                        container.find("." + index + " .article").html(dataJson.res[index].article);
                    })
                    console.log(dataJson.res);
                    ////console.log(dataJson.res);
                    ////var html = container.find("#my-article").tmpl(dataJson.res);
                    ////console.log(container.find("#my-article"));
                    ////console.log(html);
                    ////container.find(".my-article").append(html);
                    ////container.find("#my-article").tmpl(dataJson.res).appendTo(".my-article");
                    //var markup =" <div > " +
                    //"<span class=\"title\">${title}</span>"+
                    //"${article}" + "</div>";
                    ////dataJson.res[0].article = $.parseHTML(dataJson.res[0].article).toString();
                    ////dataJson.res[0].title = $.parseHTML(dataJson.res[0].title).toString();
                    //
                    //$.template('article', markup);
                    //$.tmpl("article", data).appendTo(".my-article");
                    //console.log($(".title").html());
                }
            })
        }
    });
})(jQuery, window));
