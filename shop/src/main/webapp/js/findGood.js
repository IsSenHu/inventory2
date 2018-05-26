$(function () {
   $("#findGood").click(findGood);
});

function findGood() {
    var goodName = $("input[name='goodName']").val();
    location.href = '/findGood.do?goodName=' + goodName;
}