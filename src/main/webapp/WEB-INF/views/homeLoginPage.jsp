<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}" scope="application" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Login | e Doc </title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${baseUrl}/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${baseUrl}/resources/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${baseUrl}/resources/bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseUrl}/resources/dist/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${baseUrl}/resources/plugins/iCheck/square/blue.css">
    <!-- core css -->
    <link href="${baseUrl}/resources/dist/css/style.css" rel="stylesheet">

    <link rel="shortcut icon" href="${baseUrl}/resources/favicon.png" type="image/x-icon">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

    <style>
        .login-page{background-color: #2874f0; font-size: 16px; display: block;}
        .login-page h2{margin: 0 0 25px; font-weight: bold;}
        #particles-js{max-height: 100%; overflow: hidden;}
        .login-page h4{padding: 10px 0; letter-spacing: .7px}
        .login-box{width: 800px; max-width: 100%; background: #fff; margin:0;  box-shadow: 0 0 5px rgba(0,0,0,.2); position: absolute; top: calc(50% - 244px); left: calc(50% - 400px); z-index: 5}
        .login-box>.row{margin:0;}
        .login-box>.row>div[class^='col-']{padding: 0}
        .login-page .form-group{height: 70px}
        .login-page .form-control{border-radius:0 !important;padding:20px 12px; border: none; border-bottom: 2px solid #d2d6de; padding: 20px 0; position: absolute; z-index: 5; bottom: 0; background: none; font-size: 18px;}
        .login-left{border-right: 1px solid #ccc}
        .login-content{padding: 25px}
        .login-page form{margin-top: 16%}
        .login-page .f-label{font-size: 14px; margin-top:5px}
        .login-page label.active{color: #2874f0; font-weight: normal; font-size: 15px; top: 5px}
        .login-page label{font-size: 18px; font-weight: normal; color: #999; margin-bottom: -5px; position: absolute; top: 35px; transition: all linear .1s}
        .btn-login{padding: 16px; font-weight: 600; font-size:18px; box-shadow: 0 3px 1px rgba(0,0,0,.15); letter-spacing: 1px}
        .edn-logo{margin: 0 auto}
        input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
        }
        .login-page select.form-control{padding: 0}
        .login-page .accountType{padding-top: 7px}

        @media(max-width: 767px){
            #particles-js{display: none}
            .login-page{background: #fff;}
            .login-box{margin: 0; box-shadow: none; left: 0}
            .login-left{display: none}
        }
    </style>
</head>
<body class="hold-transition login-page">
<div id="particles-js"></div>
<div class="login-box">
    <div class="row">
        <div class="row">
            <a href="${baseUrl}/doctor/login" class="btn-reddit btn btn-block btn-login">DOCTOR LOG IN</a>
        </div>
        <div class="row">
            <a href="${baseUrl}/patient/login" class="btn-reddit btn btn-block btn-login">PATIENT LOG IN</a>
        </div>
        <div class="row">
            <a href="${baseUrl}/admin/login" class="btn-reddit btn btn-block btn-login">ADMIN LOG IN</a>
        </div>
    </div>
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="${baseUrl}/resources/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${baseUrl}/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${baseUrl}/resources/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="${baseUrl}/resources/dist/js/particles.min.js"></script>
<script>
    $(function () {
        $(".btn-login").click(function(){
            $(this).html("<i class='fa fa-spinner fa-spin'></i> Logging In ...");
            $(this).prop("disabled",true);
            $("#login-form").submit();
        });

        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });

        $('.login-page .form-control').focus(function(){
            $(this).parent().find('label').addClass('active');
        });

        $('.login-page .form-control').blur(function(){
            if (!$(this).val()) {
                $(this).parent().find('label').removeClass('active');
            }
        });

        particlesJS('particles-js',
            {
                "particles": {
                    "number": {
                        "value": 80,
                        "density": {
                            "enable": true,
                            "value_area": 800
                        }
                    },
                    "color": {
                        "value": "#ffffff"
                    },
                    "opacity": {
                        "value": 0.5,
                        "random": false,
                        "anim": {
                            "enable": false,
                            "speed": 1,
                            "opacity_min": 0.1,
                            "sync": false
                        }
                    },
                    "size": {
                        "value": 3,
                        "random": true,
                        "anim": {
                            "enable": false,
                            "speed": 10,
                            "size_min": 0.1,
                            "sync": false
                        }
                    },
                    "line_linked": {
                        "enable": true,
                        "distance": 150,
                        "color": "#ffffff",
                        "opacity": 0.4,
                        "width": 1
                    },
                    "move": {
                        "enable": true,
                        "speed": 2,
                        "direction": "none",
                        "random": false,
                        "straight": false,
                        "out_mode": "out",
                        "attract": {
                            "enable": false,
                            "rotateX": 600,
                            "rotateY": 1200
                        }
                    }
                }
            }
        );
    });
</script>
</body>
</html>
