/**
 * Created by yehuizhang on 14-9-1.
 */
var EOS = EOS || {};

function isEmail(email){
    var emailReg = new RegExp("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+");
    return emailReg.test(email);
}
((function($, window){
    EOS.Register = EOS.Register || {};

    var register = EOS.Register;

    register.init = register.init || {};
    register.init = $.extend(register.init,{
        doInit:function(container){
            register.init.container = container;
            register.submit.initSubmit();
        }
    })

    register.submit = register.submit || {};
    register.submit = $.extend(register.submit, {
        initSubmit:function(){
            register.submit.emailRegister();
        },
        emailRegister:function(){
            var container = register.init.container;
            container.find('.email-register').unbind().click(function(){
                var email = container.find(".email").val();
                var flag = isEmail(email);
                if(flag == false){
                    alert("请输入邮箱的正确格式!");
                    return;
                }
                $.ajax({
                    type:'get',
                    url:'/Register/isEmailRegister',
                    data:{email:email},
                    success:function(dataJson){
                        if(dataJson.success){
                            alert("恭喜，邮箱可用！");
                            return;
                        }

                        alert(dataJson.message);
                    }

                })
            })
        },
        submitRegister:function(){
            var data = register.util.submitParams();
            if(data == null)return;
            $.ajax({
                type:"get",
                url:"/"
            })
        }
    })

    register.util = register.util || {};
    register.util = $.extend(register.util, {
        submitParams:function(){
            var container = register.util.container;
            var email           = container.find(".email").val(),
                username        = container.find(".username").val(),
                password        = container.find(".password").val(),
                confirmpassword = container.find(".confirmpassword").val(),
                usernamereg     = new RegExp("[\u4E00-\u9FA50-9A-Za-z]{2,16}");

            if(!isEmail(email)){
                alert("请输入正确的邮箱格式！");
                return null;
            }

            if(!usernamereg.text(username)){
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
            data.username = username;
            data.password = password;
            return data;

        }
    })

})(jQuery, window));