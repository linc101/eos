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
        }
    })

    register.submit = register.submit || {};
    register.submit = $.extend(register.submit, {
        initSubmit:function(){

        },
        emailRegister:function(){
            var container = register.init.container;
            container.find('.email-register').unbind().click(function(){
                
            })
        }
    })

})(jQuery, window));