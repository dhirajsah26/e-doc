<%@ include file="../fragments/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- =============================================== -->
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Role Management <span><i class="fa fa-angle-double-right"></i> ${title}</span></h1>
    </section>
    
<!-- Main content -->
    <section class="content">
      		<!-- Default box -->
		<div class="box">
			<div class="box-header with-border">
				<div class="action-btn">
					<button role="button" class="btn btn-success btn-md" onclick="openCreateModal()"><i class="fa fa-pencil"></i> Create User Role</button>
					<span class="loader ml-15"></span>
					<div class="table-tools"></div>
				</div>
			</div>
			<div class="box-body">
				${message}
				<table id="tbl_user_role" class="table table-bordered table-striped awesome-table fs-12">
					<thead>
						<tr>
							<th>SN.</th>
							<th>Role</th>
							<th>Is Super User Role</th>
							<th>Status</th>
							<th>Created By</th>
							<th>Created Date</th>
							<th>Modified By</th>
							<th>Modified Date</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
				    </tbody>
				</table>
				<!-- /.box-body -->
				<div class="box-footer"></div>
				<!-- /.box-footer-->
			</div>
			<!-- /.box -->
		</div>
	</section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <script type="text/javascript">
  var operationDateSetting = '${operationDateSetting}';
	  $(function(){
		  selectAllUserRole();
	  })
		  
	  function selectAllUserRole(){

		showLoader();  
	    var tableInstance = $("#tbl_user_role");
		destroyDataTable(tableInstance);
		showLoader();
		var url = baseUrl+"user/role/findall"
		$.post(url)
		.done(function(response){
			//console.log(response);
			if(response.success){
				var tableRows = "";
				var i = 1;
				var status = "";

					$.each(response.body, function(index, item) {
						var assignMenuLi = '';
						var showEditAndDelteLink = '';
						if (!item.superUserRole) {
							assignMenuLi = '<li><a href="'+baseUrl+'user/role/assign/menu?role_id='+ item.id +'" target=\_blank\"> <i class="material-icons">edit</i>Assign Menu</a></li>';
							showEditAndDelteLink = '<li><a role="button" onclick="populateData(this)" data-id="'+item.id+'"> <i class="material-icons">edit</i>Edit</a></li>'+
						      '<li><a role="button" onclick="deleteUserRole(this)" data-id="'+item.id+'" class="text-red"><i class="material-icons">delete</i> Delete</a></li>';
						}
						
						if(item.status){	
							status = "<label class = \"label label-success\">Active</label>"
						}else{
							status = "<label class = \"label label-danger\">De-Active</label>"
						}
						tableRows += 
									"<tr>"+
										"<td>"+ i++ +"</td>"+
										"<td>"+item.name+"</td>" +
										"<td>"+ (getDisplayableStatus(item.superUserRole, YES_NO)) +"</td>" +
										"<td>"+status+"</td>" +
										"<td>"+item.createdBy.username+"</td>"+
										"<td>"+(item.createdDate != null ? getFinalDate(item.createdDate, operationDateSetting): "")+"</td>"+ 
										"<td>"+(item.modifiedBy ? item.modifiedBy.username: "")+"</td>"+
										"<td>"+(item.modifiedDate != null ? getFinalDate(item.modifiedDate, operationDateSetting): "")+"</td>"+
									    "<td class=\"btn-actions text-center\">"+
										   "<div class=\"btn-actions-inner\">" + 
									         "<button type=\"button\" class=\"btn btn-link\" data-toggle=\"dropdown\">" + 
									           "<i class=\"material-icons hidden\">add_circle</i>" + 
									           "<i class=\"material-icons icon_more\">more_horiz</i>" +
									       "</button>" +
									      "<ul class=\"dropdown-menu\">"+
									          /* '<li><a href="'+baseUrl+'user/role/assignpermission/'+item.id+'" data-id="'+item.id+'" target=\"blank\"> <i class="material-icons">edit</i>Assign Permission</a></li>'+*/
									          assignMenuLi +
									          '<li><a href="'+baseUrl+'user/role/'+item.id+'/info/" data-id="'+item.id+'" target=\"_blank\"> <i class="material-icons">visibility</i>View Detail</a></li>'+
									          showEditAndDelteLink +
									     "</ul>"+
									   "</div>"+
										"</td>";
								"</tr>";
				  });
				$("#tbl_user_role tbody").html(tableRows);
				}
			})
			.fail(function(xhr, status, error) {
				alert(SOMETHING_WENT_WRONG);
			}).always(function() {
			initDataTable(tableInstance, $(".table-tools"));
			hideLoader();
		});
	 }

	function deleteUserRole(instance){
		if (confirm(DELETE_CONFIRMATION)) {
			//showButtonLoader(instance, "Deleting");
			var userRoleId = $(instance).attr('data-id');
			var url = baseUrl + "user/role/delete";
			$.post(url, {userRoleId : userRoleId})
			.done(function(response) {
				selectAllUserRole();
				if(response.message) alert(response.message);
			})
			.fail(function(xhr, status, error) {
				alert(SOMETHING_WENT_WRONG);
			})
			.always(function() {
				//hideButtonLoader(instance);
			})
		}
	}
  
  </script>
 <%@ include file="create.jsp"%>
 <%@ include file="edit.jsp"%>
 <%@ include file="../fragments/footer.jsp"%>