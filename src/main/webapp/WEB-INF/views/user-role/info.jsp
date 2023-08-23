<%@ include file="../fragments/header.jsp"%>

<style>
.info-item label:not(.label) {width : 200px !important;}
</style>

<!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>User Role Management <span><i class="fa fa-angle-double-right"></i> ${title}</span></h1>
    </section>
    
<!-- Main content -->
    <section class="content">
    	<!-- Default box -->
	      <div class="box">
	      	<div class="box-header with-border">
				<div class="action-btn">
					<a href="${baseUrl}/user/role"  class="btn btn-default"><i class="material-icons">arrow_back</i>Back</a>
					<div class="rounded-action-btn">
					
					<c:if test="${!userRoleSchool.superUserRole}">
						<a href="${baseUrl}/user/role/assign/menu?role_id=${userRoleSchool.id}" class="btn btn-success btn-sm" data-toggle="tooltip" title="Assign/Update Menu"><i class="material-icons">edit</i></a>
					</c:if>
					</div>
				</div>
			</div>
			<div class="box-body">
			
 			<ul class="nav nav-tabs">
			  <li class="active"><a data-toggle="tab" href="#user-role-info"><i class="fa fa-info-circle"></i> Basic Info</a></li>
			  <c:if test="${!userRoleSchool.superUserRole}">
			  	<li><a data-toggle="tab" href="#assign-menu" onclick="fetchByUserRoleMenu(${userRoleSchool.id}, true)"><i class="material-icons">done_all</i>Assigned Menus</a></li>
			  </c:if>
			</ul>
			
			 <div class="tab-content">
				<div id="user-role-info" class="tab-pane fade in active">
				    <div class="single-info">
					  <div class="info-item">
						<label>User Role</label> <span>${userRoleSchool.name}</span>
					  </div>
					  <div class="info-item">
						<label>Is Super User Role</label> <label class="label ${userRoleSchool.superUserRole ? 'label-success' : 'label-danger'} inline-block">${userRoleSchool.superUserRole ? 'Yes' : 'No'}</label>
					  </div>
					<div class="info-item">
						<label>Status</label>
							<c:choose>
							   <c:when test="${userRoleSchool.status}">
							   		<label class="label label-success inline-block">Active</label>
							   </c:when>
							   <c:otherwise>
							   		<label class="label label-danger inline-block">De-active</label>
							   </c:otherwise>
							</c:choose>
					</div>
					<div class="info-item">
						<label>Created Date</label> <span>${dateUtils.getFinalDate(userRoleSchool.createdDate, operationDateSetting, true, false)}</span>
					</div>
					<div class="info-item">
						<label>Created By</label> <span>${userRoleSchool.createdBy.username}</span>
					</div>
					
					<div class="info-item">
						<label>Modified Date</label> <span>
						<c:if test="${userRoleSchool.modifiedDate != null}">
	                			${dateUtils.getFinalDate(userRoleSchool.modifiedDate, operationDateSetting, true, false)}</c:if></span>
					</div>
					<div class="info-item">
						<label>Modified By</label> <span>${userRoleSchool.modifiedBy.username}</span>
					</div>
				  </div>
		     </div>
			    <div id="assign-menu" class="tab-pane fade">
				   <div class="loader"></div>
					<table class="table table-bordered table-striped awesome-table" id="tbl_assigned_menus">
						<thead>
							<tr>
									<th>S.N</th>
									<th style="min-width: 150px; vertical-align: middle">Module</th>
									<th style="min-width: 150px; vertical-align: middle">Sub Module</th>
									<th style="min-width: 150px; vertical-align: middle">Menu Line</th>
								</tr>
						</thead>
						<tbody>
								
						</tbody>
					</table>
			    </div>		
			    	
			   </div>
			</div>
			<!-- /.box-body -->
	      </div>
	      <!-- /.box -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper --> 
  
  <script>
  var userRoleSchool = "${userRoleSchool.id}";
  var isLoaded = false;
  function fetchByUserRolePermissionId(userRoleSchool,isTabMenu){
	if(isLoaded && isTabMenu) return; 
	showLoader();
	var url = baseUrl + "user/role/viewalluserrolepermission";
	$.post(url, {userRoleId : userRoleSchool})
	.done(function(response) {
			$("#tbl_assigned-permission tbody").append(response.body);
		if (response.message) alert(response.message);
	})
	.fail(function(xhr, status, error) {
		alert(SOMETHING_WENT_WRONG);
	})
	.always(function() {
		hideLoader();
		isLoaded = true;
	});
 }


  var isAssignedMenuLoaded = false;
  function fetchByUserRoleMenu(userRoleSchool,isTabMenu){
	if(isAssignedMenuLoaded && isTabMenu) return; 
	showLoader();
	var url = baseUrl + "user/role/viewallassignedmenus";
	$.post(url, {userRoleId : userRoleSchool})
	.done(function(response) {
			$("#tbl_assigned_menus tbody").append(response.body);
		if (response.message) alert(response.message);
	})
	.fail(function(xhr, status, error) {
		alert(SOMETHING_WENT_WRONG);
	})
	.always(function() {
		hideLoader();
		isAssignedMenuLoaded = true;
	});
 }
	
   </script>

<%@ include file="../fragments/footer.jsp"%>