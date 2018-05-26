$(function () {
    $("#lths1").html('+0');
    $("#lths2").html('+0');
    $("#lths3").html('+0');
    $("#lths4").html('+0');
    $("#lths5").html(0);
    $("#lths6").html(0);
    $("#waitVerfyInOrderNumber").html("+0");
    $("#waitVerfyOutOrderNumber").html("+0");
    $("#waitDoDeliverOrderNumber").html("+0");
    $("#warnStockNumber").html("+0");
    window.setInterval("refresh_waitVerfyInOrderNumber('/waitVerfyInOrderNumber.action')", 1000);
    window.setInterval("refresh_waitVerfyOutOrderNumber('/waitVerfyOutOrderNumber.action')", 1000);
    window.setInterval("refresh_waitDoDeliverOrderNumber('/waitDoDeliverOrderNumber.action')", 1000);
    window.setInterval("refresh_warnStockNumber('/warnStockNumber.action')", 1000);
    window.setInterval("refresh_count('/count.action')", 1000);
});
function refresh_count(url) {
    $.ajax({
        type : 'post',
        url : url,
        success : function (data) {
            $("#lths6").html($.parseJSON(data).data);
        },
        error : function (data) {
            console.log("未知错误：{" + data + "}");
        }
    });
}
function refresh_waitVerfyInOrderNumber(url) {
    $.ajax({
        type : 'post',
        url : url,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                $("#waitVerfyInOrderNumber").html('+' + json.data);
                $("#lths1").html('+' + json.data);
            }else {
                $("#waitVerfyInOrderNumber").html('+' + 0);
                $("#lths1").html('+' + 0);
            }
        },
        error : function (data) {
            console.log("未知错误：{" + data + "}");
        }
    });
}
function refresh_waitVerfyOutOrderNumber(url) {
    $.ajax({
        type : 'post',
        url : url,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                $("#waitVerfyOutOrderNumber").html('+' + json.data);
                $("#lths2").html('+' + json.data);
            }else {
                $("#waitVerfyOutOrderNumber").html('+' + 0);
                $("#lths2").html('+' + 0);
            }
        },
        error : function (data) {
            console.log("未知错误：{" + data + "}");
        }
    })
}
function refresh_waitDoDeliverOrderNumber(url) {
    $.ajax({
        type : 'post',
        url : url,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                $("#waitDoDeliverOrderNumber").html('+' + json.data);
                $("#lths3").html('+' + json.data);
            }else {
                $("#waitDoDeliverOrderNumber").html('+' + 0);
                $("#lths3").html('+' + 0);
            }
        },
        error : function (data) {
            console.log("未知错误：{" + data + "}");
        }
    })
}
function refresh_warnStockNumber(url) {
    $.ajax({
        type : 'post',
        url : url,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                $("#warnStockNumber").html('+' + json.data);
                $("#lths4").html('+' + json.data);
                $("#lths5").html(json.data);
            }else {
                $("#warnStockNumber").html('+' + 0);
                $("#lths4").html('+' + 0);
                $("#lths5").html(0);
            }
        },
        error : function (data) {
            console.log("未知错误：{" + data + "}");
        }
    })
}