
<%@page import="com.dn.amonir.model.Request"%>

<% Request Request5 = (Request) request.getAttribute("Request");
%>

<!-- PAGE CONTAINER-->
<div class="page-container">

    <%@ include file="/layout/header.jsp" %> 

    <%@ include file="/layout/breadcrumb.jsp" %> 

    <div class="main-content">

        <%            if (!Request5.Site_Manage_Page.getJSONObject("Page").isNull("Active")) {
        %>
        <div class="row">
            <div class="col-md-12">
                <div class="overview-wrap">
                    <h2 class="title-1" style="padding-bottom: 30px;"><%= Request5.Site_Manage_Page.getJSONObject("Page").getJSONObject("Active").getString("Name")%></h2>
                </div>
            </div>
        </div>

        <%}
        %>

        <%            if (Request.Page_Device_Require.contains(request.getAttribute("Page"))) {

                if (Request5.Account.Device.Selected == null) {
        %>
        <jsp:include page="/page/device_require.jsp"></jsp:include>

        <%
        } else {
        %>
        <jsp:include page="/page/${Page}.jsp"></jsp:include>
        <%
            }

        } else {
        %>
        <jsp:include page="/page/${Page}.jsp"></jsp:include>

        <%
            }

        %>

    </div>
    <%@ include file="/layout/footer.jsp" %> 

</div>
<!-- END PAGE CONTAINER-->