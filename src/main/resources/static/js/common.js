/**
 * 上传图片
 * @param layui
 * @param btnId 上传按钮的id
 * @param imgType 图片类型(隐藏input)
 * @param tmpPath 暂存图片的地址
 * @param dbPath 数据库对应的字段(对应页面中隐藏的input框)
 */
function uploadImg(layui, btnId, imgType, tmpPath, dbPath) {
    var upload = layui.upload;
    var img = upload.render({
        elem: '#' + btnId + '' //绑定元素
        , url: 'upPic/uploadPic'
        , data: {
            type: $("#" + imgType + "").val()
        }
        , done: function (res) {
            var msg = res.msg;
            if (res.success == true) {
                msg = "上传成功";
                $("#" + tmpPath + "").attr("src", res.data);
                $("#" + dbPath + "").attr("value", res.data);
            }
            layer.alert(msg);
        }
        , error: function () {
            //演示失败状态，并实现重传
            var msgText = $('.msgText');
            msgText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            msgText.find('.demo-reload').on('click', function () {
                img.upload();
            });
        }
    })
}

/**
 * 格式转换（单个时间）
 * @param layui
 * @param timeStr 绑定元素：暂存时间input框对应的id
 * @param dbTime 数据库对应的时间字段名
 */
function initTime(layui, timeStr, dbTime) {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#' + timeStr + ''
        , trigger: 'click'
        , position: 'fixed'
        , type: 'datetime'
        , done: function (value, date) {
            $("#" + dbTime + "").val(new Date(date.year, date.month - 1, date.date, date.hours, date.minutes, date.seconds).getTime())
        }
    });
}

/**
 * 格式转换（时间段）+ 范围判断
 * @param layui
 * @param startTimeStr 暂存开始时间input框对应的id
 * @param endTimeStr 暂存结束时间input框对应的id
 * @param dbStartTime 数据库对应的开始时间字段名
 * @param dbEndTime  数据库对应的结束时间字段名
 */
function initTimeRange(layui, startTimeStr, endTimeStr, dbStartTime, dbEndTime) {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#' + startTimeStr + ''
        , type: 'datetime'
        , done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#' + endTimeStr + '').val()).getTime();
            if (endTime < startDate) {
                layer.msg('结束时间不能小于开始时间');
                $('#' + startTimeStr + '').val($('#' + endTimeStr + '').val());
            }
            $("#" + dbStartTime + "").val(startDate)
        }
    });
    laydate.render({
        elem: '#' + endTimeStr + ''
        , type: 'datetime'
        , done: function (value, date, endDate) {
            var startDate = new Date($('#' + startTimeStr + '').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('结束时间不能小于开始时间');
                $('#' + endTimeStr + '').val($('#' + startTimeStr + '').val());
            }
            $("#" + dbEndTime + "").val(endTime)
        }
    });
}

/**
 * 改变资讯状态
 * @param newsId 资讯id
 * @param obj table 对象
 * @param index 弹出层
 */
function changeValid(newsId, obj, index) {
    $.ajax({
        type: "get",
        url: "admin/zyx/news/changeValid",
        data: {
            id: newsId,
        },
        success: function (ev) {
            if (ev.success) {
                obj.del();
                layer.close(index);
                layer.msg('成功', {icon: 1}, {time: 2000});
                window.location.reload();
            } else {
                layer.msg('连接网络失败，请检查网络设置或联系管理员', {icon: 2}, {time: 2000});
            }
        }

    });
}
