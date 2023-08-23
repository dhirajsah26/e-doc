<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade" id="editUserRoleModal" role="dialog" data-backdrop="static">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Edit User Role</h4>
			</div>
			<div class="modal-body">
				<div class="loader"></div>
					<form id="editUserRoleForm"> 
						<input type="hidden" name="userRoleId" value="0">
							<div class="form-group">
								<label>Name</label><span class="mandatory">*</span>
								<input name="name" placeholder="Enter user role" class="form-control" onchange="checkNameAvailableOrNot(this)" required>
							</div>
							<!-- <div class="form-group">
								<label><input type="checkbox" name="superUserRole" value="1"> Is Super User Role</label>
							</div> -->
			                <label>Status</label>
				          	<label class="radio-inline"><input type="radio" id = "activeStatus" name="status" value="1"> Active</label>
				          	<label class="radio-inline"><input type="radio" id="deActiveStatus" name="status" value="0"> De-Active</label>
					</form>
			   </div>
			 <div class="modal-footer">
				<button type="button" onclick="updateUserRole(this)" class="btn btn-success btnUpdate"><i class="material-icons">send</i> Update</button>
				<!-- <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons">close</i> Close</button> -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

  function checkNameAvailableOrNot(instance){
		var url = baseUrl + "user/role/name/checkavailability";
		var name = $.trim($(instance).val());
		if(name != ''){
			$.post(url, {name : name, id : null})
			.done(function(response){
				if(!response.success){
					$(instance).val("");
					$("#code-error").empty().html(response.message).show().fadeOut(10000);
				}
			})
			.fail(function(xhr, status, error){
				alert(SOMETHING_WENT_WRONG);
			});
		}
	}

	function populateData(instance) {
		var userRoleId = $(instance).attr('data-id');
		var url = baseUrl + "user/role/fetchbyid";
		$.post(url, {userRoleId : userRoleId})
		.done(function(response) {
			if (response.success){
				$("#editUserRoleModal").modal('show');
				var formInstance = $("#editUserRoleForm");
				formInstance.find("input[name='name']").val(response.body.name);
				formInstance.find("input[name='userRoleId']").val(response.body.id);
				formInstance.find("input[name=superUserRole]").iCheck(response.body.superUserRole ? 'check' : 'uncheck');
				
				//formInstance.find("input[name='status']").iCheck(response.body.status ? 'checked, true' : 'unchecked');
				var radioButtonInstance = response.body.status ? $("#activeStatus") : $("#deActiveStatus");
				 radioButtonInstance.prop("checked", true);
				 $("input[name=status]").iCheck({
		        	checkboxClass: 'icheckbox_square-blue',
		        	radioClass: 'iradio_square-blue',
				    increaseArea: '20%' // optional
				});  
				$("label.error").remove();
			}
			if(response.message) alert(response.message);
		})
		.fail(function(xhr, status, error) {
			alert(SOMETHING_WENT_WRONG);
		})
		.always(function() {
			
		})
	}
	
	function updateUserRole(instance){
		if (!$("#editUserRoleForm").valid())
			return;
		showButtonLoader(instance, "Updating");
		var url = baseUrl + "user/role/update";
		$.post(url, $("#editUserRoleForm").serialize())
		.done(function(response) {
		   if (response.success) {
			  $("#editUserRoleModal").modal("hide");
			}
		    selectAllUserRole();
			if(response.message) alert(response.message);
		})
		.fail(function(xhr, status, error) {
			alert(SOMETHING_WENT_WRONG);
		})
		.always(function() {
			hideButtonLoader(instance);
	    });
	 }	 
</script>