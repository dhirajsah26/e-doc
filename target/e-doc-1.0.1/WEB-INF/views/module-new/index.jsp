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
        <li><a href="#"><i class="fa fa-th"></i> Module</a></li>
        <li class="active">Index</li>
      </ol>
    </section>
    
<!-- Main content -->
    <section class="content">
      		<!-- Default box -->
		      <div class="box">
		        <div class="box-header with-border">		          
					<div class="action-btn">
						<a href="${baseUrl}/admin/module/v2/create" class="btn btn-success btn-md"><i class="fa fa-plus"></i> Create Module</a>
						<span class="loader ml-15"></span>
						<div class="col-md-3 pull-right">
							<select class="form-control select-item-type" name="parentModuleId" id = "parent-module"> 
							</select>
						</div>
					</div>
		        </div>
		        <div class="box-body">
		        	${message}
	              <table class="table table-bordered table-striped awesome-table fs-12" id="permission-module-table">
	                <thead>
	                <tr>
	                  <th>SN.</th>
	                  <th>Name</th>
	                  <th>Icon</th>
	                  <th>Parent Module</th>
	                  <th>Role</th>
	                  <th>Target Url</th>
	                  <th>Permission Key</th>
	                  <th width="50px;">Rank</th>
	                  <th width="50px;">Status</th>
	                  <th width="60px;">View Menu</th>
	                  <th>Created</th>
					  <th>Modified</th>
	                  <th>Actions</th>
	                </tr>
	                </thead>
	                <tbody>
	                	<%-- <c:forEach var="module" items="${modules}">
	                		<tr>
			                  <td></td>
			                  <td>${module.name}</td>
			                  <td><i class="material-icons">${module.icon}</i></td>
			                  <td>${module.parentModuleName}</td>
			                  <td>${module.role}</td>
			                  <td>${module.targetUrl}</td>
			                  <td>${module.permissionKey}</td>
			                  <td>${module.rank}</td>
			                  <td>
			                  	<span class="label label-${module.status ? 'success' : 'danger'}">${module.status ? 'Active' : 'In-active'}</span>
			                  </td>
			                  <td>
			                  	<span class="label label-${module.viewMenu ? 'success' : 'danger'}">${module.viewMenu ? 'Yes' : 'No'}</span>
			                  </td>
			                  <td>${module.createdDate}</td>
			                  <td>${module.createdBy}</td>
			                  <td>${module.modifiedDate}</td>
			                  <td>${module.modifiedBy}</td>
			                  <td>
			                  	<a href="${baseUrl}/admin/module/v2/${module.id}/edit" class="btn btn-success btn-sm" data-toggle="tooltip" title="Edit"><i class="fa fa-edit"></i></a> 
			                  	<a href="${baseUrl}/admin/module/v2/${module.id}/delete" class="btn btn-danger btn-sm" data-toggle="tooltip" title="Delete" onclick="return confirm('Are you sure you want to delete?')"><i class="fa fa-trash"></i></a>
			                  </td>
			                  
			                  <td class
		        <div class="table-respons"	="btn-actions text-center">
									<div class="btn-actions-inner">
									<button type="button" class="btn btn-link" data-toggle="dropdown">
								      <i class="material-icons hidden">add_circle</i>
								      <i class="material-icons icon_more">more_horiz</i>
								    </button>
									
									 <ul class="dropdown-menu">
										<li><a href="${baseUrl}/admin/module/v2/${module.id}/edit"><i class="material-icons">edit</i>Edit</a> </li>
										<li><a href="${baseUrl}/admin/module/v2/${module.id}/delete" class="text-red"onclick="return confirm('Are you sure to delete ?')"> <i class="material-icons">delete</i>Delete</a></li>
									</ul>
									</div>		
								</td>
			                  
			                </tr>
	                	</c:forEach> --%>
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

<script type="text/javascript">

	$(".select-item-type").select2({});

	$(document).ready(function(){
		setTimeout(function(){
			populateParentModules();
		},1);
		populateParentModuleNamesOnly();
	});
		
	function populateParentModules(instance){
		showLoader();
		var tableInstance = $('#permission-module-table');
		destroyDataTable(tableInstance);
		
		var parentModuleId = $("#parent-module option:selected").val();
		var url = baseUrl + "admin/module/v2/fetchallmodulerelateddatasbyid";

		$.post(url, {moduleId : parentModuleId}).done(function(response) {
			var html = "";
			if (response.success){
				$.each(response.body, function(index, item) {
				var status = "<span class='label label-"+(item.status ? 'success' : 'danger')+"'>"+ (item.status ? 'Active' : 'In-active') +"</span>";
				var viewMenu = "<span class='label label-" + (item.viewMenu ? 'success' : 'danger')+"'>"+ (item.viewMenu ? 'Yes' : 'No') +"</span>";	

					html += '<tr>';
					html += '<td>'+ ++index +'.</td>'
					html += '<td>'+ item.name +'</td>';
					html += '<td><i class="material-icons">'+item.icon+'</i></td>';
					html += '<td>'+ (item.parentModuleName ? item.parentModuleName : '') +'</td>';
					html += '<td>'+ item.role +'</td>';
					html += '<td>'+ item.targetUrl +'</td>';
					html += '<td>'+ (item.permissionKey ? item.permissionKey : '')  +'</td>';
					html += '<td>'+ item.rank +'</td>';
					html += '<td>'+ status  +'</td>';
					html += '<td>'+ viewMenu +'</td>';
	               	html += '<td>'+ item.createdDate + '<br>' + (item.createdBy ? item.createdBy : '') +'</td>';
	                html += '<td>'+ (item.modifiedDate ? item.modifiedDate : '') + '<br>' + (item.modifiedBy ? item.modifiedBy : '') +'</td>';
	                //html += '<td>'+ (item.modifiedBy ? item.modifiedBy : '') +'</td>';
	                html += '<td class="btn-actions text-center"><div class="btn-actions-inner">'+
							'<button type="button" class="btn btn-link" data-toggle="dropdown">'+
						    	'<i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i>'+
						    '</button>'+
							'<ul class="dropdown-menu">'+
								'<li><a href="" data-val="'+ item.id +'" onclick="displayQuickEditModalNew(this)" data-toggle="modal" data-target="#quick-edit-modulenew-modal"><i class="material-icons">edit</i>Quick Edit</a> </li>'+
								'<li><a href="'+ baseUrl+'/admin/module/v2/'+ item.id+'/edit"><i class="material-icons">edit</i>Edit</a> </li>'+
								'<li><a href="'+ baseUrl+'/admin/module/v2/'+ item.id+'/delete" class="text-red"onclick="return confirm("Are you sure to delete ?")"> <i class="material-icons">delete</i>Delete</a></li>'+
							'</ul></div></td>';
					html +='</tr>';
				});
				
				/* $('#permission-module-table').dataTable( {
					responsive: true,
			        "scrollX": true  
			    });	 */			
			}
			tableInstance.find("tbody").html(html);
		}).fail(function(xhr, status, error) {
			alert(SOMETHING_WENT_WRONG);
		}).always(function() {
			initDataTable(tableInstance);
			hideLoader();
		})
	}

	function populateParentModuleNamesOnly(){
		var url = baseUrl + "admin/module/v2/fetchallparentmodules";
		
		$.post(url).done(function(response) {
			var html = "<option value = ''> All </option>";
			if (response.success){
				$.each(response.body, function(index, item) {
					html += "<option value = '"+ item.id +"'>"+ item.name +"</option>";
				});

				$("#parent-module").html(html);
			}
		}).fail(function(xhr, status, error) {
			alert("Sorry but something went wrong.");
		}).always(
				function() {
					$(".btnUpdate").html(
							"<i class=\"material-icons\">send</i> Update");
					$(".btnUpdate").prop("disabled", false);
				})
	}
	
	$(document).on("change", "#parent-module", function(){
		populateParentModules();
	});

</script>
 <%@ include file="quick-edit.jsp" %>
<%@ include file="../fragments/footer.jsp" %>