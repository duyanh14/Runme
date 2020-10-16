<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.dn.amonir.model.Device"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.dn.amonir.model.Time"%>
<%@page import="com.dn.amonir.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    Request Request = (Request) request.getAttribute("Request");

    LinkedHashMap<String, Object> contacts_get = com.dn.amonir.model.device.Contacts.Get(Request.Account.Device.Selected.ID, request);

    ArrayList<com.dn.amonir.dto.device.Contacts> contacts_get_list = (ArrayList<com.dn.amonir.dto.device.Contacts>) contacts_get.get("List");
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
                                                <i class="fas fa-address-book"></i>
                                            </h3>
                                            <button type="button" class="btn btn-primary" style="float: right;margin-top: -29px;margin-right: 6px;" onclick="aucard_fullscreen(this);">
                                                <i class="fas fa-expand-arrows-alt"></i>
                                            </button>
                                        </div>
                                        <div class="au-message-list" style="">
                                            <%                                                for (int i = 0; i < contacts_get_list.size(); i++) {
                                                    com.dn.amonir.dto.device.Contacts cts = contacts_get_list.get(i);
                                            %>

                                            <div class="au-message__item " onClick="get(this,<%= cts.ID%>)">
                                                <div class="au-message__item-inner">
                                                    <div class="au-message__item-text">
                                                        <div class="avatar-wrap">
                                                            <div class="avatar">
                                                                <i class="far fa-address-book fa-2x" style=" font-size: 38px;color: rebeccapurple;padding: 12px; padding-left: 15px;"></i>
                                                            </div>
                                                        </div>
                                                        <div class="text">
                                                            <h5  class="name text-overflow" style="margin-top: 14x;" ><%= cts.Name%></h5>
                                                            <!--                                                            <p class ="text-overflow">1</p>-->
                                                        </div>
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
                                                            Total of contacts
                                                        </td>
                                                        <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= contacts_get.get("TotalOfContacts")%>
                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="border-top: unset;">
                                                        Total of contacts phone number
                                                    </td>
                                                    <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= contacts_get.get("TotalOfContactsNumber")%>

                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="border-top: unset;">
                                                        Total of contacts email
                                                    </td>
                                                    <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= contacts_get.get("TotalOfContactsEmail")%>

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
        // var domElement = $($this).find("p#content").get(0);

        var data = {
            'ID': $id,
        };

        $.ajax({
            url: "./Device/Contacts/Details",
            type: "post",
            data: data,
            success: function (result) {

                var obj = JSON.parse(result);
                var listn = obj.Number;
                var liste = obj.Email;

                html = '<div class="row" style="margin-right: unset !important;margin-left: unset !important;margin-top: 12px;padding: 0px !important;">';
                if (listn) {
                    html += '    <div class="col-lg-5" id="gfhgfoi53hou4ehrdgiufdg00" style="margin-bottom: 24px;padding-left: 0px;    margin: 0 auto;">';
                    html += '        <div class="table-responsive table--no-card ">';
                    html += '            <table class="table table-borderless table-striped table-earning">';
                    html += '                <thead>';
                    html += '                    <tr>'; 
                    html += '                        <th style="text-align: center;"><i class="fas fa-phone"></i>';
                    html += '                        </th>';
                    html += '                        <th style="text-align: center;"><i class="fas fa-bookmark"></i>';
                    html += '                        </th>';
                    html += '                    </tr>';
                    html += '                </thead>';
                    html += '                <tbody>';



                    for (var i = 0; i < listn.length; i++) {

                        icon_type = 'question-square';

                        switch (listn[i].Type) {
                            case 1:
                                icon_type = 'home';
                                break;
                            case 2:
                                icon_type = 'mobile';
                                break;
                            case 3:
                                icon_type = 'briefcase';
                                break;
                            case 4:
                                icon_type = 'fax';
                                break;
                            case 5:
                                icon_type = 'fax';
                                break;
                        }

                        html += '                    <tr>';
                        html += '                        <td style="text-align: center;">' + listn[i].Value + '</td>';
                        html += '                        <td style="text-align: center;"> <i class="fas fa-' + icon_type + '"></i></td>';
                        html += '                    </tr>';
                    }


                    html += '                </tbody>';
                    html += '            </table>';
                    html += '        </div>';
                    html += '    </div>';
                }
                if (liste) {
                    html += '    <div class="col-lg-7 " style="margin-bottom: 6px;padding: 0px !important;    margin: 0 auto;">';
                    html += '        <div class="table-responsive table--no-card ">';
                    html += '            <table class="table table-borderless table-striped table-earning">';
                    html += '                <thead>';
                    html += '                    <tr>';
                    html += '                        <th style="text-align: center;"><i class="fas fa-envelope"></i>';
                    html += '                        </th>';
                    html += '                        <th style="text-align: center;"><i class="fas fa-bookmark"></i>';
                    html += '                        </th>';
                    html += '                    </tr>';
                    html += '                </thead>';
                    html += '                <tbody>';




                    for (var i = 0; i < liste.length; i++) {

                        icon_type = 'question-square';

                        switch (liste[i].Type) {
                            case 1:
                                icon_type = 'home';
                                break;
                            case 2:
                                icon_type = 'mobile';
                                break;
                            case 3:
                                icon_type = 'briefcase';
                                break;
                            case 4:
                                icon_type = 'fax';
                                break;
                            case 5:
                                icon_type = 'fax';
                                break;
                        }

                        html += '                    <tr>';
                        html += '                        <td style="text-align: center;">' + liste[i].Value + '</td>';
                        html += '                        <td style="text-align: center;"> <i class="fas fa-' + icon_type + '"></i></td>';
                        html += '                    </tr>';
                    }

                    html += '                </tbody>';
                    html += '            </table>';
                    html += '        </div>';
                    html += '    </div>';
                    html += '</div>';
                }

                $(".au-chat__content").append(html);

            }
        });


    }
</script>