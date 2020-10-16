<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="section__content section__content--p30">
    <div class="row" style="padding-top: 1px;">
        <div class="col-lg-5">
            <!-- TOP CAMPAIGN-->
            <div class="top-campaign" style="padding-bottom: 30px !important;">
                <h3 class="title-3 m-b-30">Thanh toán cho game</h3>

                <div class="card-body card-block" style="padding: 10px 0px 1px !important;">
                    <div class="has-success form-group">
                        <div class="row">
                            <div class="col-lg-7" style="padding-bottom: 15px;">
                                <label for="inputSuccess2i" class=" form-control-label">Nội dung tin nhắn</label>

                                <input type="text" id="request_phonenumber" class="form-control-success form-control" onkeyup="Payment_Game_PriceDetect()">

                            </div>
                            <div class="col-lg-5" style="padding-bottom: 10px;">
                                <label for="inputSuccess2i" class=" form-control-label">Mệnh giá</label>

                                <input id="Payment_Game_PriceDetect" type="text" id="request_phonenumber" class="form-control-success form-control" readonly>

                            </div>
                        </div>
                    </div>
                    <div class="has-danger has-feedback form-group">
                        <button onClick="request();" class="btn btn-info btn-sm" style="padding: 9px 22px;">Gửi yêu cầu</button>
                    </div>

                    <div class="alert alert-info" role="alert" id="hehewahehe124214">
                        Hãy nhập thông tin và tiến hành gửi yêu cầu thành toán!
                    </div>

<!--                    <div class="alert alert-warning" role="alert">
                        Chúng tôi xử lý hàng ngàn giao dịch mỗi ngày, xin vui lòng cung cấp thông tin chính xác, mọi sai sót sẽ dẫn tới lỗi hệ thống, chúng tôi sẽ không hoàn tiền đối với các trường hợp do lỗi người dùng.
                    </div>-->
                </div>

            </div>
            <!--  END TOP CAMPAIGN-->
        </div>

        <div class="col-lg-7">
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
                                <th>Nội dung</th>
                                <th>Mệnh giá</th>
                                <th>Trạng thái</th>
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
//    $("#cardpicker").imagepicker({
//        hide_select: true,
//        show_label: false
//    })

    function Payment_Game_PriceDetect() {
        var data = {
            'Content': $('#request_phonenumber').val()
        };
        $.ajax({
            url: "./Payment/Game/PriceDetect",
            type: "post",
            data: data,
            success: function (result) {
                var obj = JSON.parse(result);
                if (obj.Status == 0) {
                    $("#Payment_Game_PriceDetect").val(obj.Price);
                    return;
                }
                $("#Payment_Game_PriceDetect").val("");
            }
        });
    }

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
            url: "./Payment/Game/History",
            type: "post",
            data: data,
            success: function (result) {
                var h = document.getElementById("table_transaction_history").rows.length - 1;

                var jsonData = JSON.parse(result);

                for (var i = 0; i < jsonData.List.length; i++) {
                    var counter = jsonData.List[i];
                    if ($('#table_transaction_history tbody #' + counter.ID).length) {

                        $status = "Không xác định";
                        $status_style = "color:#117a8b";

                        if (counter.Status == 0) {
                            $status = "Thành công";
                            $status_style = "color:green";
                        } else if (counter.Status == 1) {
                            $status = "Đang xử lý";
                        } else {
                            $status = "Thất bại";
                            $status_style = "color:red";
                        }

                        $('#table_transaction_history tbody #' + counter.ID + " #status").attr('style', $status_style);
                        $('#table_transaction_history tbody #' + counter.ID + " #status").text($status);

                    } else {

                        $html = '<tr id="' + counter.ID + '">';
                        $html += '    <td style="padding-top: 17px;">' + counter.ID + '</td>';


                        $html += '    <td style="padding-top: 17px;">' + counter.MessageContent + '</td>';
                        $html += '    <td style="padding-top: 17px;">' + counter.Price + '</td>';

                        $status = "Không xác định";
                        $status_style = "color:#117a8b";

                        if (counter.Status == 0) {
                            $status = "Thành công";
                            $status_style = "color:green";
                        } else if (counter.Status == 1) {
                            $status = "Đang xử lý";
                        } else {
                            $status = "Thất bại";
                            $status_style = "color:red";
                        }

                        $html += '    <td id="status" style="' + $status_style + '">' + $status + '</td>';

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

    function request() {
        $("#hehewahehe124214").attr('class', 'alert alert-info');
        document.getElementById("hehewahehe124214").innerHTML = "Xin hãy chờ...";

        var data = {
            'MessageContent': $('#request_phonenumber').val()
        };
        $.ajax({
            url: "./Payment/Game/Request",
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
                        message = "Nội dung không hợp lệ.";
                    } else if (obj.Status == 4) {
                        message = "Mệnh giá không hợp lệ.";
                    } else if (obj.Status == 5) {
                        message = "Tài khoản không đủ số dư để thực hiện giao dịch.";
                    } else {
                        message = "Lỗi không xác định.";
                    }
                }
                document.getElementById("hehewahehe124214").innerHTML = message;
                alert(message);
            }
        });
    }
</script>