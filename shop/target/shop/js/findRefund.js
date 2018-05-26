$(function () {
    /**
     * 查询
     * */
   $("#findRefund").click(function () {
       var userId = $("#userId").val().trim();
       location.href = '/refunds.do?userId=' + userId;
   });
   /**
    * 退款
    * */
    $(".refund").click(function () {
       var refundId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/refund.do',
           data : 'refundId=' + refundId,
           success : function (data) {
               var json = $.parseJSON(data);
               if(json.name == "200"){
                   location.reload();
               }else {
                   console.log("发货失败:{" + data + "}");
               }
           },
           error : function (data) {
               console.log("发货失败:{" + data + "}");
           }
       });
    });
});