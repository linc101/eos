/**
 * Created by yehuizhang on 14-9-1.
 */
var EOS = EOS || {};

((function($, window){
    EOS.Register = EOS.Register || {};

    var register = EOS.Register;

    register.init = register.init || {};
    register.init = $.extend(register.init,{
        doInit:function(container){
            register.init.container = container;
            register.submit.initSubmit();
            register.init.defaultSubmit();
            register.init.showWelcome();
        },
        defaultSubmit:function(){
            var container = register.init.container;
            container.find(".confirmpassword").keydown(function (event){
                if(event.keyCode == 13){
                    container.find(".submit_btn").click();
                }
            })
        },
        showWelcome:function(){
            var container = register.init.container;
            container.find(".wel-register").slideDown(600, function(){
                container.find(".register-content").fadeIn(400);
            });

        },
        changeCaptcha:function(){
            var container = register.init.container;
            container.find(".captcha-image").unbind().click(function(){
                $.ajax({
                    url:""

                })
            })
        }
    })

    register.submit = register.submit || {};
    register.submit = $.extend(register.submit, {
        initSubmit:function(){
            register.submit.emailRegister();
            register.submit.submitRegister();
        },
        emailRegister:function(){
            var container = register.init.container;
            container.find('.email-register').unbind().click(function(){
                var email = container.find(".email").val();
                var flag = isEmail(email);
                if(flag == false){
                    EOS.util.UIAssert("请输入邮箱的正确格式!");
                    return;
                }
                $.ajax({
                    type:'get',
                    url:'/Register/isEmailRegister',
                    data:{email:email},
                    success:function(dataJson){
                        if(dataJson.success){
                            EOS.util.UIAssert("恭喜，邮箱可用！");
                            return;
                        }

                        alert(dataJson.message);
                    }

                })
            })
        },
        submitRegister:function(){
            var container = register.init.container;
            container.find(".submit_btn").click(function(){
                var data = register.util.submitParams();
                if(data == null)return;
                $.ajax({
                    type:"get",
                    url:"/Register/doRegister",
                    data:data,
                    success:function(dataJson){
                        if(dataJson.success){
//                            EOS.util.UIAssert("恭喜，注册成功！");
                            window.location.href = "/Application/index";
                            return;
                        }
                        alert(dataJson.message);
                    }
                })
            })

        }
    })

    register.util = register.util || {};
    register.util = $.extend(register.util, {
        submitParams:function(){
            var container = register.init.container;
            var email           = container.find(".email").val(),
                username        = container.find(".username").val(),
                password        = container.find(".password").val(),
                confirmpassword = container.find(".confirmpassword").val(),
                usernamereg     = new RegExp("[\u4E00-\u9FA50-9A-Za-z]{2,16}"),
                code            = container.find(".captcha").val(),
                randomID        = container.find(".captcha-image").attr("randomIDValue");
            console.log(randomID);
            if(!isEmail(email)){
                alert("请输入正确的邮箱格式！");
                return null;
            }

            if(!usernamereg.test(username)){
                alert("输入的用户名不满足要求！");
                return null;
            }

            if(password.length < 6){
                alert("密码太短！");
                return null;
            }

            if(password != confirmpassword){
                alert("密码不匹配！");
                return null;
            }

            var data = {};
            data.email = email;
            data.userName = username;
            data.password = password;
            data.code = code;
            data.randomID = randomID;
            return data;

        }
    })

})(jQuery, window));