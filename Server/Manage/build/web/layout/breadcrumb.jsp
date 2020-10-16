<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.dn.amonir.model.Request"%>

<% Request Request4 = (Request) request.getAttribute("Request");%>

<!-- BREADCRUMB-->
<section class="au-breadcrumb m-t-75">
    <div class="section__content section__content--p30">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="au-breadcrumb-content">
                        <div class="au-breadcrumb-left">
                            <ul class="list-unstyled list-inline au-breadcrumb__list">
                                <li class="list-inline-item">Manage</li>
                             
                                <%

                                    JSONArray breadcrumb = Request4.Site_Manage_Page.getJSONObject("Page").getJSONArray("Breadcrumb");

                                    for (int i = 0; i < breadcrumb.length(); i++) {
                                        JSONObject breadcrumb_o = breadcrumb.getJSONObject(i);

                                       
                                %>
                                <li class="list-inline-item seprate">
                                    <span>/</span>
                                </li>

                                <%
                                   
                                    if (breadcrumb_o.isNull("Path")) {
                                %>
                                <li class="list-inline-item"><%= breadcrumb_o.getString("Name")%></li>

                                <%
                                } else {
                                %>
                                <li class="list-inline-item">
                                    
                                    <%= breadcrumb_o.getString("Name")%>
                                
                                    <% //  (breadcrumb_o.isNull("Path")) ? "#" : breadcrumb_o.getString("Path")%>
                                    <% //  breadcrumb_o.getString("Name") %>
                                      <!--<a href=""></a>-->
                                      

                                </li>
                                <%
                                        }
                                    }
                                %>

                                
                     

                            </ul>
                        </div>
                        <button class="au-btn au-btn-icon au-btn--green" style="display:none;">
                            <i class="zmdi zmdi-plus"></i>add item</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- END BREADCRUMB-->