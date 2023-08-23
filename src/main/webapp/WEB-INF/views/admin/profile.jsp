<%@ include file="../fragments/header.jsp" %>
<!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Admin Management <span><i class="fa fa-angle-double-right"></i> ${title}</span></h1>
    </section>
    
<!-- Main content -->
    <section class="content">
      		<!-- Default box -->
		      <div class="box">
		        <div class="box-header with-border">		          
					<div class="action-btn">
						<div class="rounded-action-btn">
							<a href="${baseUrl}/admin/changepwd" class="btn btn-black" data-toggle="tooltip" title="Change Password"><i class="material-icons">security</i></a>
							<%-- <a href="${baseUrl}/admin/${admin.username}/profile/edit" class="btn btn-" data-toggle="tooltip" title="Edit Profile"><i class="material-icons">edit</i></a> --%>
						</div>
						</div>
		        </div>
		        <div class="box-body">
	              <div class="row">
	              	<div class="col-md-6">
	              	${message}
	              		<table class="table table-bordered table-striped">
							
			               	<tbody>
			               		<tr>
			               			<th class="col-md-4">Image</th>
			               			<td><img src="${baseUrl}/resources/dist/img/user2-160x160.jpg" class="img-responsive"></td>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Name</th>
			               			<td>${admin.name}</td>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Username</th>
			               			<td>${admin.username}</td>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Phone</th>
			               			<td>${admin.phone}</td>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Email</th>
			               			<td>${admin.email}</td>
			               		</tr>
			               		<tr>
			               			<th>Role</th>
			               			<td>${admin.adminRole.role.name}</td>
			               		</tr>
			               		<%-- <tr>
			               			<th>Last Logged In</th>
			               			<td>${admin.last_login}</td>
			               		</tr> --%>
			               		<tr>
			               			<th class="col-md-2">Created Date</th>
			               			<%--<td>${admin.created_date}</td>--%>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Created By</th>
			               		<%--	<td>${admin.createdBy.username}</td>--%>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Modified Date</th>
			               			<%--<td>${admin.modified_date}</td>--%>
			               		</tr>
			               		<tr>
			               			<th class="col-md-2">Modified By</th>
			               		<%--	<td>${admin.modifiedBy.username}</td>--%>
			               		</tr>
			               	</tbody>
			              </table>
	              	</div>
	              </div>
		        <!-- /.box-body -->
		      </div>
		      <!-- /.box -->
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <%@ include file="../fragments/footer.jsp" %>