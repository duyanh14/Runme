<%@page import="com.dn.amonir.model.Time"%>
<%@page import="com.dn.amonir.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Request Request = (Request) request.getAttribute("Request"); %>

<div class="row">
    <div class="col-lg-12">
        <div class="card">
            <div class="card" style="margin-bottom: unset;">
                <div class="card-body">
                    <div class="row">

                        <div class="col-sm-7" style="padding-right: 1px;" id="gfhgfoi53hou4ehrdgiufdg ">
                            <section class="card" style="border: 0px;margin-bottom:0px;" id="ghj54oi6htouigfdghfd">
                                <div class="custom-tab">
                                    <nav>
                                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                            <a class="nav-item nav-link active show" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home" aria-selected="false">
                                                <i class="fas fa-mobile" style="margin-right: 10px;">
                                                </i>Device available
                                            </a>
                                            <!-- <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile" aria-selected="false"><i class="fas fa-archive" style="margin-right: 10px;"></i>Lưu trữ</a> -->
                                        </div>
                                    </nav>

                                    <div class="tab-content pl-3 pt-2" id="nav-tabContent" style="padding-left: 0px !important;border: 1px solid #dee2e6;border-top: unset;border-bottom-left-radius: .25rem;border-bottom-right-radius: .25rem;">
                                        <div class="tab-pane fade active show" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
                                            <div class="table-responsive ">
                                                <table id="list_device" class="table table-hover" style="margin-top: -7px !important;">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col" style="min-width: 60px;text-align: center;">#</th>
                                                            <th scope="col">Name</th>
                                                            <th scope="col" >Status</th> 
                                                            <th scope="col" style="min-width: 156px;">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                        <%
                                                            for (int i = 0; i < Request.Account.Device.List.size(); i++) {

                                                                com.dn.amonir.dto.Device device = Request.Account.Device.List.get(i);

                                                        %>

                                                        <tr id="list_device_<%= device.ID%>" style="cursor: pointer;"  <%=  (Request.Account.Device.Selected != null) && (Request.Account.Device.Selected.ID == device.ID) ? "class=\"table-active\"" : ""%>  >
                                                            <th scope="row" style="vertical-align: inherit; text-align: center;"><%= (i + 1)%></th>
                                                            <th ><button onClick="device_select(<%= device.ID%>)" type="button" class="btn btn-outline-secondary ">
                                                                    <i class="fas fa-mobile"></i>&nbsp; <div style="display: contents" id="name"><%= device.Name%></div></button></th>

                                                            <th style="vertical-align: inherit;"><i class="fas fa-circle" style="<%= ((Time.Unix.Now() - device.Time.Server.Active) < 600) ? "color: #28a745;" : ""%>"></i></th>


                                                            <th>
                                                                <!--<button onClick="device_edit(1);" type="button" class="btn btn-outline-success btn-sm" style="width: 60px"> <i class="fas fa-edit"></i></button>-->
                                                                <!--&nbsp;-->
                                                                <button onClick="device_archive(<%= device.ID%>, '<%= device.Name%>');" type="button" class="btn btn-outline-danger btn-sm" style="width: 60px"> <i class="fa fa-archive"></i></button>
                                                            </th>

                                                        </tr>


                                                        <%}%>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="tab-pane fade" id="custom-nav-profile" role="tabpanel" aria-labelledby="custom-nav-profile-tab">
                                            2
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                        <div class="col-sm-5">
                            <section class="card" style="border: 0px; margin-bottom: unset;">

                                <%
                                    if (Request.Account.Device.Selected == null) {
                                %>
                                <div class="custom-tab">
                                    <nav>
                                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                            <a class="nav-item nav-link active show" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home" aria-selected="false">
                                                <i class="fas fa-info" style="margin-right: 10px;">
                                                </i>Notice
                                            </a>
                                            <!-- <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile" aria-selected="false"><i class="fas fa-archive" style="margin-right: 10px;"></i>Lưu trữ</a> -->
                                        </div>
                                    </nav>
                                    <div class="tab-content pl-3 pt-2" id="nav-tabContent" style="padding-left: 0px !important;padding-left: 0px !important;border: 1px solid #dee2e6;border-top: unset;">
                                        <div class="tab-pane fade active show" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
                                            <div style="padding: 10px;padding-top: 1px;">

                                                <div class="alert alert-success" role="alert">
                                                    <i class="fa fa-archive"></i>&nbsp&nbspStore when the device is no longer in use! 
                                                </div>

                                                <div class="alert alert-warning" role="alert" style="margin: 0;">
                                                    <i class="fa fa-archive"></i>&nbsp&nbspWhen stored, the device can only be displayed again when configured again on the application. 
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% } else {%>
                                <div class="custom-tab">
                                    <nav>
                                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                            <a class="nav-item nav-link active show" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home1" role="tab" aria-controls="custom-nav-home1" aria-selected="false">
                                                <i class="fas fa-info" style="margin-right: 10px;">
                                                </i>Information
                                            </a>
                                            <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile1" role="tab" aria-controls="custom-nav-profile1" aria-selected="false">
                                                <i class="fas fa-sliders-h" style="margin-right: 10px;">
                                                </i>Configuration
                                            </a>
                                        </div>
                                    </nav>
                                    <div class="tab-content pl-3 pt-2" id="nav-tabContent" style="padding-left: 0px !important;    border: 1px solid #dee2e6;    border-top: unset; ">
                                        <div class="tab-pane fade active show" id="custom-nav-home1" role="tabpanel" aria-labelledby="custom-nav-home-tab1">
                                            <table class="table table-hover table-striped table-align-middle mb-0" style="margin-top:-8px;">
                                                <tbody>

                                                    <tr>
                                                        <td style="border-top: unset;    width: 40%;">
                                                            Name
                                                        </td>
                                                        <td style="border-top: unset;">
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td style="border-top: unset;">

                                                    <div style="float: left"><p id="device_information_name"><%= Request.Account.Device.Selected.Name%></p></div>
                                                    <div style="float: right"><button onclick="device_name(<%= Request.Account.Device.Selected.ID%>);" type="button" class="btn btn-outline-danger btn-sm" style="width: 37px;height: 27px;padding: unset !important;"> <i class="fa fa-edit" style="font-size: 16px;"></i></button></div>

                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="    width: 40%;">
                                                        Manufacturer
                                                    </td>
                                                    <td >
                                                <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td>
                                                    <%= Request.Account.Device.Selected.Manufacturer%>
                                                </td>
                                                </tr>

                                                <tr>
                                                    <td style="    width: 40%;">
                                                        Model
                                                    </td>
                                                    <td>
                                                <basix-switch type="3d" variant="primary" size="xs" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td>
                                                    <%= Request.Account.Device.Selected.Model%>

                                                </td>
                                                </tr>
                                                <tr>
                                                    <td style="    width: 40%;">
                                                        SDK
                                                    </td>
                                                    <td>
                                                <basix-switch type="3d" variant="primary" size="sm" :checked="true">
                                                </basix-switch>
                                                </td>
                                                <td>
                                                    <%= Request.Account.Device.Selected.SDK%>
                                                </td>
                                                </tr>

                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="tab-pane fade" id="custom-nav-profile1" role="tabpanel" aria-labelledby="custom-nav-profile-tab1">
                                            <div style="padding: 10px;padding-top: 1px;">

                                                <div class="alert alert-info" role="alert">
                                                    The configuration will be updated immediately if the device is online.
                                                </div>
                                                <table class="table table-hover table-striped table-align-middle mb-0" style="margin-top:-9px;">
                                                    <tbody>
                                                        <tr>
                                                            <td style="border-top: unset;">
                                                                Send file over mobile network
                                                            </td>
                                                            <td style="border-top: unset;">
                                                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                    </basix-switch>
                                                    </td>
                                                    <td style="border-top: unset;">


                                                        <label class="switch switch-3d switch-secondary mr-3">
                                                            <input type="checkbox" class="switch-input" checked="true">
                                                            <span class="switch-label"></span>
                                                            <span class="switch-handle"></span>
                                                        </label>

                                                    </td>
                                                    </tr>

                                                    <tr>
                                                        <td style="border-top: unset;">
                                                            Period of time you want to receive the location ( second)
                                                        </td>
                                                        <td style="border-top: unset;">
                                                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                    </basix-switch>
                                                    </td>
                                                    <td style="border-top: unset;">
                                                        <input style="max-width:80px" type="text" autocomplete="off" id="repassword" name="input1-group1" class="form-control" value="">
                                                    </td>
                                                    </tr>

                                                    <tr>
                                                        <td style="border-top: unset;">
                                                            Period of time you want to receive the location of a location is available sooner you can get it ( second)
                                                        </td>
                                                        <td style="border-top: unset;">
                                                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                    </basix-switch>
                                                    </td>
                                                    <td style="border-top: unset;">
                                                        <input style="max-width:80px" type="text" autocomplete="off" id="repassword" name="input1-group1" class="form-control" value="">
                                                    </td>
                                                    </tr>


                                                    <tr>
                                                        <td style="border-top: unset;">
                                                            Distance updating location ( meter)
                                                        </td>
                                                        <td style="border-top: unset;">
                                                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                    </basix-switch>
                                                    </td>
                                                    <td style="border-top: unset;">
                                                        <input style="max-width:80px" type="text" autocomplete="off" id="repassword" name="input1-group1" class="form-control" value="">
                                                    </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% }%>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


<script>

    function device_archive(id, name) {
        conf = confirm('Are you sure you want to put "' + name + '" device on the archive list?');

        if (!conf) {
            return;
        }
        var data = {
            'ID': id,
        };

        $.ajax({
            url: "./Device/Archive",
            type: "post",
            data: data,
            success: function (result) {
                var obj = jQuery.parseJSON(result);

                switch (obj.Status) {
                    case 0:
                        location.reload();
                        break;
                    default:
                        alert(obj.Message);
                        break;
                }

            }
        });


    }



    function device_name(id) {
        var person = prompt("Please enter your new name for this device", $('#device_information_name').text());

        if (person == null) {
            return;
        }

        var data = {
            'ID': id,
            'Value': person
        };

        $.ajax({
            url: "./Device/Name",
            type: "post",
            data: data,
            success: function (result) {
                var obj = jQuery.parseJSON(result);

                switch (obj.Status) {
                    case 0:
                        $('#device_information_name').text(person);
                        $('#list_device tbody #list_device_' + id + ' #name').text(person);

                        break;
                    default:
                        alert(obj.Message);
                        break;
                }

            }
        });

    }


</script>
