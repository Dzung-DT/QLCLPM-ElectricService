jQuery.validator.addMethod("checkChar", function (value, element, param) {
    return value.match(new RegExp("." + param + "$"));
});

function showCustomerID() {
    $.ajax({
        url: "/get-customer-id-list",
        type: "POST",
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var optionText = data[i];
                var optionValue = data[i];
                $("#maKH_select").append(new Option(optionText, optionValue));
            }
        }, error: function () {
            alert("FAIL!");
        }
    });
}

function showDSSoDien() {
    $.ajax({
        url: "/danh-sach-so-dien",
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
    $("#form_them_so_dien").validate({
        rules: {
            chiSoCu_input: {
                required: true,
                checkChar: "[0-9]+",
                maxlength: 5
            },
            chiSoMoi_input: {
                required: true,
                checkChar: "[0-9]+",
                maxlength: 5
            }
        },
        messages: {
            chiSoCu_input: {
                required: "Nhập chỉ sổ cũ",
                checkChar: "Chỉ nhập kí tự số",
                maxlength: "Nhỏ hơn 5 chữ số"
            },
            chiSoMoi_input: {
                required: "Vui lòng nhập tên KH",
                checkChar: "Chỉ nhập kí tự số",
                maxlength: "Nhỏ hơn 5 chữ số"
            }
        },
        submitHandler: function () {
            var maKH = $('#maKH_select option:selected').val();
            var maThang = $('#maThang_input').val().trim().replace('/', '');
            var chiSoCu = $('#chiSoCu_input').val().trim();
            var chiSoMoi = $('#chiSoMoi_input').val().trim();

            if (chiSoCu > chiSoMoi) {
                swal("Không thỏa mãn", "Nhập chỉ số mới phải lớn hơn chỉ số cũ", "warning");
            } else {
                $.ajax({
                    url: "/them-so-dien",
                    type: "POST",
                    data: {
                        "maKH": maKH,
                        "maThang": maThang,
                        "chiSoCu": chiSoCu,
                        "chiSoMoi": chiSoMoi
                    },
                    success: function (data) {
                        getSoDienByCustomer(maKH);
                        swal("Done", data, "success");
                    },
                    error: function (data) {
                        swal("Fail", data.responseText, "warning");
                    }
                });
            }
        }
    });
});

function deleteSoDien(idSoDien) {
    swal({
            title: "Are you sure?",
            text: "Do you want to delete this record ?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Delete",
            cancelButtonText: "Cancel",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                $.ajax({
                    url: "/xoa-so-dien",
                    type: "POST",
                    data: {
                        "idSoDien": idSoDien
                    },
                    success: function (data) {
                        swal("Done", data, "success");
                        showDSSoDien();
                    }
                });
            }
        });
}

function getSoDienByCustomer(customerID) {
    if (customerID == "all") {
        hideInput();
        $("#maKH_input").val("");
        showDSSoDien();
    } else {
        showInput();
        $("#maKH_input").val(customerID);
        $.ajax({
            url: "/tim-kiem-dien-ke-KH",
            type: "POST",
            dataType: "json",
            data: {
                "customerID": customerID
            },
            success: function (data) {
                showTable(data);
            },
            error: function () {
                hideInput();
                var contentString = "Thông tin điện kế với mã khách hàng này chưa có. Vui lòng thêm";
                $("#bang_so_dien").html(contentString);
            }
        });
    }
}

function showTable(data) {
    var contentString;
    var stt = 0;
    for (var i = 0; i < data.length; i++) {
        stt++;
        contentString = contentString
            + '<tr>'
            + '<td>' + stt + '</td>'
            + '<td>' + data[i][1] + '</td>'
            + '<td>' + data[i][2] + '</td>'
            + '<td>' + data[i][3] + '</td>'
            + '<td>' + data[i][4] + '</td>'
            + '<td style="padding: 0 0 0 0">' +
            '<a data-toggle="tooltip" title="Remove"><button onclick="deleteSoDien(' + data[i][0] + ')" class="btn btn-danger center-block ml-1" style="padding: 3px 6px 3px 6px; border-radius: 54%;"><i class="icon-trash"></i></button></a>' +
            '<a data-toggle="tooltip" title="Lập hóa đơn"><button data-toggle="modal" data-target="#modalCreatBillForm" class="btn btn-info center-block ml-1" onclick="lapHoaDon()" style="padding: 3px 6px 3px 6px; border-radius: 54%;"><i class="icon-pencil"></i></button></a></td>'
            + '</tr>';
    }
    $("#bang_so_dien").html(contentString);
}

function lapHoaDon() {
    $('#bang_so_dien').find('tr').click(function () {
        var maKH = $(this).find('td').eq(1).text();
        $("#maKH_modalCreatBillForm").html(maKH);
        var maThang = $(this).find('td').eq(2).text();
        $("#maThang_modalCreatBillForm").html(maThang);
        var maHD = maKH + "-" + maThang;
        $("#maHD_modalCreatBillForm").html(maHD);
        var chiSoMoi = $(this).find('td').eq(3).text();
        $("#chiSoMoi_modalCreatBillForm").html(chiSoMoi);
        var chiSoCu = $(this).find('td').eq(4).text();
        $("#chiSoCu_modalCreatBillForm").html(chiSoCu);
        var luongDienTT = chiSoMoi - chiSoCu;
        $("#luongDienTT_modalCreatBillForm").html(luongDienTT);
        $.ajax({
            url: "/lay-MDSD-by-maKH",
            type: "POST",
            dataType: "json",
            data: {
                "maKH": maKH
            },
            success: function (data) {
                $("#loaiDienSuDung_modalCreatBillForm").html(data);
            }
        });
    });
}

function taoHoaDon() {
    var maHD = $("#maHD_modalCreatBillForm").text().trim();
    var maKH = $("#maKH_modalCreatBillForm").text().trim();
    var maThang = $("#maThang_modalCreatBillForm").text().trim();
    var luongDienTT = $("#luongDienTT_modalCreatBillForm").text().trim();
    var loaiDienSD = $("#loaiDienSuDung_modalCreatBillForm").text().trim();
    $.ajax({
        url: "/them-hoa-don",
        type: "POST",
        data: {
            "maHD": maHD,
            "maKH": maKH,
            "maThang": maThang,
            "luongDienTT": luongDienTT,
            "loaiDienSD": loaiDienSD
        },
        success: function (data) {
            $('#modalCreatBillForm').modal('hide');
            swal("Done", data, "success");
        },
        error: function (data) {
            $('#modalCreatBillForm').modal('hide');
            swal("Fail", data.responseText, "warning");
        }
    });
}

function showInput() {
    $("#maThang_input").prop('disabled', false);
    $("#chiSoMoi_input").prop('disabled', false);
    $("#chiSoCu_input").prop('disabled', false);
}

function hideInput() {
    $("#maThang_input").prop('disabled', true);
    $("#chiSoMoi_input").prop('disabled', true);
    $("#chiSoCu_input").prop('disabled', true);
}

