$(function () {
  $("#ltt").click(one);
  $("#ltt2").click(two);
  $("#ltt3").click(three);
});

function one() {
    var storesName = getInput('storesName');
    var address = getInput('address');
    var sports = '';
    $("input[name='sport']").each(function () {
        if($(this).prop("checked")){
            sports = sports + $(this).val() + ',';
        }
    });
    var sportStr = '';
    if(sports.length >= 2){
        sportStr = sports.substr(0, sports.length - 1);
    }
    $.ajax({
        type : 'post',
        url : '/one.do',
        data : 'storesName=' + storesName + '&address=' + address + '&sportStr=' + sportStr,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.name == 'ok'){
                location.href = '/toTwo.do'
            }else if(json.name == 'noLogin'){
                alert("没有登录");
                location.href = '/toLogin.do'
            }else if(json.name == 'no'){
                alert("不能进行该操作")
            }else {
                alert("输入有错误");
            }
        },
        error : function (data) {
            alert("发生错误:" + data);
        }
    });
}

function two() {
    $("#pageForm").ajaxSubmit({
        type : 'post',
        url : '/two.do',
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.name == 'success'){
                location.href = '/toThree.do';
            }else if(json.name == 'noLogin'){
                alert("你没有登陆");
                location.href = '/toLogin.do';
            }else if(json.name == 'haveError'){
                alert("输入有错误");
            }else if(json.name == 'no'){
                alert("不能进行该操作")
            }
        },
        error : function (data) {
            alert("发生错误:" + data);
        }
    })
}

function three() {
    $("#pageForm3").ajaxSubmit({
        type : 'post',
        url : '/three.do',
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.name == 'noLogin'){
                alert("你没有登陆");
                location.href = '/toLogin.do'
            }else if(json.name == 'noIn'){
                alert("没有表单输入");
            }else if(json.name == 'haveError'){
                alert("输入有误");
            }else if(json.name == 'success'){
                location.href = '/toFouth.do';
            }else if(json.name == 'uploadFaile'){
                alert("上传文件失败");
            }else if(json.name == 'no'){
                alert("不能进行该操作")
            }
        },
        error : function (data) {
            alert("发生错误:" + data);
        }
    })
}

function getInput(name) {
    var value = $("input[name='" + name + "']").val().trim();
    return value;
}
