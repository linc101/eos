/**
 * Created by uttp on 14-10-13.
 */
var EOS = EOS || {};
((function($, window){
    EOS.Main = EOS.Main || {};
    var main = EOS.Main;

    main.init = main.init || {};
    main.init = $.extend(main.init,{
        doInit:function(container){
            main.init.container = container;
            main.submit.submitInit();
            main.init.initHead();
            main.init.testDialog();
        },
        initHead:function(){
            var container = main.init.container;
            if(EOS.userId != null) {
                container.find(".user-login-head").show();
                container.find(".user-nologin-head").addClass("hidden");
                container.find(".user-login-head #login-username").html(decodeURI(EOS.userName));
            }
        },
        testDialog:function(){
            EOS.Main.login = $("#user-login").dialog({
                title:'登录经验分享社区帐号',
                autoOpen: false,
                height: 400,
                width: 350,
                modal: true
            })
        }
    })

    main.submit = main.submit || {};
    main.submit = $.extend(main.submit, {
        submitInit:function(){
            main.submit.logoutAction();
            main.submit.tabChange();
            main.submit.tabFocus();
            main.submit.userConfig();
            main.submit.noticeMsgCount();
            main.submit.noticeMsg();
            main.submit.userLogin();
        },
        logoutAction:function(){
            var container = main.init.container;
            $('body').find(".logout").livequery(function(){
                $(this).click(function(){
                    $.ajax({
                        type:'POST',
                        url:'/Login/userLogout',
                        success:function(dataJson){
                            if(dataJson.success){
                                window.location.href = "/Application/index";
                                return;
                            }
                            EOS.util.UIAssert(dataJson.message);
                        }
                    })
                })

            })
        },
        tabChange:function(){
            var container = main.init.container;
            container.find('.l-item').click(function(){
                container.find('.l-item').removeClass('current');
                $(this).addClass('current');
            })
        },
        tabFocus:function(){
            var container = main.init.container;
            $('body').find('.hover-on').livequery(function(){
                $(this).hover(function(){
                    $(this).addClass('mouseon');
                },function(){
                    $(this).removeClass('mouseon');
                });
            })
        },
        userConfig:function(){
            var container = main.init.container;
            container.find('#login-username').poshytip({
                className : 'tip-yellowsimple',
                content : '<div style="height:3px;"></div>' +
                    '<a href="/UserCenter/userCenter" class="downlist_a">用户中心</a>'+
                    '<br/>' +
                    '<div style="height:10px;"></div>' +
                    '<a href="/AccountSetting/accountSetting" class="downlist_a hover-on">帐号设置</a>' +
                    '<br/>' +
                    '<div style="height:10px;"></div>' +
                    '<a href="javascript:void(0)" class="downlist_a logout hover-on">退出</a>',

                alignTo : 'target',
                showTimeout:0,
                alignX : 'inner-right',
                alignY : 'bottom',
                offsetX : 0,
                offsetY : 5,
                fade : false,
                slide : false
            });
        },
        noticeMsg:function(){
            if(EOS.userId != null) {
                setInterval(main.submit.noticeMsgCount, 5000);
            }
        },
        noticeMsgCount:function(){
            if(EOS.userId != null) {
                $.ajax({
                    type: 'get',
                    url: '/Application/findCountMsg',
                    success: function (dataJson) {
                        if (!dataJson.success) {
                            EOS.util.UIAssert(dataJson.message);
                            return;
                        }
                        var container = main.init.container;
                        container.find(".my-msg-notice .my-msg-count").remove();
                        if(dataJson.res != 0)
                            container.find(".my-msg-notice").append("<span class='my-msg-count'>" + dataJson.res + "</span>");
                    }
                })
            }
        },
        userLogin:function(){
            var container = main.init.container;
            container.find(".user-login").unbind().click(function(){
                EOS.Main.login.dialog("open");
            })
        },
        submitLogin:function(){
            var container = main.init.container;

        }
    })

})(jQuery, window));
