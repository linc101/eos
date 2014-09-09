/**
 * Created by yehuizhang on 14-9-5.
 */

EOS = EOS || {};

((function($, window){
    EOS.Login = EOS.Login || {};

    var login = EOS.Login;

    login.init = login.init || {};
    login.init = $.extend(login.init, {
       doInit:function(container){
           login.init.container = container;
           login.init.defaultSubmit();
           login.submit.doSubmit();
       },
       defaultSubmit:function(){
           var container = login.init.container;
           container.find('.password').keydown(function(event){
               if(event.keyCode == 13){
                   container.find('.submit_btn').click();
               }
           })
       }
    })

    login.submit = login.submit || {};
    login.submit = $.extend(login.submit, {
        doSubmit:function(){
            var container = login.init.container;
            container.find('.submit_btn').unbind().click(function(){
                var params = login.util.submitParams();
                if(params == null) return;
                $.ajax({
                    type:'get',
                    url:'/Login/userLogin',
                    data:params,
                    success:function(dataJson){
                        if(dataJson.success){
                            window.location.href = "/Application/index";
                            return;
                        }
                        EOS.util.UIAssert(dataJson.message);
//                        alert(dataJson.messag
//                        var html =
//                            '<div class="dialog" id="dialog-message">' +
//                            '  <p>' +
//                            '    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>' + "test" +
//                            '  </p>' +
//                            '</div>';
//                        $(html).dialog({
//                            //autoOpen: false,
//                            resizable: false,
//                            modal: true,
//                            show: {
//                                effect: 'fade',
//                                duration: 300
//                            },
//                            title: "test" || "提示信息",
//                            buttons: {
//                                "确定": function() {
//                                    var dlg = $(this).dialog("close");
//                                }
//                            }
//                        });
                    }
                })
            })
        }
    })

    login.util = login.util || {};
    login.util = $.extend(login.util, {
        submitParams:function(){
            var container = login.init.container;
            var email       = container.find(".email").val(),
                password    = container.find(".password").val();

            if(email == null){
                alert('请输入邮箱！');
                return null;
            }

            if(password == null){
                alert('请输入密码！')
                return null;
            }

            var params = {};
            params.email = email;
            params.password = password;

            return params;
        }
    })
})(jQuery, window));