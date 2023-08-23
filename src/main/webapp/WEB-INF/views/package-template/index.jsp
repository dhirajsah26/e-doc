<%@ include file="../fragments/header.jsp"%>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
			Package Template<span> <i class="fa fa-angle-double-right"></i>
				${title}</span>
		</h1>
    </section>
    
<!-- Main content -->
    <section class="content">
      		<!-- Default box -->
		      <div class="box">
				<div class="box-header with-border">
					<div class="action-btn">
							<a href="${baseUrl}/admin/package/template/create" class="btn btn-success btn-md"><i class="fa fa-plus"></i> Create New</a>
						<!-- Button trigger modal -->
						<div class="table-tools"></div>
					</div>
				</div>
		      
		        <div class="box-body">
		          ${message}
	              <table id="package-template-table" class="table table-bordered advanced_table fs-12">				
	                <thead>
	                <tr>
	                  <th>S.N.</th>
	                  <th>Package Name</th>
	                  <th>Created Date</th>
	                  <th>Created By</th>
	                  <th>Modified Date</th>
	                  <th>Modified By</th>
	                  <th>Remarks</th>
	                  <th>Actions</th> 
	                </tr>
	                </thead>
	                <tbody> 
	                	<% int i=1; %>
	                	<c:forEach var="packageTemplateInfo" items="${packageTemplateInfos}">
	                		<tr>
	                			<td><%= i++ %>.</td>
	                			<td>${packageTemplateInfo.name}</td>
		                		<td>${packageTemplateInfo.createdDate}</td>
		                		<td>${packageTemplateInfo.createdBy}</td>
		                		<td>${packageTemplateInfo.modifiedDate}</td>
		                		<td>${packageTemplateInfo.modifiedBy}</td>
		                		<td>${packageTemplateInfo.remarks}</td>
		                		<td class="btn-actions text-center"><div class="btn-actions-inner">
		                			<button type="button" class="btn btn-link" data-toggle="dropdown">
								      <i class="material-icons hidden">add_circle</i>
								      <i class="material-icons icon_more">more</i>
									</button>
		                			
		                			<ul class="dropdown-menu">
										<li><a href="${baseUrl}/admin/package/template/${packageTemplateInfo.id}/view"><i class="material-icons">visibility</i>View Details</a></li>
			                			<li><a href="${baseUrl}/admin/package/template/${packageTemplateInfo.id}/edit"><i class="material-icons">edit</i>Edit</a></li>
			                			<li><a href="${baseUrl}/admin/package/template/${packageTemplateInfo.id}/delete" onclick="return confirm('Are you sure you want to delete ?')" class="text-red"><i class="material-icons">delete</i>Delete</a></li>
		                			</ul>
		                		</div></td>
	                		</tr>
	                	</c:forEach>
	                
	                </tbody>
	                
	              </table>
		        <!-- /.box-body -->
		        </div>
		        <!-- /.box-footer-->
		      </div>
		      <!-- /.box -->
    </section>
  </div>
  
  <script type="text/javascript">
    $(document).ready(function(){
    	//$('#package-template-table').DataTable();
    });
  </script>
 
<%@ include file="../fragments/footer.jsp"%>