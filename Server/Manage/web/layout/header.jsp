
<%@page import="com.dn.amonir.model.Request"%>
<%@page import="com.dn.amonir.model.Account"%>

<% Request Request1 = (Request) request.getAttribute("Request"); %>


<!-- HEADER DESKTOP-->
<header class="header-desktop">
    <div class="section__content section__content--p30">
        <div class="container-fluid">
            <div class="header-wrap">
                <form class="form-header" action="" method="POST">
                    <input class="au-input au-input--xl" type="text" name="search" placeholder="" />
                    <button class="au-btn--submit" type="submit">
                        <i class="zmdi zmdi-search"></i>
                    </button>
                </form>
                <div class="header-button">
                    <div class="noti-wrap">

                        <%  if (Request1.Account.Device.Selected != null) {%>    

                        <%@ include file="/layout/header/noti/wrap/device.jsp" %> 

                        <% }%>


                        <div class="noti__item js-item-menu">
                            <i class="fas fa-tablet-alt"></i>
                            <div class="email-dropdown js-dropdown">

                                <script>
                                    function device_select(id) {
                                        var data = {
                                            'ID': id
                                        };

                                        $.ajax({
                                            url: "./Device/Selected",
                                            type: "post",
                                            data: data,
                                            success: function (result) {
                                    <%
                                        if (request.getParameter("drp") == null) {
                                            out.println("location.reload();");
                                        } else {
                                            out.println("location.href = \"./" + request.getParameter("drp") + "\"");
                                        }
                                    %>
                                            }
                                        });
                                    }
                                </script>

                                <%  if (Request1.Account.Device.Selected == null) {%>    

                                <%@ include file="/layout/header/noti/dropdown/device_select.jsp" %> 

                                <%                                } else {
                                %>        

                                <%@ include file="/layout/header/noti/dropdown/device_state.jsp" %> 

                                <%  }%> 

                            </div>
                        </div>
                    </div>
                    <div class="account-wrap">
                        <div class="account-item clearfix js-item-menu">
                            <div class="image">
                                <img src="images/icon/Windows-10-user-icon-big.png" alt="Tên nè" />
                            </div>
                            <div class="content">
                                <a class="js-acc-btn" href="#"></a>
                            </div>
                            <div class="account-dropdown js-dropdown">
                                <div class="info clearfix">

                                    <div class="content" style="padding-left:0px;">
                                        <h5 class="name">
                                            <a href="#"><%= Request1.Account.Name%></a>
                                        </h5>
                                        <span class="email"><%= Request1.Account.Email%></span>
                                    </div>
                                </div>
                                <div class="account-dropdown__body">
                                    <div class="account-dropdown__item">
                                        <a href="./account">
                                            <i class="zmdi zmdi-account"></i>My account</a>
                                    </div>
                                </div>
                                <div class="account-dropdown__footer">
                                    <a href="#" onClick="account_logout()">
                                        <i class="zmdi zmdi-power"></i>Logout</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- HEADER DESKTOP-->