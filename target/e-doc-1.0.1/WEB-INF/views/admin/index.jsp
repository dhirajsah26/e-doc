<%@ include file="../fragments/header.jsp" %>

<!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        ${title}
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-user"></i> City</a></li>
        <li class="active">List</li>
      </ol>
    </section>
    
<!-- Main content -->
    <section class="content">
      		<!-- Default box -->
		      <div class="box">
		        <div class="box-body">
		          ${message}
		          
		          <div class="form-group">
		          	<a href="${baseUrl}/admin/signup" class="btn btn-success"><i class="fa fa-pencil"></i> Create Admin Account</a>
		          </div>
		          
	              <table id="state_list" class="table table-bordered table-striped fs-12">
	                <thead>
	                <tr>
	                  <th>SN.</th>
	                  <th>Name</th>
	                  <th>Username</th>
	                  <%--<th>Role</th>--%>
	                  <th>Status</th>
	                  <th>Created Date</th>
	                  <th>Created By</th>
	                  <th>Actions</th>
	                </tr>
	                </thead>
	                <tbody>
	                	<% int i=1; %>
	                	<c:forEach var="admin" items="${admins}">
	                		<tr>
	                			<td><%= i++ %></td>
	                			<td>${admin.name}</td>
	                			<td>${admin.username}</td>
	                			<%--<td>${admin.role.name}</td>--%>
	                			<td>
		                			<c:choose>
		                				<c:when test="${admin.status}">
		                					<span class="label label-success">Active</span>
		                				</c:when>
		                				<c:otherwise>
		                					<span class="label label-danger">De-Active</span>
		                				</c:otherwise>
		                			</c:choose>
		                		</td>
		                		<td>${admin.createdDate}</td>
		                		<td></td>
		                		<%-- <td>${admin.createdBy.username}</td> --%>
		                		<td>
		                			<a href="${baseUrl}/admin/${admin.username}" class="btn btn-primary btn-sm" data-toggle="tooltip" title="View Profile"><i class="material-icons">zoom_in</i></a>
		                			<a href="${baseUrl}/admin/${admin.username}/resetpwd" class="btn btn-default btn-sm" data-toggle="tooltip" title="Reset Password"><i class="material-icons">security</i></a>
		                		</td>
	                		</tr>
	                	</c:forEach>
	                </tbody>
	              </table>
		        <!-- /.box-body -->
		        <div class="box-footer">
		          
		        </div>
		        <!-- /.box-footer-->
		      </div>
		      <!-- /.box -->
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<%--   <!-- dataTable -->
  <!-- DataTables -->
<script src="${baseUrl}/resources/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${baseUrl}/resources/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){
    	$('#state_list').DataTable();
    });
  </script>
   --%>
  
  <%@ include file="../fragments/footer.jsp" %>