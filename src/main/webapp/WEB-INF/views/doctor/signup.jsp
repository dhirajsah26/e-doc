<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<c:set var="baseUrl" value="${pageContext.request.contextPath}" scope="application" />

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Doctor Sign Up | E doc</title>
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
<!-- select2 -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />
    <!-- core css -->
<link href="${baseUrl}/resources/dist/css/style.css" rel="stylesheet">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition register-page college_form_single">
<div class="register-box">
<h3 class="text-center">Sign up</h3>

  <div class="register-box-body">
    <p class="login-box-msg">Create New Doctor Account</p>

    <form action="${baseUrl}/doctor/signup" method="post">
    	${message}
      <div class="form-group has-feedback">
        <input type="text" name="name" id="name" class="form-control" placeholder="Full name" autofocus="">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <span class="error">${errname}</span>
      </div>
      <div class="form-group has-feedback">
        <input type="text" name="username" class="form-control" placeholder="Enter unique username" id="username" autocomplete="false">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <span class="error">${erruname}</span>
        <span id="disp_uname_msg"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="email" name="email" id="email" class="form-control" placeholder="Email">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        <span class="error">${erremail}</span>
        <span id="disp_em_msg"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" id="pwd" class="form-control" placeholder="Password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        <span class="error" id="err_pwd">${errpwd}</span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="repassword" id="re_pwd" class="form-control" placeholder="Retype password">
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
        <span class="error">${err_repwd}</span>
        <span id="disp_pwd_msg"></span>
      </div>
          <div class="form-group">
            <select class="form-control" id="select_role" name="specialization_id">
              <option></option>
              <c:forEach var="specialization" items="${specializationList}">
                <option value="${specialization.id}">${specialization.specializationName}</option>
              </c:forEach>
            </select>
            <span class="error">${err_role}</span>
          </div>

      <div class="row">
        <div class="col-xs-8">
        	<a href="login" class="text-center">Already have an account</a>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Sign Up</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 3 -->
<script src="${baseUrl}/resources/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${baseUrl}/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${baseUrl}/resources/plugins/iCheck/icheck.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>

<script>
  $(function () {
	  
	 $("#select_role").select2({
		placeholder : "Select Role" 
	 });
	  
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
  $(document).ready(function(){
	  
	  $(".error").fadeOut(15000);
	  
	  //validating name
	  $("#name").change(function(){
		  var name = $.trim($(this).val());
		  $(this).val(name);
	  });
	  //validating username
	 $('#username').change(function(){
		 var username = $(this).val().replace(/\s/g,"");
		 if(username.length >= 5){
			 $(this).val(username);
			 var path = "${baseUrl}/admin/checkUsernameAvailability";
			 $.ajax({
				 url : path,
				 method : "post",
				 data : {'username':username},
				 dataType : "text",
				 success:function(resp){
					 if(resp == "available"){
						 $("#disp_uname_msg").empty().append("<i class='fa fa-check'></i> available").css({'color':'green'});
					 }else{
						 $("#username").val("");
						 $("#disp_uname_msg").empty().append("<i class='fa fa-times'></i> username not available").css({'color':'#f00'});
					 }
				 } 
			 });
		 }else{
			 $("#username").val("");
			 $("#disp_uname_msg").empty().append("<i class='fa fa-times'></i> username must be minimum 5 character long").css({'color':'#f00'});
		 }
	 });
	  //validating email
	 $("#email").change(function(){
		var email = $.trim($(this).val()).replace(/\s/g,"");
		$(this).val(email);
		var path = "${baseUrl}/admin/checkEmailAvailability";
		 $.ajax({
			 url : path,
			 method : "post",
			 data : {'email':email},
			 dataType : "text",
			 success:function(resp){
				 if(resp == "available"){
					 $("#disp_em_msg").empty().append("<i class='fa fa-check'></i> available").css({'color':'green'});
				 }else{
					 $("#email").val("");
					 $("#disp_em_msg").empty().append("<i class='fa fa-times'></i> email already in use").css({'color':'#f00'});
				 }
			 } 
		 });
	 });
	  //validating pwd
	  $("#pwd").change(function(){
		  var pwd = $(this).val();
		  if(pwd.length < 6){
			  $(this).val("");
			  $("#err_pwd").empty().show().append("password must be minimum 6 characters").fadeOut(10000);
		  }
	  });
	  
	  //validating pwd && re-pwd
	  $("#re_pwd").change(function(){
		  var re_pwd = $(this).val();
		  var pwd = $("#pwd").val();
		  
		  if(pwd == re_pwd){
			  $("#disp_pwd_msg").empty().append("<i class='fa fa-check'></i> password matched").css({'color':'green'});
		  }else{
			  $("#pwd").val("");
			  $("#re_pwd").val("");
			  $("#disp_pwd_msg").empty().show().append("<i class='fa fa-times'></i> password disn't mactched.Please retry").css({'color':'#f00'}).fadeOut(10000);
		  }
		  
	  });
		  
  });
</script>
</body>
</html>
