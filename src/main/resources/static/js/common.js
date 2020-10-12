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
        , url: 'admin/upload/uploadPic'
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
 * @param timeStr 暂存时间input框对应的id
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
                $('#' + startTimeStr + '').val($('#wnEndTimeStr').val());
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
                $('#' + endTimeStr + '').val($('#wnStartTimeStr').val());
            }
            $("#" + dbEndTime + "").val(endTime)
        }
    });
}

/**
 * 获取医生列表(多选树形结构)
 * @param layui
 * @param expertIds 专家的id串，用于回显数据
 */
function getExperts(layui, expertIds) {
    var expertSelect;
    var expertsId = document.getElementById("wnExpertId");
    var expertsName = document.getElementById("wnExpert");
    $.ajax({
        url: 'admin/briefing/getExpertList',
        type: 'get',
        success: function (res) {
            if (res.success) {
                //渲染下拉框
                var expertArrayArrayList = res.data;
                //回显专家
                var strArr = expertIds.split(",");
                var initExpert = [];
                strArr.forEach(function (data, index, arr) {
                    initExpert.push(+data);
                })
                expertSelect = layui.xmSelect.render({
                    el: '#expert',
                    prop: {
                        name: 'weName',
                        value: 'weId',
                    },
                    filterable: 'true',//开启搜索
                    tips: '请选择专家',
                    empty: '暂无数据',
                    max: 5,
                    theme: {
                        maxColor: 'orange',
                    },
                    height: '150px',
                    language: 'zn',
                    data: expertArrayArrayList,
                    initValue: initExpert,
                    model: {
                        label: {
                            type: 'text',
                            text: {
                                separator: ',',
                            }
                        }
                    },
                    on: function (data) {
                        var array = data.arr;
                        expertsId.value = '';
                        expertsName.value = '';
                        for (var i = 0; i < array.length; i++) {
                            if (i == array.length - 1) {
                                expertsId.value += array[i].weId;
                                expertsName.value += array[i].weName;
                            } else {
                                expertsId.value += array[i].weId + ",";
                                expertsName.value += array[i].weName + ",";
                            }
                        }
                    },
                })

            } else {
                layer.msg(res.message);
            }
        }
    });
}
