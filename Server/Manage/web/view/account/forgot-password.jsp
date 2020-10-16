<%@page import="java.util.ArrayList"%>
<%@page import="com.dn.amonir.model.API"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    try {

        ArrayList<String> actions = new ArrayList<>();
        actions.add("ForgotPassword");
        actions.add("State");

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("Token", request.getParameter("token"));

        JSONObject rs = new JSONObject(API.Request.Make("Account", actions, parameters));

        if (rs.getInt("Status") != 0) {
            response.getWriter().append("<script>alert('" + rs.getString("Message") + "');location.href = \"./\";</script>");
            response.getWriter().close();

            return;
        }

    } catch (Exception ex) {

        ex.printStackTrace();
    }


%>


<div class="page-content--bge5">
    <div class="container">
        <div class="login-wrap">
            <div class="login-content">
                <div class="login-logo">
                    <a href="./">
                        <img src="http://amonir.com/img/logo.png" alt="AnmApp" style="height: 85px;">
                    </a>
                </div>

                <div id="forgot_password" >

                    <div class="form-group">
                        <label>New password</label>
                        <input class="au-input au-input--full" type="text" id="password" placeholder="" style="text-security:disc; -webkit-text-security:disc;">
                    </div>
                    <div class="form-group">
                        <label>Retype new password</label>
                        <input class="au-input au-input--full" type="text" id="retypepassword" placeholder="" style="text-security:disc; -webkit-text-security:disc;">
                    </div>

                    <button class="au-btn au-btn--block au-btn--green m-b-20" style="margin-bottom: 5px !important;" id="btnForgotPassword">Confirm</button>

                    <!-- </form> -->

                </div>
            </div>
        </div>
    </div>
</div>

<script>


    $("#btnForgotPassword").click(function () {

        if ($('#forgot_password #password').val() != $('#forgot_password #retypepassword').val()) {
            alert("Retype password does not match.")
            return;
        }

        $('#btnForgotPassword').prop('disabled', true);

        var data = {
            'Password': $('#forgot_password #password').val(),
            'Token': "<%= request.getParameter("token")%>"
        };

        $.ajax({
            url: "./Account/ForgotPassword/Confirm",
            type: "post",
            data: data,
            success: function (result) {
                var obj = jQuery.parseJSON(result);

                if (obj.Status == 0) {
                    location.href = "./";
                    return;
                }

                alert(obj.Message);

                $('#btnForgotPassword').prop('disabled', false);
            }

        });
    });


</script>