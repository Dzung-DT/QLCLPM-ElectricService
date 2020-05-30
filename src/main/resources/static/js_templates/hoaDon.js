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
    var tongSodien = 0;
    var tongTien = 0;
    var contentString = "";
    for (var i = 0; i < data.length; i++) {
        var index = i + 1;
        var soDien = data[i][5] - data[i][6];
        tongSodien += soDien;
        var thue = data[i][8] * 100;
        var giaTien = tinhTien(soDien, data[i][7], data[i][8]);
        tongTien += giaTien;
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
            + '<td>' + giaTien + '</td>'
            + '<td>' + data[i][9] + '</td>'
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

function layDonGiaByMDSD() {
    $.ajax({
        url: "/lay-don-gia-by-MDSD",
        type: "POST",
        dataType: "json",
        success: function (data) {
            getDonGiaInfo(data);
        }, error: function () {
            alert("ERROR");
        }
    });
}

var donGiaInfo;
var MDSD_SH;
var MDSD_SHTT;
var giaSH;
var giaSHTT;

function getDonGiaInfo(response) {
    donGiaInfo = response;
    giaSH = Object.values(donGiaInfo)[0];
    MDSD_SH = Object.keys(donGiaInfo)[0];
    giaSHTT = Object.values(donGiaInfo)[1];
    MDSD_SHTT = Object.keys(donGiaInfo)[1];
}

function tinhTien(soDien, MDSD, thue) {
    var giaTien = 0;
    if (MDSD == MDSD_SH) {
        var a = giaSH;
        var b = [50, 50, 100, 100, 100, 100];
        var c = [];
        if (soDien > 500) {
            for (var x = 0; x < 5; x++) {
                giaTien += a[x] * b[x];
            }
            giaTien += a[5] * (soDien - 400);
        } else if (soDien <= 500 && soDien >= 100) {
            var i = 0;
            while (true) {
                soDien = soDien - b[i];
                if (soDien > 0) {
                    c.push(b[i]);
                    i++;
                } else if (soDien < 0) {
                    c.push(100 - soDien * (-1));
                    break;
                } else if (soDien == 0) {
                    c.push(soDien + b[i]);
                    break;
                }
            }
            for (var j = 0; j < c.length; j++) {
                giaTien += a[j] * c[j];
            }
        } else if (soDien < 100 && soDien >= 0) {
            var i = 0;
            while (true) {
                soDien = soDien - b[i];
                if (soDien > 0) {
                    c.push(b[i]);
                    i++;
                } else if (soDien < 0) {
                    c.push(50 - soDien * (-1));
                    break;
                } else if (soDien == 0) {
                    c.push(soDien + b[i]);
                    break;
                }
            }
            for (var j = 0; j < c.length; j++) {
                giaTien += a[j] * c[j];
            }
        }
        return Math.round(giaTien + giaTien * thue);
    }
    if (MDSD == MDSD_SHTT) {
        giaTien = giaSHTT[0] * soDien;
        return Math.round(giaTien + giaTien * thue);
    }
}
