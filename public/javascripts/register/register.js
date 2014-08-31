/**
 * Created by yehuizhang on 14-9-1.
 */
var EOS = EOS || {};

((function($, window){
    EOS.Register = EOS.Register || {};

    var register = EOS.Register;

    register.init = register.init || {};
    register.init = $.extend(register.init,{
        doInit:function(content){
            register.init.content = content;
        }
    })

    register.submit = register.submit || {};
    register.submit = $.extend(register.submit, {
        
    })

})(jQuery, window));