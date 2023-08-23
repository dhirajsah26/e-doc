<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="No Permission" />

<%@ include file="fragments/header.jsp" %>

<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
			No Permission <span><i class="fa fa-angle-double-right"></i>
				Unauthorized Access</span>
		</h1>
    </section>

    <!-- Main content -->
    <section class="content">


       <div style="margin-top:150px;">
       	<p class="text-center" style="font-size:100px;"><i class="fa fa-warning text-red"></i></p>
         <h3 class="text-center">Oops! It seems like you have been restricted by administrator to access this page.</h3>
         <p class="text-center"><a href="${baseUrl}/dashboard" class="btn btn-primary"><i class="fa fa-arrow-left"></i> Back to Dashboard</a></p>
       </div>
      <!-- /.error-page -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <%@ include file="fragments/footer.jsp" %>
  