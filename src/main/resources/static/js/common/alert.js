//tip是提示信息，type:'success'是成功信息，'danger'是失败信息,'info'是普通信息
function showTip(tip, type,icon) {
    var $tip = $('#'+type);
    if ($tip.length == 0) {
        $tip = $('<span id="'+type+'" style="position:absolute;top:50px;left: 50%;z-index:9999;"><i class="'+icon+'" aria-hidden="true"></i><span id="tip-content'+type+'"></span></span>');
        $('body').append($tip);
    }
    $("#tip-content"+type).text(tip);
    $tip.stop(true).attr('class', 'alert alert-' + type).css('margin-left', -$tip.outerWidth() / 2).fadeIn(300).delay(1000).fadeOut(300);
}

var $alert ={
    success:function(tip){
        showTip(tip,'success','fa fa-check-circle-o');
    },
    warning:function(tip){
        showTip(tip,'warning','fa fa-exclamation-triangle');
    },
    danger:function (tip) {
        showTip(tip,'danger','fa fa-minus-circle');
    },
    info:function (tip) {
        showTip(tip,'info','fa fa-comment-o');
    }
}