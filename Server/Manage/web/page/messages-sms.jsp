<%@page import="com.dn.amonir.model.TimeUtils"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.dn.amonir.model.Device"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.dn.amonir.model.Time"%>
<%@page import="com.dn.amonir.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    Request Request8 = (Request) request.getAttribute("Request");

    LinkedHashMap<String, Object> sms_get = com.dn.amonir.model.device.Messages.SMS.Get(Request8.Account.Device.Selected.ID, request);

    ArrayList<com.dn.amonir.dto.device.Messages.SMS> sms_get_list = (ArrayList<com.dn.amonir.dto.device.Messages.SMS>) sms_get.get("List");
%>

<div class="row">
    <div class="col-lg-12">
        <div class="card">
            <div class="card" style="margin-bottom: unset;">
                <div class="card-body">
                    <div class="row">

                        <div class="col-sm-8" style="padding-right: 1px;" id="ghj54oi6htouigfdghfd">
                            <div class="au-card au-card--no-shadow au-card--no-pad m-b-40" id="aucard_fullscreen" style="margin-bottom:0px;">
                                <div class="au-inbox-wrap js-inbox-wrap" style="height:100%;">
                                    <div class="au-message js-list-load" style="">
                                        <div class="au-message__noti" style="padding-bottom: 29px;">
                                            <h3 class="title-3 m-b-30" style="margin-bottom: 0px;margin: -6px;margin-top: 0px;">
                                                <i class="fas fa-sms"></i>
                                            </h3>
                                            <button type="button" class="btn btn-primary" style="float: right;margin-top: -29px;margin-right: 6px;" onclick="aucard_fullscreen(this);">
                                                <i class="fas fa-expand-arrows-alt"></i>
                                            </button>
                                        </div>
                                        <div class="au-message-list" style="">
                                            <%                                                for (int i = 0; i < sms_get_list.size(); i++) {
                                                    com.dn.amonir.dto.device.Messages.SMS cts = sms_get_list.get(i);
                                            %>

                                            <div class="au-message__item <%= cts.Read ? "" : "unread"%>" onClick="get(this,<%= cts.Contacts.ID%>)">
                                                <div class="au-message__item-inner">
                                                    <div class="au-message__item-text">
                                                        <div class="avatar-wrap">
                                                            <div class="avatar">
                                                                <i class="far fa-sms fa-2x" style=" font-size: 38px;color: rebeccapurple;padding: 12px; padding-left: 15px;"></i>
                                                            </div>
                                                        </div>
                                                        <div class="text">
                                                            <h5  class="name text-overflow" style="" ><%= cts.Contacts.Name.isEmpty() ? cts.Contacts.By.Value : cts.Contacts.Name%></h5>
                                                            <p class ="text-overflow"><%= cts.Content%></p>
                                                        </div>
                                                    </div>
                                                    <div class="au-message__item-time">
                                                        <span class="text-overflow"><%= TimeUtils.millisToLongDHMS(cts.Time)%></span>
                                                    </div>

                                                </div>
                                            </div>

                                            <%
                                                }
                                            %>




                                        </div>

                                    </div>
                                    <div class="au-chat">
                                        <div class="au-chat__title">
                                            <button type="button" class="btn btn-primary" style="top: 16px;float: left;left: 42px;margin: 18px;" onClick='aucard_back();'>
                                                <i class="fas fa-angle-left"></i>
                                            </button>
                                            <div class="au-chat-info" style="padding: 12px;    padding-left: 0px;">
                                                <div class="avatar-wrap online">
                                                    <!-- <div class="avatar avatar--small"> -->
                                                    <!-- <i class="fas fa-map-marker-alt fa-2x" style="font-size: 41px;color: rebeccapurple;padding: 4px;padding-left: 17px;"></i> -->
                                                    <!-- </div> -->
                                                </div>
                                                <span class="nick" style="height: 50px;padding-top: 12px;margin-left: 0px;font-size: 16px; font-weight: 600;"></span>
                                            </div>
                                        </div>
                                        <div class="au-chat__content">


                                        </div>
                                    </div>
                                </div>
                            </div>


                        </div>
                        <div class="col-sm-4">
                            <section class="card" style="border: 0px; margin-bottom: unset;">

                                <div class="custom-tab">
                                    <nav>
                                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                            <a class="nav-item nav-link active show" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home" aria-selected="false">
                                                <i class="fas fa-info" style="margin-right: 10px;">
                                                </i>Details
                                            </a>
                                            <!-- <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile" aria-selected="false"><i class="fas fa-archive" style="margin-right: 10px;"></i>Lưu trữ</a> -->
                                        </div>
                                    </nav>
                                    <div class="tab-content pl-3 pt-2" id="nav-tabContent" style="padding-left: 0px !important;">
                                        <div class="tab-pane fade active show" id="custom-nav-home1" role="tabpanel" aria-labelledby="custom-nav-home-tab1">
                                            <table class="table table-hover table-striped table-align-middle mb-0" style="margin-top:-8px;">
                                                <tbody>
                                                    <tr>
                                                        <td style="border-top: unset;">
                                                            Total of SMS
                                                        </td>
                                                        <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= sms_get.get("TotalOfSMS")%>
                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="border-top: unset;">
                                                        Total of SMS content
                                                    </td>
                                                    <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= sms_get.get("TotalOfSMSContent")%>

                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="border-top: unset;">
                                                        Total of SMS content sent
                                                    </td>
                                                    <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= sms_get.get("TotalOfSMSContentSent")%>

                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="border-top: unset;">
                                                        Total of SMS content received
                                                    </td>
                                                    <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= sms_get.get("TotalOfSMSContentReceived")%>

                                                </td>
                                                </tr>


                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


<script>

    function get($this, $id) {
        var domElement = $($this).find("h5.name").get(0);
        $(".au-chat .au-chat__title .au-chat-info .nick").text(domElement.innerHTML);
        $($this).removeClass("unread");

        // var domElement = $($this).find("p#content").get(0);

        var data = {
            'CID': $id,
        };

        $.ajax({
            url: "./Device/Messages/SMS/Details",
            type: "post",
            data: data,
            success: function (result) {
                $("#table_getSMS tbody > tr").remove();
                var obj = JSON.parse(result);

                // var img = $('#mobileNetwork_img'); //Equivalent: $(document.createElement('img'))
                // img.attr('src', obj.mobileNetwork_img);

                // $("#numOfRecords").html(obj.numOfRecords);
                // $("#startDay").html(obj.startDay);

                $('.column_middle').css('display', 'block');

                $lastState = 0;
                $html = '';
                $check = false;
                $x = 0;
                var client = obj.List.reverse(); //client prop is an array

                while (true) {

                    for (var i = $x; i < client.length; i++) {

                        if ((client[i].state != $lastState) && ($lastState != 0)) {
                            $html += '  </div>';
                            $html += ' </div>';
                            $html += ' </div>';
                            $lastState = 0;
                            $check = false;
                            break;
                        }
                        $x++;


                        $lastState = client[i].State;

                        if (client[i].State == 1) {
                            if (!$check) {
                                $html += ' <div class="send-mess-wrap">';
                                // $html += '<span class="mess-time">' + client[i].Time + '</span>';
                                $html += ' <div class="send-mess__inner">';
                                $html += ' <div class="send-mess-list">';
                            }
                            $html += '  <div class="send-mess" onClick="messages_showtime(this)">' + client[i].Content + '</div>';
                            $html += '<div style="text-align: center;display: none;background-color: rgb(242, 242, 242);margin-bottom: 2px;height: 39px;position: initial;border-radius: 8px; /* optional */float: right;"><p style="padding-top: 7px;padding-left: 20px;padding-right: 16px;">' + client[i].Time + '</p></div>';


                        } else if (client[i].State == 2) {

                            if (!$check) {
                                $html += '<div class="recei-mess-wrap">';
                                // $html += '<span class="mess-time">' + client[i].Time + '</span>';
                                $html += '<div class="recei-mess__inner">';
                                $html += '<div class="avatar avatar--tiny">';
                                $html += '<i class="fas fa-user-circle fa-4x" style="font-size: 32px;color: rebeccapurple;"></i>';
                                $html += '</div>';
                                $html += '<div class="recei-mess-list">';

                            }
                            $html += '<div class="recei-mess" onClick="messages_showtime(this)">' + client[i].Content + '</div>';
                            $html += '<div style="text-align: center;display: none;background-color: rgb(242, 242, 242);margin-bottom: 2px;height: 39px;position: initial;border-radius: 8px; /* optional */float: left;"><p style="padding-top: 7px;padding-left: 20px;padding-right: 16px;">' + client[i].Time + '</p></div>';

                        }
                        $check = true;
                    }

                    if ($x >= client.length) {
                        break;
                    }

                }

                $(".au-chat__content").append($html);
                $html = '';

                var $target = $('.au-chat__content');
                $target.animate({
                    scrollTop: $target.height() + 10000000
                }, 0);

            }
        });

    }

    function messages_showtime(element) {
        $(element).next().css("display", "block");

    }
</script>