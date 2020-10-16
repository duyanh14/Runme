<%@page import="com.dn.amonir.model.Time"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.dn.amonir.model.Request"%>

<% Request Request4 = (Request) request.getAttribute("Request"); %>

<div class="notifi__title"> 
    <p>Select device</p>
</div>

<%
    ArrayList<com.dn.amonir.dto.Device> devices = Request4.Account.Device.List;

    for (int i = 0; i < ((devices.size() > 4) ? 4 : devices.size()); i++) {
%>
<div class="email__item"  onClick="device_select(<%= devices.get(i).ID%>)">
    <div class="image img-cir img-40">
        <i class="fas fa-mobile" style="padding-left: 12px;padding-top: 5px;"></i>

    </div>
    <div class="content">

        <div style="float: left"><p style=" margin: auto;padding: 10px;"><%= devices.get(i).Name%></p></div>
        <div style="float: right"><i class="fas fa-circle" style="padding-top: 10px;font-size: 15px;padding-left: 5px;<%= ((Time.Unix.Now() - devices.get(i).Time.Server.Active) < 600) ? "color: #28a745;" : ""%>"></i></div>

    </div>
</div>
<%
    }
%>



<div class="email__footer">
    <a href="./device" style="text-transform: unset;">See all device</a>
</div>


