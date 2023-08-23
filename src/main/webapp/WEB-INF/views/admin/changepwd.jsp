<%@ include file="../fragments/header.jsp" %>

<!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Admin Management<span><i class="fa fa-angle-double-right"></i> ${title}</span></h1>
    </section>
    
<!-- Main content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-6">
    			<div class="box">
			        <div class="box-body">
			        	<form action="${baseUrl}/admin/changepwd" method="post" id="change_pwd_form">
					    	${message}
						    <div class="form-group has-feedback">
						      	<label>Current Password</label>
						        <input type="password" name="current_pwd" id="curr_pwd" autofocus="autofocus" class="form-control" placeholder="Enter current Password" required>
						        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
						        <span class="error" id="err_curr_pwd">${err_curr_pwd}</span>
						     </div>
					      <div class="form-group has-feedback">
					      	<label>New Password</label>
					        <input type="password" name="new_pwd" id="pwd" class="form-control" placeholder="Enter new Password" required>
					        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
					        <span class="error" id="err_pwd">${errpwd}</span>
					      </div>
					      <div class="form-group has-feedback">
					      	<label>Re-Enter new Password</label>
					        <input type="password" name="confirmed_new_pwd" id="re_pwd" class="form-control" placeholder="Retype New Password" required>
					        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
					        <span class="error">${err_repwd}</span>
					        <span id="disp_pwd_msg"></span>
					      </div>
					      <button type="submit" class="btn btn-blue">Change Password</button>
					      <a href="${baseUrl}/admin/${sessionScope.admin_username}" class="btn btn-default"><i class="fa fa-arrow-left"></i></a>
					    </form>
			        </div>
			    </div>
			   </div>
    		</div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <script type="text/javascript">
    $(document).ready(function(){
      $('#change_pwd_form').validate();
      
      //validate current pwd
      $("#curr_pwd").change(function(){
    	 var curr_pwd = $(this).val();
    	 var path = "${baseUrl}/iscurrentpwdcorrect";
    	 $.ajax({
    		url : path,
    		method : "post",
    		dataType : "text",
    		data : {'pwd':curr_pwd},
    		success:function(resp){
    			if(resp == 'false'){
    				$("#curr_pwd").val("");
    				$("#err_curr_pwd").html("<i class='fa fa-times'></i> Incorrect current password").show().fadeOut(10000);
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
			  $("#disp_pwd_msg").empty().append("<i class='fa fa-check'></i> password matched").css({'color':'green'}).show();
		  }else{
			  $("#pwd").val("");
			  $("#re_pwd").val("");
			  $("#disp_pwd_msg").empty().append("<i class='fa fa-times'></i> password didn't matched.Please retry").css({'color':'#f00'}).show().fadeOut(15000);
		  }
		  
	  });
      
    });
  </script>
  <%@ include file="../fragments/footer.jsp" %>