var _html;
$(function () {
    $("html").css("min-height",$(window).height());
    $(".mains").css("min-height",$(window).height()-241)
    $(".login-tab-li").off().on("click",function () {
        var _index=$(this).index();
        $(this).addClass("ligin-tab-active").siblings().removeClass("ligin-tab-active");
        $(".login-main-li").eq(_index).addClass("login-main-active").siblings().removeClass("login-main-active");
    });
    $(".user-type-li").off().on("click",function () {
        var _index=$(this).index();
        $(this).addClass("user-type-avtive").siblings().removeClass("user-type-avtive");
        $(".user-type-mainLi").eq(_index).addClass("type-mainLi-active").siblings().removeClass("type-mainLi-active");
    });
    $(".qr-open").off().on("click",function () {
        $(this).animate({right:"-39.5px"});
        $(".erweima_box").animate({right:"0"});
    });
    $(".erweima-hide").off().on("click",function () {
        $(this).parents(".erweima_box").animate({right:"-130px"});
        $(".qr-open").animate({right:"0"});
    });
    $(".js-submit-close").off().on("click",function () {
        $(this).parents(".mask-box").hide();
    });
});
function sendemail(){
    var obj = $("#get-btn");
    settime(obj);
};
function settime(obj) { //发送验证码倒计时
    if (countdown == 0) {
        obj.attr('disabled',false);
        obj.html("获取");
        countdown = 60;
        return;
    } else {
        obj.attr('disabled',true);
        obj.html("(" + countdown + ")");
        countdown--;
    }
    setTimeout(function() {settime(obj)},1000);
}
function timer(intDiff){
    window.setInterval(function(){
        var day=0,
            hour=0,
            minute=0,
            second=0;//时间默认值
        if(intDiff > 0){
            day = Math.floor(intDiff / (60 * 60 * 24));
            hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
            minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
            second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        }
        if (day<=9) day = '0' + day;
        if (hour<=9) hour = '0' + hour;
        if (minute<=9) minute = '0' + minute;
        if (second<=9) second = '0' + second;
        $('#day_show').html(day);
        $('#hour_show').html('<s id="h"></s>'+hour);
        $('#minute_show').html('<s></s>'+minute);
        $('#second_show').html('<s></s>'+second);
        intDiff--;
    }, 1000);
}
function ScrollTop(scrotop,time) {//滚动高度、滚动时间
    var _scrotop=Number(scrotop);
    $("html,body").animate({scrollTop:_scrotop},time)
};
function SetSS(key,val) {
    var _key=key;
    return window.sessionStorage .setItem(_key,JSON.stringify(val))
};
function GetSS(key){
    var _key=key;
    return JSON.parse(window.sessionStorage .getItem(_key)||'[]');
};
