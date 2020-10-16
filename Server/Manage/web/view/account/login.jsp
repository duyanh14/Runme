<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="page-content--bge5">
    <div class="container">
        <div class="login-wrap">
            <div class="login-content">
                <div class="login-logo">
                    <a href="./">
                        <img src="http://amonir.com/img/logo.png" alt="AnmApp" style="height: 85px;">
                    </a>
                </div>
                <div id="login">
                    <form id="login_form" name="login_form" > 
                        <div class="form-group">
                            <label>Email</label>
                            <input class="au-input au-input--full" type="email" id="email" placeholder="">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input class="au-input au-input--full" type="password" id="password" placeholder="">
                        </div>
                        <div class="login-checkbox" style="padding-bottom: 5px;">
                            <label>
                                <!-- <input type="checkbox" name="remember">Nhớ tài khoản -->
                            </label>
                            <label>
                                <a href="#" onClick="showForgotPassword();">Forgot your password?</a>
                            </label>
                        </div>
                        <button type="button" class="au-btn au-btn--block au-btn--green m-b-20" style="margin-bottom: 5px !important;" id="btnLogin">Login</button>

                    </form> 
                    <div class="register-link">
                        <p style="font-size: 16px;">
                            Do not have an account?
                            <a onClick="showRegister();" href="#">register</a>
                        </p>

                    </div>
                </div>
                <div id="register" style="display:none;">
                    <!-- <form action="" > -->
                    <div class="form-group">
                        <label>Full name</label>
                        <input class="au-input au-input--full" type="text" id="name" placeholder="">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input class="au-input au-input--full" type="email" id="email" placeholder="">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input class="au-input au-input--full secure" id="password" placeholder="" style="text-security:disc; -webkit-text-security:disc;"> 
                    </div>
                    <div class="form-group">
                        <label>Retype the password</label>
                        <input class="au-input au-input--full secure" id="repassword" placeholder="" style="text-security:disc; -webkit-text-security:disc;">
                    </div>
                    <div class="form-group">
                        <label>Phone number</label>
                        <input class="au-input au-input--full" type="text" id="phone" placeholder="">
                    </div>
                    <button class="au-btn au-btn--block au-btn--green m-b-20" style="margin-bottom: 5px !important;" id="btnRegister">Register</button>

                    <!-- </form> -->
                    <div class="register-link">
                        <p style="font-size: 16px;">
                            Do you already have an account?
                            <a onClick="showLogin();" href="#">login</a>
                        </p>

                    </div>
                </div>
                <div id="forgot_password" style="display:none;">

                    <div class="form-group">
                        <label>Email</label>
                        <input class="au-input au-input--full" type="email" id="email" placeholder="">
                    </div>

                    <button class="au-btn au-btn--block au-btn--green m-b-20" style="margin-bottom: 5px !important;" id="btnForgotPassword">Send request</button>

                    <!-- </form> -->
                    <div class="register-link">
                        <p style="font-size: 16px;">
                            Do you already have an account?
                            <a onClick="showLogin();" href="#">login</a>
                        </p>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $("#login").on('keyup', function (event) {
        event.preventDefault();
        if (event.which === 13) {
            $("#btnLogin").click();
        }
    });

    $("#register").on('keyup', function (event) {
        event.preventDefault();
        if (event.which === 13) {
            $("#btnRegister").click();
        }
    });

    $("#forgot_password").on('keyup', function (event) {
        event.preventDefault();
        if (event.which === 13) {
            $("#btnForgotPassword").click();
        }
    });

    $("#btnForgotPassword").click(function () {

        $('#btnForgotPassword').prop('disabled', true);

        var data = {
            'Email': $('#forgot_password #email').val(),
        };

        $.ajax({
            url: "./Account/ForgotPassword",
            type: "post",
            data: data,
            success: function (result) {
                var obj = jQuery.parseJSON(result);


                alert(obj.Message);
                if (obj.Status == 0) {
                    showLogin();
                }
                $('#btnForgotPassword').prop('disabled', false);

            }

        });
    });

    $("#btnRegister").click(function () {

        if ($('#register #password').val() != $('#register #repassword').val()) {
            alert("Retype password does not match.");
            return;
        }

        var data = {
            'Name': $('#register #name').val(),
            'Email': $('#register #email').val(),
            'PhoneNumber': $('#register #phone').val(),
            'Password': md5($('#register #password').val())
        };

        $.ajax({
            url: "./Account/Register",
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

    });

    function showRegister() {
        $("#forgot_password").css("display", "none");
        $("#login").css("display", "none");
        $("#register").css("display", "unset");

    }

    function showLogin() {
        $("#forgot_password").css("display", "none");
        $("#register").css("display", "none");
        $("#login").css("display", "unset");
    }

    function showForgotPassword() {
        $("#register").css("display", "none");
        $("#login").css("display", "none");
        $("#forgot_password").css("display", "unset");
    }

    $("#btnLogin").click(function () {
        var data = {
            'Email': $('#login #email').val(),
            'Password': md5($('#login #password').val())
        };

        $.ajax({
            url: "./Account/Login",
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
    });
</script>