/**
 * Created by yehuizhang on 14-9-5.
 */

var EOS = EOS || {};

((function($, window){
    EOS.Login = EOS.Login || {};

    var login = EOS.Login;

    login.init = login.init || {};
    login.init = $.extend(login.init, {
       doInit:function(container){
           login.init.container = container;
           login.init.defaultSubmit();
           login.submit.doSubmit();
           login.submit.doubanLogin();
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
                        EOS.util.UIAssert(dataJson.message, function(){
                            window.location.href = "/Login/login";
                        });
                    }
                })
            })
        },
        doubanLogin:function(){
            var container = login.init.container;
            container.find(".douban-login").click(function(){
                console.log("enter");
                $.ajax({
                    type:'GET',
                    url:'/URLRequest/authDouban',
                    success:function(dataJson){
                        console.log(dataJson);
                    }
                })
//                $.ajax({
//                    type:'POST',
//                    url:"https://www.douban.com/service/auth2/auth?" +
//                "client_id=009d7e877f154b8501d87b967d3fe28f&"+
//                "redirect_uri=https://www.example.com/back",
//                    success:function(dataJson){
//                        alert(dataJson);
//                        console.log(dataJson);
//                    }
//                })
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
                EOS.util.UIAssert('请输入邮箱！');
                return null;
            }

            if(password == null){
                EOS.util.UIAssert('请输入密码！')
                return null;
            }

            var params = {};
            params.email = email;
            params.password = password;

            return params;
        }
    })
})(jQuery, window));