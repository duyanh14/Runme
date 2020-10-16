<%@page import="com.dn.amonir.model.Request"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.dn.amonir.model.Site"%>
<%@page import="org.json.JSONObject"%>

<% Request Request3 = (Request) request.getAttribute("Request"); %>


<!-- HEADER MOBILE-->
<header class="header-mobile d-block d-lg-none">
    <div class="header-mobile__bar">
        <div class="container-fluid" style="padding-left: 9px;padding-right: 15px;">
            <div class="header-mobile-inner">
                <a class="logo" href="./">
                    <img src="http://amonir.com//img/logo.png" alt="Amonir" style="height: 55px;" />
                </a>
                <button class="hamburger hamburger--slider" type="button">
                    <span class="hamburger-box">
                        <span class="hamburger-inner"></span>
                    </span>
                </button>
            </div>
        </div>
    </div>
    <nav class="navbar-mobile">
        <div class="container-fluid" style="padding: 0px">
            <ul class="navbar-mobile__list list-unstyled">
                 <%
                    try {
                        JSONArray SGMP = Request3.Site_Manage_Page.getJSONObject("Page").getJSONArray("List");

                        for (int i = 0; i < SGMP.length(); i++) {
                            JSONObject SGMPO = SGMP.getJSONObject(i);

                %>
                <li class="<%= (SGMPO.getBoolean("Active")) ? "active": ""  %> has-sub">
                    <a class="js-arrow <%= (!SGMPO.isNull("Child")) ? "nohover" : "" %>" style="padding-left: 20px !important;" href="<%= (SGMPO.getBoolean("Home")) ? "./" : (SGMPO.isNull("Path")) ? "#" : SGMPO.getString("Path")%>">
                         <div style="text-align:center;display: inline-block;margin-right: 10px;width: 30px !important;">
                        <i class="<%= SGMPO.getString("Icon")%>"></i></div><%= SGMPO.getString("Name")%>
                        <%
                            if (!SGMPO.isNull("Child")) {
                        %>
                        <span class="arrow up">
                            <i class="fas fa-angle-down" style="    padding-left: 6px;"></i>
                        </span>
                        <%
                            }
                        %>
                    </a>
                    <ul class="list-unstyled navbar__sub-list js-sub-list" style="display: block;background-color: white;">
                        <%
                            if (!SGMPO.isNull("Child")) {
                                JSONArray SGMPOC = SGMPO.getJSONArray("Child");

                                for (int ii = 0; ii < SGMPOC.length(); ii++) {
                                    JSONObject SGMPOCO = SGMPOC.getJSONObject(ii);
                        %>
                        <li class="<%= (SGMPOCO.getBoolean("Active")) ? "active": ""  %>">
                            <a  href="<%=  (SGMPOCO.isNull("Path")) ? "#" : SGMPOCO.getString("Path")%>">
                                 <div style="text-align:center;display: inline-block;margin-right: 10px;width: 30px !important;">
                                <i class="<%= SGMPOCO.getString("Icon")%>"></i></div><%= SGMPOCO.getString("Name")%></a>
                        </li>
                        <%

                                }
                            }
                        %>
                    </ul>
                </li>
                <%
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                %>   
            </ul>
        </div>
    </nav>
</header>
<!-- END HEADER MOBILE-->

<!-- MENU SIDEBAR-->
<aside class="menu-sidebar d-none d-lg-block">
    <div class="logo">
        <a href="./" style="margin: 0 auto;">
            <img src="http://amonir.com//img/logo.png" alt="Amonir" style=" height: 55px; ">
        </a>
    </div>
    <div class="menu-sidebar__content js-scrollbar1 ps" style="box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.1);border-right: 1px solid #e5e5e5;">
        <nav class="navbar-sidebar2">
            <ul class="list-unstyled navbar__list">

                <%
                    try {
                        JSONArray SGMP = Request3.Site_Manage_Page.getJSONObject("Page").getJSONArray("List");

                        for (int i = 0; i < SGMP.length(); i++) {
                            JSONObject SGMPO = SGMP.getJSONObject(i);

                %>
                <li class="<%= (SGMPO.getBoolean("Active")) ? "active": ""  %> has-sub open">
                    <a class="js-arrow open "  href="<%= (SGMPO.getBoolean("Home")) ? "./" : (SGMPO.isNull("Path")) ? "#" : SGMPO.getString("Path")%>">
                       <div style="text-align:center;display: inline-block;margin-right: 10px;width: 30px !important;">
                           <i class="<%= SGMPO.getString("Icon")%>"></i></div><%= SGMPO.getString("Name")%>
                        <%
                            if (!SGMPO.isNull("Child")) {
                        %>
                        <span class="arrow up">
                            <i class="fas fa-angle-down"></i>
                        </span>
                        <%
                            }
                        %>
                    </a>
                    <ul class="list-unstyled navbar__sub-list js-sub-list" style="display: block;">
                        <%
                            if (!SGMPO.isNull("Child")) {
                                JSONArray SGMPOC = SGMPO.getJSONArray("Child");

                                for (int ii = 0; ii < SGMPOC.length(); ii++) {
                                    JSONObject SGMPOCO = SGMPOC.getJSONObject(ii);
                        %>
                        <li class="<%= (SGMPOCO.getBoolean("Active")) ? "active": ""  %>">
                            <a  href="<%=  (SGMPOCO.isNull("Path")) ? "#" : SGMPOCO.getString("Path")%>">
                                 <div style="text-align:center;display: inline-block;margin-right: 10px;width: 30px !important;"><i class="<%= SGMPOCO.getString("Icon")%>"></i></div><%= SGMPOCO.getString("Name")%></a>
                        </li>
                        <%

                                }
                            }
                        %>
                    </ul>
                </li>
                <%
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                %>   


            </ul>
        </nav>
    </div>
</aside>
<!-- END MENU SIDEBAR-->
