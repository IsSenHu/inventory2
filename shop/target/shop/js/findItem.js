$(function () {
    $("#findItem").click(findItem);
});

function findItem() {
    var brandId = getSelect('brandId');
    var sportItemId = getSelect('sportItemId');
    var applyerId = getSelect('applyerId');
    var materialId = getSelect('materialId');
    var specificationId = getSelect('specificationId');
    location.href = "/pageItem.do?brandId=" + brandId + '&sportItemId=' + sportItemId + '&applyerId=' + applyerId + '&materialId=' + materialId + '&specificationId=' + specificationId;
}

function getSelect(name) {
    var value = $("select[name='" + name + "']").val();
    return value;
}