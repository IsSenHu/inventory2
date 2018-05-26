$(function () {
   $("#closeGoodInfo").click(closeGoodInfo);
   $("#showGoodInfo").click(showGoodInfo);
   $("#saveGoodInfo").click(saveGoodInfo);

   $("#showDesPic").click(showDesPic);
   $("#closeDesPic").click(closeDesPic);
   $("#saveDesPic").click(saveDesPic);

   $("#showOtherPics").click(showOtherPics);
   $("#closeOtherPics").click(closeOtherPics);
   $("#saveOtherPic").click(saveOtherPic);
   $(".deletepic").click(deletepic);

   $("#showCoupon").click(showCoupon);
   $("#closeCoupons").click(closeCoupons);
   $("#saveCoupon").click(saveCoupon);

   $("#showPromotions").click(showPromotions);
   $("#closePromotions").click(closePromotions);
   $("#savePromotion").click(savePromotion);

   $("#showPostage").click(showPostage);
   $("#updatePostage").click(updatePostage);
   $("#closePostage").click(closePostage);
   
   $("#upGood").click(function () {
       var goodId = $("input[name='goodId']").val();
       $.ajax({
           type : 'post',
           url : '/upGoodById.do',
           data : 'goodId=' + goodId,
           success : function (data) {
               var json = $.parseJSON(data);
               if(json.name == '200'){
                   location.reload();
               }else {
                   alert("上架失败");
               }
           }
       });
   });

    $("#downGood").click(function () {
        var goodId = $("input[name='goodId']").val();
        $.ajax({
            type : 'post',
            url : '/downGoodById.do',
            data : 'goodId=' + goodId,
            success : function (data) {
                var json = $.parseJSON(data);
                if(json.name == '200'){
                    location.reload();
                }else {
                    alert("下架失败");
                }
            }
        });
    });
});

function closeGoodInfo() {
    $("#goodInfo").css("display", "none");
}

function openGoodInfo() {
    $("#goodInfo").css("display", "block");
}

function showGoodInfo() {
    var goodId = $("input[name='goodId']").val();
    $.ajax({
        type : 'post',
        url : '/findGoodById.do',
        data : 'goodId=' + goodId,
        success : function (data) {
            var json = $.parseJSON(data);
            var goodName = json.goodName;
            var originalPrice = json.originalPrice;
            var salePrice = json.salePrice;
            $("input[name='newGoodName']").val(goodName);
            $("input[name='newOriginalPrice']").val(originalPrice);
            $("input[name='newSalePrice']").val(salePrice);
            openGoodInfo();
        },
        error : function (data) {
            alert("请求出错：{" + data + "}");
        }
    });
}
function saveGoodInfo() {
    var goodId = $("input[name='goodId']").val();
    var goodName = $("input[name='newGoodName']").val();
    var originalPrice = $("input[name='newOriginalPrice']").val();
    var salePrice = $("input[name='newSalePrice']").val();
    $.ajax({
        type : 'post',
        url : '/saveGoodInfo.do',
        data : 'goodId=' + goodId + '&goodName=' + goodName + '&originalPrice=' + originalPrice + '&salePrice=' + salePrice,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                closeGoodInfo();
                showGoodInfo();
            }else if(json.code == 400) {
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("请求出错：{" + data + "}");
        }
    });
}

function showDesPic() {
    $("#desPic").css("display", "block");
}
function closeDesPic() {
    $("#desPic").css("display", "none");
}
function saveDesPic() {
    var goodId = $("input[name='goodId']").val();
    $("#newDesPic").ajaxSubmit({
        type : 'post',
        url : '/saveDesPic.do',
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                location.href = '/desGood.do?goodId=' + goodId;
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("发生错误:{" + data + "}");
        }
    })
}

function showOtherPics() {
    var goodId = $("input[name='goodId']").val();
    $.ajax({
        type : 'post',
        url : '/otherPics.do',
        data : 'goodId=' + goodId,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                $("#pics").empty();
                var pics = json.data;
                for (key in pics){
                    var pic = pics[key];
                    var tr = '<tr>' +
                                '<td style="width: 80%; text-align: center">' +
                                    '<img style="width: 100%; height: 200px;" src="' + pic + '"/>' +
                                '</td>' +
                                '<td style="text-align: center;">' +
                                    '<button onclick="deletepic(this)" value="' + key + '" class="btn btn-danger deletepic">删除</button>' +
                                '</td>' +
                            '</tr>';
                    console.log(tr);
                    $("#pics").append(tr);
                }
                $("#otherPics").css("display", "block");
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function closeOtherPics() {
    $("#otherPics").css("display", "none")
}
function saveOtherPic() {
    var goodId = $("input[name='goodId']").val();
    $("#newOtherPic").ajaxSubmit({
        type : 'post',
        url : '/saveOtherPic.do',
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                closeOtherPics();
                showOtherPics();
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("发生错误:{" + data + "}");
        }
    })
}
function deletepic(obj) {
    var index = obj.value;
    var goodId = $("input[name='goodId']").val();
    $.ajax({
        type : 'post',
        url : '/deletepic.do',
        data : 'index=' + index + '&goodId=' + goodId,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                closeOtherPics();
                showOtherPics()
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("发生错误:{" + data + "}");
        }
    });
}

function showCoupon() {
    var goodId = $("input[name='goodId']").val();
    $("#couponss").empty();
    $.ajax({
        type : 'post',
        url : '/coupons.do',
        data : 'goodId=' + goodId,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                var coupons = json.data;
                for(index in coupons){
                    var coupon = coupons[index];
                    var tr = '<tr>' +
                                '<td style="text-align: center;">' + '满' + coupon.need + '元免' + coupon.free + '元</td>' +
                                '<td style="text-align: center;">' + coupon.need + '元</td>' +
                                '<td style="text-align: center;">' + coupon.free + '元</td>' +
                                '<td style="text-align: center;">' + coupon.goodName + '</td>' +
                                '<td style="text-align: center;">' + coupon.expiryTimeStr + '</td>' +
                                '<td style="text-align: center;">' +
                                    '<button value="' + coupon.id + '" class="btn btn-danger" onclick="deleteCoupons(this)">删除</button>' +
                                '</td>' +
                             '</tr>';
                    $("#couponss").append(tr);
                }
                $("#coupons").css("display", "block");
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function saveCoupon() {
    var goodId = $("input[name='goodId']").val();
    var need = $("input[name='need']").val();
    var free = $("input[name='free']").val();
    var expiryTimeLong = $("input[name='expiryTimeLong']").val();
    $.ajax({
        type : 'post',
        url : '/addCoupon.do',
        data : 'goodId=' + goodId + '&need=' + need + '&free=' + free + '&expiryTimeLong=' + expiryTimeLong,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert("添加成功！")
                closeCoupons();
                showCoupon();
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function deleteCoupons(obj) {
    var id = obj.value;
    $.ajax({
        type : 'post',
        url : '/deleteCoupon.do',
        data : "id=" + id,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert("删除成功！")
                closeCoupons();
                showCoupon();
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function closeCoupons() {
    $("#coupons").css("display", "none");
}

function showPromotions() {
    var goodId = $("input[name='goodId']").val();
    $.ajax({
        type : 'post',
        url : '/promotions.do',
        data : 'goodId=' + goodId,
        success : function (data) {
            var promotions = $.parseJSON(data).data;
            $("#promotionss").empty();
            for(index in promotions){
                var promotion = promotions[index];
                var tr = '<tr>' +
                            '<td style="text-align: center">' + '满' + promotion.number + '打' + promotion.discount + '折' +'</td>' +
                            '<td style="text-align: center">' + promotion.number + '</td>' +
                            '<td style="text-align: center">' + promotion.discount + '</td>' +
                            '<td style="text-align: center">' + promotion.goodName + '</td>' +
                            '<td style="text-align: center">' +
                                '<button value="' + promotion.spId + '" class="btn btn-danger" onclick="deletePromotion(this)">删除</button>'
                            '</td>' +
                         '</tr>'
                $("#promotionss").append(tr);
            }
            $("#promotions").css("display", "block");
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}

function deletePromotion(obj) {
    var spId = obj.value;
    $.ajax({
        type : 'post',
        url : '/deletePromotion.do',
        data : 'spId=' + spId,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                closePromotions();
                showPromotions();
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function closePromotions() {
    $("#promotions").css("display", "none");
}
function savePromotion() {
    var goodId = $("input[name='goodId']").val();
    var number = $("input[name='number']").val();
    var discount = $("input[name='discount']").val();
    $.ajax({
        type : 'post',
        url : '/savePromotion.do',
        data : 'goodId=' + goodId + '&number=' + number + '&discount=' + discount,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                closePromotions();
                showPromotions();
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 400){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}

function showPostage() {
    var goodId = $("input[name='goodId']").val();
    $.ajax({
        type : 'post',
        url : '/showPostage.do',
        data : 'goodId=' + goodId,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                var x = json.data;
                var str;
                if(x.isPostage == 'noPostage'){
                    str = '包邮：没有邮费';
                }else {
                    str = '不包邮：邮费' + x.postageMoney + '元';
                }
                $("#isPostage").html(str);
                $("#postage").css("display", "block");
            }else if(json.code == 400) {
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function updatePostage() {
    var goodId = $("input[name='goodId']").val();
    var isPostage = $("select[name='isPostage']").val();
    var postageMoney = $("input[name='postageMoney']").val();
    $.ajax({
        type : 'post',
        url : '/updatePostage.do',
        data : 'goodId=' + goodId + '&isPostage=' + isPostage + '&postageMoney=' + postageMoney,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.code == 200){
                alert(json.des);
                closePostage();
                showPostage();
            }else if(json.code == 400){
                alert(json.des);
            }else if(json.code == 401){
                alert(json.des);
            }else if(json.code == 500){
                alert(json.des);
            }
        },
        error : function (data) {
            alert("未知错误：{" + data + "}");
        }
    });
}
function closePostage() {
    $("#postage").css("display", "none");
}
