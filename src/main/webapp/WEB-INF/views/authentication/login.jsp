<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" href="/favicon.ico">

    <!-- Bootstrap 4.3.1 -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- Font Awesome -->
	<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

    <title>Login Here</title>
		<style>
		input[type=text]{
		padding-left:35px !important;
		}
		input[type=password]{
		padding-left:35px !important;
		}
		</style>
	</head>


<body>

<div class="row ">
    <div class="col-4 "></div>
    <div style="margin-top: 2rem; padding: 2rem;" class="col-4 bg-light shadow p-3 mb-5 rounded">
        <div class="box ">
            <div class="login-logo text-center">
				<%-- 	<span class="logo-lg"> <img
                            src="<%=request.getContextPath()%>/resources/dist/img/logo.png"
                            height="50" alt="Digital Nepal Tender Management System"> <br> --%>
						<h6><strong>Employee Management System</strong></h6>
						<h5><strong>OM SAI RAM CONSTRUCTION PVT. LTD.</strong></h5>
						<p class="text-align:center;">Log in to start your session.</p>
            </div>
            <!-- /.login-logo -->
            <div class="login-box-body">
                <p class="login-box-msg"> </p>
                <form action="<%=request.getContextPath()%>/vendor/signin"
                      method="post">

                	<div class="form-group has-feedback">
               		 	<span>Username</span>
               		 	<div class="position-relative"> 
               		 	<i style="top:12px; left: 13px" class="fa fa-user position-absolute" aria-hidden="true"></i>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
                    </div> </div>

                    <div class="form-group has-feedback">
                    <span>Password</span>
                    <div class="position-relative"> 
                    <i style="top:12px; left: 13px" class="fa fa-lock position-absolute" aria-hidden="true"></i>
                        <input type="password" class="form-control" id="password"
                               name="password" placeholder="Password" required> <span toggle="#password-field" ></span>
                    </div>
                   <a href="#">Forgot Password</a>
                    </div>
                    
                     <div class="form-group has-feedback">
                     <span>Choose Account Type</span>
                      <!--   <input type="email" class="form-control" id="accountType" name="accountType" placeholder="select" required> -->
                   	         <!--  <label class="lab">Salutation</label> -->
                                     <select name="accountType" type="text"
                                        class="form-control inp" value="${updatebidder.salutation}">
                                        <c:forEach var="salutation" items="${Arrays.ACCOUNT_TYPE}">
                                            <option <c:if test="${salutation == updatebidder.salutation}">selected
                                                </c:if>
                                                value="${salutation}">${salutation}</option>
                                        </c:forEach>
                                    </select>                
                   	                    
                   	                    
                   	                    
                   	                    
                   	                    </div> 
				    <br>
				<button class="shadow p-2 mb-5 rounded btn btn-primary btn-block " type="submit"><strong>LOG IN</strong></button>

                </form>
                <div class="text-center">Not a member?
                   <a href="<%=request.getContextPath()%>/authentication/signup">Sign up now</a>
                </div>
            </div>
            <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->
    </div>
</div>
</body>
</html>
