/**
 * Created by uttp on 14-9-8.
 */

var EOS = EOS || {};

((function($, window){
    EOS.addExp = EOS.addExp || {};

    var addExp = EOS.addExp;
    addExp.init = addExp.init || {};

    addExp.init = $.extend(addExp.init, {
        doInit:function(container){
            addExp.init.container = container;
            addExp.submit.submitExp();
        }
    })

    addExp.submit = addExp.submit || {};
    addExp.submit = $.extend(addExp.submit,{
        submitExp:function(){
            var container = addExp.init.container;
            container.find(".exp-wrap .submit_btn").unbind().click(function(){
                console.log(container.find('exp-wrap .input-content').val());
                var expTitle = container.find('.exp-wrap .input-title').val().trim(),
                    expContent = container.find('.exp-wrap .input-content').val().trim();
                if(expTitle.length < 6){
                    EOS.util.UIAssert("请确保输入的标题长度大于6");
                    return;
                }

                if(expContent.length == 0 || expContent == null){
                    EOS.util.UIAssert("请输入内容");
                    return;
                }

                $.ajax({
                    type:'POST',
                    url:"/UserCenter/doAddExperience",
                    data:{
                        title:expTitle,
                        article:expContent
                    },
                    success:function(dataJson){
                        if(dataJson.success){
                            window.location.href = "/article/" + dataJson.res;
//                            EOS.util.UIAssert("ok!");
                            return;
                        }
                        EOS.util.UIAssert("失败！");
                    }

                })
            })

        }
    })


})(jQuery, window));
