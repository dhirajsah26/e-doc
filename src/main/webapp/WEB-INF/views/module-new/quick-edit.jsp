 <div class="modal fade" id="quick-edit-modulenew-modal" role="dialog" data-backdrop="static">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Quick Edit Module</h4>
			</div>
			<div class="modal-body">
				<form id="quick-update-module-new">
				
					<input name="id" id ="id" type="hidden" />
				
					<div class="form-group">
		          		<label>Name<span class="mandatory">*</span></label>
		          		<input maxlength="100" type="text" id="name" name="name" placeholder="Enter module name" class="form-control" required>
		          	</div>
		          	
		          	<div class="row">
		          		<div class="col-md-6">
		          		
		          		</div>
		          	</div>
		          	
		          	<div class="row">
		          		<div class="col-md-6">
		          			<div class="form-group">
				          		<label>Icon<span class="mandatory">*</span></label>
				          		<input type="text" id="icon" name="icon" class="form-control" placeholder="Enter icon code" required>
				          	</div>
				       	</div>
		          	
		          		<div class="col-md-6">
				          	<div class="form-group">
				          		<label>Rank<span class="mandatory">*</span></label>
				          		<input type="text" min="0" id="rank" name="rank" class="form-control" placeholder="Enter rank" required>
				          	</div>
				       	</div>
		          	</div>
		          	
		          	<div class="row">
		          		<div class="col-md-6">
		          			<div class="form-group targetUrlBlock">
				          		<label>Target Url</label>
				          		<input type="text" id="targetUrl" name="targetUrl" class="form-control" placeholder="Enter module url" >
				          	</div>
		          		</div>
		          		<div class="col-md-6">
		          			<div class="form-group">
				          		<label>Permission Key</label>
				          		<input type="text" id="permissionKey" name="permissionKey" class="form-control" placeholder="Enter permission key">
				          	</div>
		          		</div>
		          	</div>
		          	
		          	<div class="row">
		          		<div class="col-md-6">
		          			<div class="form-group">
				          		<label>Is View Menu<span class="mandatory">*</span></label>
				                		
		                		<label class="radio-inline"><input type="radio" name="viewMenu" value="1"> Yes</label>
		                		<label class="radio-inline"><input type="radio" name="viewMenu" value="0"> No</label>
				           	</div>
		          		</div>
		          		<div class="col-md-6">
		          			<div class="form-group">
				          		<label>Status<span class="mandatory">*</span></label>
		                		
		                		<label class="radio-inline"><input type="radio" name="status" value="1"> Active</label>
		                		<label class="radio-inline"><input type="radio" name="status" value="0"> De-active</label>
				              </div>
		          		</div>
		          	</div>
				</form>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<i class="material-icons">close</i> Close
				</button>
				<button type="button" class="btn btn-success btnUpdate"
					onclick="quickEditModuleNew(this)">
					<i class="material-icons">send</i> Update
				</button>
			</div>
		</div>
	</div>
</div>
  
  <script type="text/javascript">

  function displayQuickEditModalNew(instance){
		var url = baseUrl + "admin/module/v2/populateselectedmodulenew";
		var id = $(instance).attr("data-val");

		$("#id").val(id);

		$.post(url, {id : id}).done(function(response) {
			if (response.success){
				var data = response.body;

				var formInstance = $("#quick-update-module-new");
				
				$("#name").val(data.name);
				$("#icon").val(data.icon);
				$("#targetUrl").val(data.targetUrl);
				$("#permissionKey").val(data.permissionKey);
				$("#rank").val(data.rank);

				var selectedStatusButton = data.status ? formInstance.find("input[name=status]:eq(0)") : formInstance.find("input[name=status]:eq(1)");
				selectedStatusButton.iCheck("check");
				formInstance.find("input[name=status]").iCheck("update");
			
				var selectedIsViewButton = data.viewMenu ? formInstance.find("input[name=viewMenu]:eq(0)") : formInstance.find("input[name=viewMenu]:eq(1)");
				selectedIsViewButton.iCheck("check");
				formInstance.find("input[name=viewMenu]").iCheck("update");
			}
		}).fail(function(xhr, status, error) {
			alert("Sorry but something went wrong.");
		}).always(function() { })
	}

  function quickEditModuleNew(instance){
	//validate form
	$("#quick-update-module-new").validate();

	//proceed if valid
	if (!$("#quick-update-module-new").valid())
		return;

	showButtonLoader(instance, "Updating");
	var url = baseUrl + "admin/module/v2/quickupdate";
	var formvalues = $("#quick-update-module-new").serialize();

	$.post(url, formvalues).done(function(response) {
		if (response.success){
			alert(response.message);
			$("#quick-edit-modulenew-modal").modal("hide");
			populateParentModules();
		}
	}).fail(function(xhr, status, error) {
		alert("Sorry but something went wrong.");
	}).always(function() {
		hideButtonLoader(instance);
	})
  }
</script>
 