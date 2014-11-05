/**
 * Created by yehuizhang on 14/11/1.
 */
var EOS = EOS || {};
((function($, window){
    EOS.changePW = EOS.changePW || {};
    var changePW = EOS.changePW;

    changePW.init = changePW.init || {};
    $.extend(changePW.init,{
        doInit:function(container, tab){
            changePW.init.container = container;
            changePW.init.tab = tab;
            changePW.init.defaultClick();
            changePW.submit.doSubmit();
            changePW.init.headClick();
            changePW.init.initUploadPic();
            changePW.submit.modifyEmail();
            changePW.submit.modifyMotto();
        },
        defaultClick:function(){
            var container = changePW.init.container;
            container.find(".modify-password .newPWConfirm, .modify-email .email").keydown(function(event){
                if(event.keyCode == 13){
                    container.find(".submit_btn").click();
                }
            });
        },
        headClick:function(){
            var container = changePW.init.container;
            container.find(".list-group .list-group-item").click(function(){
                var thisObject = $(this);
                var tarObject = thisObject.attr("tarDiv");
                container.find(".list-group .list-group-item").removeClass("current");
                thisObject.addClass("current");
                container.find(".target").hide();
                container.find("." +tarObject).show();
            })
            var tab = changePW.init.tab;
            switch(tab){
                case 1:container.find(".head-pw").click();break;
                case 2:container.find(".head-email").click();break;
                case 3:container.find(".head-motto").click();break;
                case 4:container.find(".modify-pic-head").click();break;
                default:container.find(".head-pw").click();
            }
//            container.find(".head-pw").click();
        },
        initUploadPic:function(){
            var container = changePW.init.container;
            container.find(".modify-pic ")
            $.ajax({
                type:'GET',
                url:'/AccountSetting/getPicPath',
                success:function(dataJson){
                    container.find(".modify-pic .pic").attr("src", dataJson.res);
                }
            })

            container.find(".modify-pic #upload").change(function(){
                var pic = document.getElementById("upload");
                var fileName = pic.value;
                var imgObjPreview = document.getElementById("preview");
                if(!fileName.match(/.jpg|.gif|.png|.bmp/i)){
                    EOS.util.UIAssert("上传图片格式错误！请重新上传", function(){
//                        window.location.href = "/AccountSetting/completeInfo";
                        return;
                    });
                }
                if(pic.files && pic.files[0]) {
                    if(window.navigator.userAgent.indexOf("Chrome") >= 1 || window.navigator.userAgent.indexOf("Safari") >= 1) {
                        imgObjPreview.src = window.webkitURL.createObjectURL(pic.files[0]);
                    }else{
                        imgObjPreview.src = window.URL.createObjectURL(pic.files[0]);
                    }
                }else{
                    //待定
                }
            })
        }
    })

    changePW.submit = changePW.submit || {};
    $.extend(changePW.submit,{
        doSubmit:function(){
            var container = changePW.init.container;
            container.find(".modify-password .submit_btn").unbind().click(function(){
                var oldPW = container.find(".oldPW").val();
                var newPW = container.find(".newPW").val();
                var newPWConfirm = container.find(".newPWConfirm").val();
                if(newPW.length < 6){
                    EOS.util.UIAssert("请输入新密码的长度大于或等于6！", function(){
                        changePW.submit.doClearInput();
                    })
                    return;
                }

                if(newPW != newPWConfirm){
                    EOS.util.UIAssert("请保证两次输入的密码匹配！", function(){
                        changePW.submit.doClearInput();
                    });
                    return;
                }

                $.ajax({
                    type:'POST',
                    url:'/AccountSetting/changePW',
                    data:{
                        oldPW:oldPW,
                        newPW:newPW,
                        newPWConfirm:newPWConfirm
                    },
                    success:function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message, function(){
                                changePW.submit.doClearInput();
                            })
                            return;
                        }

                        EOS.util.UIAssert("修改密码成功", function(){
                            window.location.href = "/Application/index";
                        })
                    }
                })

            });

        },
        doClearInput:function(){
            var container = changePW.init.container;
            container.find(".oldPW").val("");
            container.find(".newPW").val("");
            container.find(".newPWConfirm").val("");
        },
        modifyEmail:function(){
            var container = changePW.init.container;
            container.find(".modify-email .submit_btn").unbind().click(function(){
                var email = container.find(".modify-email .email").val();
                if(!isEmail(email)){
                    EOS.util.UIAssert("请输入正确的邮箱格式！");
                    container.find(".modify-email .email").val("");
                    return ;
                }
                $.ajax({
                    type:'post',
                    url:'/AccountSetting/changeEmail',
                    data:{email:email},
                    success:function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message);
                            return;
                        }
                        EOS.util.UIAssert("修改成功！");
                        window.location.href="/Application/index";
                    }
                })
            });
        },
        modifyMotto:function(){
            var container = changePW.init.container;
            container.find(".modify-motto .submit_btn").unbind().click(function(){
                var motto = container.find(".modify-motto .motto").val();
                $.ajax({
                    type:'POST',
                    url:'/AccountSetting/changeBriefIntroduction',
                    data:{briefIntroduction:motto},
                    success:function(dataJson){
                        if(!dataJson.success){
                            EOS.util.UIAssert(dataJson.message);
                            return;
                        }
                        EOS.util.UIAssert("修改成功！");
                        window.location.href = "/Application/index";
                    }
                })
            });
        }
    })
})(jQuery, window));

