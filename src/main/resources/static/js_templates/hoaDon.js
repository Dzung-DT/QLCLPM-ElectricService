function showDSHoaDon() {
    $("#bang_hoa_don").empty();
    $.ajax({
        url: "/danh-sach-hoa-don",
        type: "POST",
        dataType: "json",
        success: function (data) {
            showTable(data);
        }, error: function () {
            alert("FAIL!");
        }
    });
}

$(function () {
    $("#form_tim_hoa_don").validate({
        rules: {
            maKH: {
                digits: true,
                minlength: 4,
                maxlength: 4
            },
            maThang: {
                digits: true,
                minlength: 6,
                maxlength: 6
            }
        },
        messages: {
            maKH: {
                digits: "Nhập kí tự số",
                minlength: "Có 4 chữ số",
                maxlength: "Có 4 chữ số"
            },
            maThang: {
                digits: "Nhập kí tự số",
                minlength: "Có 6 chữ số",
                maxlength: "Có 6 chữ số"
            }
        },
        submitHandler: function () {
            var maKH = $("#maKH").val();
            var maThang = $("#maThang").val();
            $.ajax({
                url: "/tim-kiem-hoa-don",
                type: "POST",
                dataType: "json",
                data: {
                    "maKH": maKH,
                    "maThang": maThang
                },
                success: function (data) {
                    swal("Done", "Lấy thông tin thành công", "success");
                    showTable(data);
                }, error: function (data) {
                    swal("Fail", data.responseText, "warning");
                }
            });
        }
    });
});

function showTable(data) {
    console.log(data);
    var tongSodien = 0;
    var tongTien = 0;
    var contentString = "";
    for (var i = 0; i < data.length; i++) {
        var index = i + 1;
        var soDien = data[i][5] - data[i][6];
        tongSodien += soDien;
        tongTien += data[i][9];
        var thue = data[i][8] * 100;
        contentString = contentString
            + '<tr>'
            + '<td>' + index + '</td>'
            + '<td>' + data[i][0] + '</td>'
            + '<td>' + data[i][1] + '</td>'
            + '<td>' + data[i][2] + '</td>'
            + '<td>' + data[i][3] + '</td>'
            + '<td>' + data[i][4] + '</td>'
            + '<td>' + data[i][5] + '</td>'
            + '<td>' + data[i][6] + '</td>'
            + '<td>' + soDien + '</td>'
            + '<td>' + data[i][7] + '</td>'
            + '<td>' + thue + ' %</td>'
            + '<td>' + data[i][9] + '</td>'
            + '<td>' + data[i][10] + '</td>'
            + '<td style="padding: 0 0 0 0"><a data-toggle="tooltip" title="Lập hóa đơn"><button class="btn btn-info center-block ml-1" onclick="showHoaDon()" style="padding: 3px 6px 3px 6px; border-radius: 54%;"><i class="icon-info22"></i></button></a></td>' +
            +'</tr>';
    }
    $("#bang_hoa_don").html(contentString);
    $("#tongSoDien").text(tongSodien + " Kwh");
    $("#tongTien").text(tongTien + " VNĐ");
}

function layMaKH() {
    $.ajax({
        url: "/lay-maKH",
        type: "POST",
        dataType: "json",
        success: function (data) {
            $("#maKH").autocomplete({
                source: data
            });
        }, error: function (data) {
            swal("Fail", data.responseText, "warning");
        }
    });
}

function layMaThang() {
    $.ajax({
        url: "/lay-maThang",
        type: "POST",
        dataType: "json",
        success: function (data) {
            $("#maThang").autocomplete({
                source: data
            });
        }, error: function (data) {
            swal("Fail", data.responseText, "warning");
        }
    });
}

function showHoaDon() {
    swal("Done", "OK", "success");
}
