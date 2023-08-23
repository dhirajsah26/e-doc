  <!-- Create User Role Modal -->
<div class="modal fade" id="userRoleModal" data-backdrop="static" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h5 class="modal-title">Create User Role</h5>
		</div>
		<form id="createUserRoleForm">
			<div class="modal-body">
				
				<div class="form-group">
					<label>Name<span class="mandatory">*</span></label> 
					<input autofocus name="name" placeholder="Enter user role name" class="form-control" maxlength="60" onchange="checkNameAvailability(this)" required>
					  <span class="error" id="code-error"></span>
				</div>
				<!-- <div class="form-group">
					<label><input type="checkbox" name="superUserRole" value="1"> Is Super User Role</label>
				</div> -->
				<label>Status</label>
				<label class="radio-inline"><input type="radio" name="status" value="1" checked>Active</label> 
				<label class="radio-inline"><input type="radio" name="status" value="0">De-Active</label>
			</div>
		<div class="modal-footer">
        	<!-- <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons" >close</i>Close</button> -->
        	<button type="button" class="btn btn-success" onclick="saveUserRole(this)"><i class="material-icons" >save</i>Save</button>
      	</div>
      </form>
    </div>
  </div>
</div>

 <script type="text/javascript">
	  $(function(){
			$("#createUserRoleForm").validate();
	   });
	
	  function openCreateModal() {
		  resetForm($("#createUserRoleForm"));
		  $("#userRoleModal").modal("show");
	  }

	 function checkNameAvailability(instance){
  		var url = baseUrl + "user/role/name/checkavailability";
  		var name = $.trim($(instance).val());
  		if(name != ''){
  			$.post(url, {name : name, id : null})
  			.done(function(response){
  				if(!response.success){
  					$(instance).val("");
  					$("#code-error").html(response.message).show().fadeOut(10000);
  				}
  			})
  			.fail(function(xhr, status, error){
  				alert("Sorry but something went wrong.");
  			})
  			.always(function() {
  	  		})
  		}
	  }
  	
  	function saveUserRole(instance){
	  	 if($('#createUserRoleForm').valid()){
		   showButtonLoader(instance, "Saving");
			var url = baseUrl + "user/role/create";
			$.post(url, $("#createUserRoleForm").serialize())
			.done(function(response) {
				if (response.success) {
					$("#userRoleModal").modal('hide');
					$("#createUserRoleForm").trigger('reset');
				}
				selectAllUserRole();
				if (response.message) alert(response.message);
			})
			.fail(function(xhr, status, error) {
				alert(SOMETHING_WENT_WRONG);
			})
			.always(function() {
				hideButtonLoader(instance);
			});
		}
	}
</script>
