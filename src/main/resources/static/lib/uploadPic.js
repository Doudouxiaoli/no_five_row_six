$(document).ready(function () {

    // 文章上传图片
    $("#uploadBtn").bind("click", function () {
        $('#myModal').modal('show');
    });

    // 文章上传图片
    $(function () {
        // 获取存储图片类型（参考FileUploadTypeUtil.java类）
        var type = $('#type').val();
        $('#fileupload').fileupload({
            type: "POST",
            dataType: 'json',
            url: 'upPic/uploadPic?type=' + type,
            success: function (json) {
                if ($("#myModal").length > 0) {
                    $('#myModal').modal('hide');
                }
                if (json.success) {
                    var path = json.data;
                    $("#picShow").attr("src", path);
                    $("#picShow").show();
                    $("#wpeThumbnailPath").val(path);

                    //仅用于前端个人中心修改头像使用 SQ
                    if ('headimg' == type) {
                        $.ajax({
                            type: "post",
                            cache: false,
                            dataType: "json",
                            url: "app/my/updatePhoto",
                            data: {
                                path: path
                            },
                            success: function (json) {
                                if (!json.success) {
                                    popAlert(json.msg);
                                } else {
                                    popAlert("更新成功！");
                                    setTimeout(function () {
                                        window.location.href = "app/my/index?toDesktop=1";
                                    }, 1000);
                                }
                            }
                        });
                    }

                } else {
                    alert("上传图片过程中有错误发生，请稍后再试。");
                }
            }
        });
    });


});