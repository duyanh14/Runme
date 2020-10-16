<%@page import="com.dn.amonir.model.TimeUtils"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.dn.amonir.model.Device"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.dn.amonir.model.Time"%>
<%@page import="com.dn.amonir.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    Request Request8 = (Request) request.getAttribute("Request");

    LinkedHashMap<String, Object> sms_get = com.dn.amonir.model.device.Location.History.Get(Request8.Account.Device.Selected.ID, request);


    ArrayList<com.dn.amonir.dto.device.Location.History> sms_get_list = (ArrayList<com.dn.amonir.dto.device.Location.History>) sms_get.get("List");
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
                                                <i class="fas fa-history"></i>
                                            </h3>
                                            <button type="button" class="btn btn-primary" style="float: right;margin-top: -29px;margin-right: 6px;" onclick="aucard_fullscreen(this);">
                                                <i class="fas fa-expand-arrows-alt"></i>
                                            </button>
                                        </div>
                                        <div class="au-message-list" style="">
                                            <%                                                for (int i = 0; i < sms_get_list.size(); i++) {
                                                    com.dn.amonir.dto.device.Location.History cts = sms_get_list.get(i);
                                            %>

                                            <div class="au-message__item <%= cts.Read ? "" : "unread"%>" onClick="get(this, '<%= cts.ID%>')">
                                                <div class="au-message__item-inner">
                                                    <div class="au-message__item-text">
                                                        <div class="avatar-wrap">
                                                            <div class="avatar">
                                                                <i class="fas fa-location-arrow fa-2x" style=" font-size: 38px;color: rebeccapurple;padding: 12px; padding-left: 15px;"></i>
                                                            </div>
                                                        </div>
                                                        <div class="text">
                                                            <h5  class="name text-overflow" style="margin-top: 14px;"><%= cts.Title%></h5>
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
                                                            Total of location
                                                        </td>
                                                        <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">
                                                    <%= sms_get.get("TotalOfLocation")%>

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
        $($this).removeClass("unread");
        var domElement = $($this).find("h5.name").get(0);
        $(".au-chat-info .nick").append('<a href="#" style="position: inherit;top: 1px;text-overflow: ellipsis; overflow: hidden;display: -webkit-inline-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;word-wrap: break-word;margin-bottom: -3px;">' + domElement.innerHTML + '</a>');
        $(".au-chat__content").append('<iframe id="if" width="100%" height="100%" frameborder="0" style="border:0" allowfullscreen></iframe>');
        $('#if').attr('src', './Device/Location/View?ID=' + $id);
    }
</script>