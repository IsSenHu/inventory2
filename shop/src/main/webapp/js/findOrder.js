$(function () {
    /**
     * 查询订单
     * */
    $("#findOrder").click(function () {
        var orderId = $("#id").val().trim();
        var userId = $("#userId").val().trim();
        var orderStatu = $("#orderStatu").val();
        location.href = '/orders.do?orderId=' + orderId + '&userId=' + userId + '&orderStatu=' + orderStatu;
    });
    /**
     * 确认订单，推送发货信息
     * */
    $(".sure").click(function () {
        var orderId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deliver.do',
            data : 'orderId=' + orderId,
            success : function (data) {
                var json = $.parseJSON(data);
                if(json.code == 200){
                    alert("推送发货消息成功");
                    location.reload();
                }else {
                    alert("推送发货消息失败");
                }
            },
            error : function () {
                console.log("订单确认失败:{" + data + "}");
            }
        });
    });
});