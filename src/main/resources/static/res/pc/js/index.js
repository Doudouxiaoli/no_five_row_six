$(function () {
    var wW=$(window).width();
    var wH=$(window).outerHeight();
    $(window).scroll(function () {
        var scrollTop=document.documentElement.scrollTop;
    });
    $(window).resize(function () {

    });
});
function calH(ele,rate) {
    var eleH= $(ele).height($(ele).width()/rate);
    return eleH;
}
