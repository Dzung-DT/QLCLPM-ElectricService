function showDSHoaDon() {
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

function timHoaDon() {
    var maKH = $("#maKH_input").val().trim();
    var maThang = $("#maThang_input").val().trim();
    $.ajax({
        url: "/tim-kiem-hoa-don",
        type: "POST",
        dataType: "json",
        data: {
            "maKH": maKH,
            "maThang": maThang
        },
        success: function (data) {
            showTable(data);
        }, error: function () {
            alert("FAIL!");
        }
    });
}

function showTable(data) {
    var contentString;
    for (var i = 0; i < data.length; i++) {
        var index = i + 1;
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
            + '<td>' + data[i][7] + '</td>'
            + '<td>' + data[i][8] + '</td>'
            + '<td>' + data[i][9] + '</td>'
            + '<td>' + data[i][10] + '</td>'
            + '<td>' + data[i][11] + '</td>'
            + '<td style="padding: 0 0 0 0"><a data-toggle="tooltip" title="Lập hóa đơn"><button data-toggle="modal" data-target="#modalCreatBillForm" class="btn btn-info center-block ml-1" onclick="lapHoaDon()" style="padding: 3px 6px 3px 6px; border-radius: 54%;"><i class="icon-info22"></i></button></a></td>' +
            +'</tr>';
    }
    $("#bang_hoa_don").html(contentString);
}
