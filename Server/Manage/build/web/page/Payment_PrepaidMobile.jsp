<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="section__content section__content--p30">
    <div class="row" style="padding-top: 1px;">
        <div class="col-lg-5">
            <!-- TOP CAMPAIGN-->
            <div class="top-campaign" style="padding-bottom: 30px !important;">
                <h3 class="title-3 m-b-30">T.bao di động trả trước</h3>

                <div class="picker">
                    <select class="image-picker show-html" id="cardpicker">
                        <option data-img-src="./images/viettel.png" value="1">Viettel</option>
                        <option data-img-src="./images/vinaphone.jpg" value="2">Vinaphone</option>
                        <option data-img-src="./images/mobifone.jpg" value="3">Mobifone</option>
                    </select>
                </div>

                <div class="card-body card-block" style="padding: 10px 0px 1px !important;">
                    <div class="has-success form-group">
                        <div class="row">
                            <div class="col-lg-7" style="padding-bottom: 15px;">
                                <label for="inputSuccess2i" class=" form-control-label">Số điện thoại</label>

                                <input type="text" id="request_phonenumber" class="form-control-success form-control">

                            </div>
                            <div class="col-lg-5" style="padding-bottom: 10px;">
                                <label for="inputSuccess2i" class=" form-control-label">Mệnh giá</label>

                                <select name="select3213213" id="request_price" class="form-control">
                                    <option value="20000">20.000 VNĐ</option>
                                    <option value="50000">50.000 VNĐ</option>
                                    <option value="100000">100.000 VNĐ</option>
                                    <option value="200000">200.000 VNĐ</option>
                                    <option value="500000">500.000 VNĐ</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="has-danger has-feedback form-group">
                        <button onClick="add();" class="btn btn-info btn-sm" style="padding: 8px 22px;">Gửi yêu cầu</button>
                    </div>

                    <div class="alert alert-info" role="alert" id="hehewahehe124214">
                        Hãy nhập thông tin và tiến hành gửi yêu cầu thành toán!
                    </div>
                </div>

            </div>
            <!--  END TOP CAMPAIGN-->
        </div>

        <div class="col-lg-7">
            <!-- TOP CAMPAIGN-->

            <!--  END TOP CAMPAIGN-->
        </div>
    </div>

    <div class="row" style="padding-top: 1px;">
        <div class="col-lg-12">
            <!-- TOP CAMPAIGN-->
            <div class="top-campaign" style="padding-bottom: 30px !important;">
                <h3 class="title-3 m-b-30">Lịch sử</h3>

                <div class="alert alert-info" role="alert">
                    Tự động làm mới sau
                    <b id="history_reload_time_countdown">1s</b>.
                </div>

                <div class="row">
                    <div class="col-lg-6">

                    </div>
                    <div class="col-lg-6 ">
                        <div style="float:right;padding-bottom: 15px;">
                            <button type="button" class="btn btn-secondary" style="width:50px" onClick="history_back();">
                                <i class="fas fa-chevron-left"></i></button>
                            <label id="label_history_page"  style=" padding: 0px 15px;"> 1</label>
                            <button type="button" class="btn btn-secondary" style="width:50px" onClick="history_next();">
                                <i class="fas fa-chevron-right"></i></button>
                        </div>

                    </div>
                </div>


                <div class="table-responsive m-b-40" style="margin-bottom: 1rem;">
                    <table class="table table-borderless table-striped table-earning" id="table_transaction_history">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nhà mạng</th>
                                <th>Số điện thoại</th>
                                <th>Mệnh giá</th>
                                <th>Đã chuyển</th>
                                <th>Thời gian</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>


                        </tbody>
                    </table>
                </div>

            </div>
            <!--  END TOP CAMPAIGN-->
        </div>
    </div>
</div>

<script>
    he(1);

    $('select').on('change', function () {

        he(this.value);
    });


    function he(x) {
        if (x == 1) {

            $('#request_price')
                    .empty()
                    .append('<option value="40000">40.000 VNĐ</option><option value="50000">50.000 VNĐ</option><option value="100000">100.000 VNĐ</option><option value="200000">200.000 VNĐ</option><option value="500000">500.000 VNĐ</option>');
        } else if (x == 2) {
            $('#request_price')
                    .empty()
                    .append('<option value="50000">50.000 VNĐ</option><option value="100000">100.000 VNĐ</option><option value="200000">200.000 VNĐ</option><option value="500000">500.000 VNĐ</option>');
        } else if (x == 3) {
            $('#request_price')
                    .empty()
                    .append('<option value="20000">20.000 VNĐ</option><option value="50000">50.000 VNĐ</option><option value="100000">100.000 VNĐ</option><option value="200000">200.000 VNĐ</option><option value="500000">500.000 VNĐ</option>');
        }

    }
    $("#cardpicker").imagepicker({
        hide_select: true,
        show_label: false
    })

    var history_page = 1;
    var reload_request_history_cd = 0;
    reload_request_history();
    var reload_request_history_cd = 5;

    setInterval(reload_request_history, 1000);

    function history_next() {
        history_page = history_page + 1;

        var h = document.getElementById("table_transaction_history").rows.length - 1;

        for (var x = 0; x < h; x++) {
            $('#table_transaction_history tbody tr:last').remove();
        }

        history_load();
    }

    function history_back() {
        if (history_page == 1) {
            alert("Đang ở trang đầu.");
            return;
        }
        history_page = history_page - 1;

        var h = document.getElementById("table_transaction_history").rows.length - 1;

        for (var x = 0; x < h; x++) {
            $('#table_transaction_history tbody tr:last').remove();
        }


        history_load();
    }

    function history_load() {
        $('#label_history_page').text(history_page);

        var data = {
            'Page': history_page,
            'Limit': 10
        };
        $.ajax({
            url: "./Payment/PrepaidMobile/History",
            type: "post",
            data: data,
            success: function (result) {
                var h = document.getElementById("table_transaction_history").rows.length - 1;

                var jsonData = JSON.parse(result);

                for (var i = 0; i < jsonData.List.length; i++) {
                    var counter = jsonData.List[i];
                    if ($('#table_transaction_history tbody #' + counter.ID).length) {

                        $('#card_history tbody #' + counter.ID + " #delivered").text(counter.Delivered);

                    } else {

                        $html = '<tr id="' + counter.ID + '">';
                        $html += '    <td style="padding-top: 17px;">' + counter.ID + '</td>';

                        $carrier = "Không xác định";

                        if (counter.Carrier == 1) {
                            $carrier = "Viettel";
                        } else if (counter.Carrier == 2) {
                            $carrier = "Vinaphone";
                        } else if (counter.Carrier == 3) {
                            $carrier = "Mobifone";
                        }

                        $html += '    <td style="padding-top: 17px;">' + $carrier + '</td>';
                        $html += '    <td style="padding-top: 17px;">' + counter.PhoneNumber + '</td>';
                        $html += '    <td style="padding-top: 17px;">' + counter.Price + '</td>';
                        $html += '    <td style="padding-top: 17px;" id="delivered">' + counter.Delivered + '</td>';
                        $html += '    <td style="padding-top: 17px;">' + counter.Date + '</td>';

                        $html += '    <td ><input style=" width: 200px;" value="' + counter.Note + '" onkeyup="note(' + counter.ID + ',this)" type="text" id="text-input" name="text-input" placeholder="" class="form-control"></td>';

                        $html += '    <td id="action" class="process">';

                        $html += ' <button id="cancel" type="button" class="btn btn-secondary" style="width:50px" onClick="run(' + counter.ID + ');"> <i class="fas fa-random"></i></button>';

                        $html += '    </td>';

                        $html += '</tr>';

                        if (h == 0) {
                            $('#table_transaction_history tbody').append($html);
                        } else {
                            $('#table_transaction_history tbody').prepend($html);

                        }
                        ////////////////////////
                    }
                }
                //// giới hạn table row
                h = document.getElementById("table_transaction_history").rows.length - 1;
                if (h > 10) {
                    for (var x = 0; x < h - 10; x++) {
                        $('#table_transaction_history tbody tr:last').remove();
                    }
                }
            }
        });
    }
    function reload_request_history() {
        if (reload_request_history_cd == 0) {

            history_load();

            reload_request_history_cd = 5;
            return;
        }
        document.getElementById("history_reload_time_countdown").innerHTML = reload_request_history_cd + "s";
        reload_request_history_cd = reload_request_history_cd - 1;
    }


    function run(id) {
        var data = {
            'ID': id
        };
        $.ajax({
            url: "./Payment/PrepaidMobile/Run",
            type: "post",
            data: data,
            success: function (result) {
                var obj = JSON.parse(result);
                if (obj.Status == 0) {
                    alert("Đã gửi yêu cầu xử lý đơn #" + id + ".");
                } else {
                    alert("Gặp vấn đề khi yêu cầu xử lý đơn #" + id + ".");
                }

            }
        });
    }

    function note(id, thiz) {

        var data = {
            'ID': id,
            'Value': $(thiz).val(),
        };
        $.ajax({
            url: "./Payment/PrepaidMobile/Note",
            type: "post",
            data: data,
            success: function (result) {

            }
        });
    }

    function add() {
        $("#hehewahehe124214").attr('class', 'alert alert-info');
        document.getElementById("hehewahehe124214").innerHTML = "Xin hãy chờ...";

        var data = {
            'PhoneNumber': $('#request_phonenumber').val(),
            'Carrier': $('#cardpicker').val(),
            'Price': $('#request_price').val()
        };
        $.ajax({
            url: "./Payment/PrepaidMobile/Add",
            type: "post",
            data: data,
            success: function (result) {
                var obj = JSON.parse(result);
                message = obj.Message;
                if (obj.Status == 0) {
                    $("#request_phonenumber").val("");
                    $("#hehewahehe124214").attr('class', 'alert alert-success');
                    message = "Giao dịch đang được xử lý, theo dõi ở phần lịch sử.";
                } else {
                    $("#hehewahehe124214").attr('class', 'alert alert-warning');
                    if (obj.Status == 3) {
                        message = "Tài khoản không đủ số dư để thực hiện giao dịch.";
                    } else if (obj.Status == 4) {
                        message = "Số điện thoại không hợp lệ.";
                    } else {
                        message = "Lỗi không xác định.";
                    }
                }
                document.getElementById("hehewahehe124214").innerHTML = message;
            }
        });
    }
</script>