<%@page import="com.dn.amonir.model.Request"%>
<%@page import="com.dn.amonir.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Request Request = (Request) request.getAttribute("Request"); %>
<%  String Page = (String) request.getAttribute("Page"); %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags-->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="AnmApp">

        <!-- Title Page-->
        <title>Manage - Amonir</title>

        <!-- Fontfaces CSS-->
        <link href="css/font-face.css" rel="stylesheet" media="all">
        <!--<link href="fontawesome/css/all.css" rel="stylesheet" media="all">-->

        <!--<link href="vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">-->
        <!--<link href="vendor/font-awesome-5/css/fontawesome-all.min.css" rel="stylesheet" media="all">--> 

        <link href="fontawesome/css/all.css" rel="stylesheet" media="all">

        <link href="vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">

        <!-- Bootstrap CSS-->
        <link href="vendor/bootstrap-4.1/bootstrap.min.css" rel="stylesheet" media="all">

        <!-- Vendor CSS-->
        <link href="vendor/animsition/animsition.min.css" rel="stylesheet" media="all">
        <link href="vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet" media="all">
        <link href="vendor/wow/animate.css" rel="stylesheet" media="all">
        <link href="vendor/css-hamburgers/hamburgers.min.css" rel="stylesheet" media="all">
        <link href="vendor/slick/slick.css" rel="stylesheet" media="all">
        <link href="vendor/select2/select2.min.css" rel="stylesheet" media="all">
        <link href="vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">

        <!-- Main CSS-->
        <link href="css/theme.css" rel="stylesheet" media="all">

        <!-- Jquery JS-->
        <script src="vendor/jquery-3.2.1.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/blueimp-md5/2.5.0/js/md5.min.js"></script>
        <link rel="shortcut icon" type="image/x-icon" href="http://amonir.com/img/fav.ico" />

        <script src="js/js.js"></script>

        <!-- <script src='https://www.google.com/recaptcha/api.js'></script> -->

        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
        <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>


    </head>

    <body class="animsition" >
        <div class="page-wrapper">

            <!--Kiểm tra đăng nhập-->

            <%

                if (Request.Account == null) {

                    switch (Page) {
                        case "forgot-password":
            %> 
            <%@     include file="/view/account/forgot-password.jsp" %> 

            <%
                    break;
                default:
            %>     

            <%@     include file="/view/account/login.jsp" %> 

            <%
                }

            %>    



            <%            } else {
            %>        

            <!--Kiểm tra trạng thái hoạt động-->
            <%  if (Request.Account.Disable) {%>    

            <%@     include file="/view/account/disable.jsp" %> 

            <%
            } else {
            %>        

            <%@     include file="/layout/sidebar.jsp" %>
            <%@     include file="/layout/container.jsp" %>

            <% }%> 
            <!--Kiểm tra trạng thái hoạt động-->

            <% }%> 
            <!--Kiểm tra đăng nhập-->

        </div>

        <!-- Bootstrap JS-->
        <script src="vendor/bootstrap-4.1/popper.min.js"></script>
        <script src="vendor/bootstrap-4.1/bootstrap.min.js"></script>
        <!-- Vendor JS       -->
        <script src="vendor/slick/slick.min.js">
        </script>
        <script src="vendor/wow/wow.min.js"></script>
        <script src="vendor/animsition/animsition.min.js"></script>
        <script src="vendor/bootstrap-progressbar/bootstrap-progressbar.min.js">
        </script>
        <script src="vendor/counter-up/jquery.waypoints.min.js"></script>
        <script src="vendor/counter-up/jquery.counterup.min.js">
        </script>
        <script src="vendor/circle-progress/circle-progress.min.js"></script>
        <script src="vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
        <script src="vendor/chartjs/Chart.bundle.min.js"></script>
        <script src="vendor/select2/select2.min.js">
        </script>

        <!-- Main JS-->
        <script src="js/main.js"></script>


        <script>
            function account_logout() {
                window.location.href = "./Account/Logout";
            }
        </script>

    </body>

</html>
<!-- end document-->