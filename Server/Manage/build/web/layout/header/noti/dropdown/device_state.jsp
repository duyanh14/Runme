<%@page import="com.dn.amonir.model.Time"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.dn.amonir.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Request Request2 = (Request) request.getAttribute("Request");%>

<style>
    tr.spaceUnder td {
        padding-bottom: 1.5em;
    }
</style>

<div class="notifi__title"> 

    <div >
        <div style="float: left">            <p><%=  Request2.Account.Device.Selected.Name%></p></div>
        <div style="float: right"><i class="fas fa-circle" style="font-size: 15px;padding-left: 5px;<%= ((Time.Unix.Now() - Request2.Account.Device.Selected.Time.Server.Active) < 600) ? "color: #28a745;" : ""%>"></i></div>
        <br>
    </div>

</div>
<div class="notifi__item">


    <!-- signal-slash -->
    <button onClick="<%= (Request2.Account.Device.Selected.AirplaneMode) ? "" : "state_viewtab('sim');"%>" type="button" class="btn btn-success" style="border-radius: 100%;    margin-right: 15px;height: 55px;width: 55px;">
        <i class="<%= (Request2.Account.Device.Selected.AirplaneMode) ? "fas fa-plane" : "fas fa-signal"%>" style="color: #fff;font-size: 1.5em;margin: 0 auto;display: inline;"></i></button>

    <!-- wifi-slash -->

    <button onClick="<%=  (Request2.Account.Device.Selected.Wifi.Connected && (Request2.Account.Device.Selected.Wifi.Name != null)) ? "state_viewtab('wifi');" : ""%>" type="button" class="btn btn-success" style="border-radius: 100%;    margin-right: 15px;height: 55px;    width: 55px;">
        <i class="fas fa-<%=  (Request2.Account.Device.Selected.Wifi.Connected && (Request2.Account.Device.Selected.Wifi.Name != null)) ? "wifi" : "wifi-slash"%>" style="color: #fff;font-size: 1.5em;margin: 0 auto;display: inline;"></i>

        <!-- location-slash -->
        <button onClick="//state_viewtab('location');" type="button" class="btn btn-success" style="border-radius: 100%;    margin-right: 15px;height: 55px;width: 55px;">
            <i class="fas fa-<%=  (Request2.Account.Device.Selected.GPS.Enable) ? "location" : "location-slash"%>" style="color: #fff;font-size: 1.5em;margin: 0 auto;display: inline;"></i></button>

        <!-- battery-bolt -->
        <button onClick="state_viewtab('battery');" type="button" class="btn btn-success" style="border-radius: 100%;    height: 55px;width: 55px;">
            <i class="fas fa-<%=  (Request2.Account.Device.Selected.Battery.Charging) ? "battery-bolt" : "battery-three-quarters"%>" style="color: #fff;font-size: 1.5em;margin: 0 auto;display: inline;"></i></button>


</div>


<div id="sim" class="state_tabcontent" style="display:none">

    <%
        if (!Request2.Account.Device.Selected.AirplaneMode) {
            JSONArray sim_list = Request2.Account.Device.Selected.SIM;

            for (int i = 0; i < sim_list.length(); i++) {
                JSONObject sim = sim_list.getJSONObject(i);

    %>
    <div class="notifi__item"><table >
            <tr class="spaceUnder">
                <td align="center" valign="center" style="padding-right: 16px;width: 60px;"><i class="fas fa-sim-card" ></i></i></td>
                <td><p><%= sim.getJSONObject("Carrier").getString("Name")%></p></td> 
            </tr>
            <tr class="spaceUnder" >
                <td align="center" valign="center" style="padding-right: 16px;width: 60px;"><i class="fas fa-terminal" ></i></i></td>

                <td><p><%= sim.getString("Serial")%></p></td> 
            </tr>
            <%
                try {
                    if (!sim.isNull("Number")) {
                        if (!sim.getString("Number").isEmpty()) {
            %>         
            <tr class="spaceUnder" >
                <td align="center" valign="center" style="padding-right: 16px;width: 60px;"><i class="fas fa-keyboard" style="margin: 0px 3%;text-align: center;"></i></i></td>

                <td><p><%= sim.getString("Number")%></p></td> 
            </tr>
            <%
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            %>

            <tr >
                <td align="center" valign="center" style="padding-right: 16px;width: 60px;"><i class="fas fa-globe-asia" style="margin: 0px 8%;text-align: center;"></i></td>
                <td><p><%= sim.getString("Country")%></p></td> 
            </tr>
        </table></div>
        <% }
            }%>

    <!--1-->
</div>


<div id="wifi" class="state_tabcontent" style="display:none">
    <!--2-->

    <div class="notifi__item"><table >
            <tr >
                <td align="center" valign="center" style="padding-right: 16px;width: 60px;"><i class="fas fa-plug" ></i></td>
                <td><p><%=  (Request2.Account.Device.Selected.Wifi.Connected) ? Request2.Account.Device.Selected.Wifi.Name : "Not connected"%></p></td> 
            </tr>


        </table></div>

</div>

<div id="location" class="state_tabcontent" style="display:none">
    <div class="notifi__item">
        <h3>Contact</h3>
        <p>Get in touch, or swing by for a cup of coffee.</p>
    </div>
</div>

<div id="battery" class="state_tabcontent" style="display:none">
    <div class="notifi__item">                                        
        <div class="card-body" style="padding: 0px 0px 0px 0px !important;">
            <style>
                @media (max-width: 767px){
       
        #tr76590trhgutd890gure89gr {
            height: 27px !important; 
        }
    }
                
            </style>
            <div class="progress mb-2" style="top:0px;height: 30px;margin-bottom: 0px !important;" id="tr76590trhgutd890gure89gr">
                <!--3-->
                <div class="progress-bar bg-info" role="progressbar" style="width: <%=  Request2.Account.Device.Selected.Battery.Percent%>%;"  aria-valuenow="<%=  Request2.Account.Device.Selected.Battery.Percent%>" aria-valuemin="0" aria-valuemax="100"><%=  Request2.Account.Device.Selected.Battery.Percent%>%</div>
            </div>

        </div>   </div>
</div>

<div class="email__footer" >
    <a href="./device" style="text-transform: unset;">See all device</a>
</div>

<script>
    function state_viewtab(pageName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("state_tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        document.getElementById(pageName).style.display = "block";
    }

</script>