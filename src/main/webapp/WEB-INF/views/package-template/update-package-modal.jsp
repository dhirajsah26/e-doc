
<!-- =============================================== -->

	<div class="modal fade" id="packageTemplateModal" role="dialog" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Update Package</h4>
				</div>
				
				<input type="hidden" id="team-id" />
				
				<div class="modal-body">
					<form id="package-template">
				      	<label>Package Template <span class="mandatory">*</span></label>
				      	<select class="form-control select-item-type" id="package" required>
					      	<option value="">Select Package Template</option>
					    </select>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="material-icons">close</i> Close
					</button>
					<button type="button" class="btn btn-success btnAssign" id="btn-save"
						onclick="editPackageTemplate(this)">
						<i class="material-icons">send</i> Update
					</button>
			</div>
			</div>
		</div>
	</div>

<script type="text/javascript">
	
	$(".select-item-type").select2();

	function conductAllOperations(instance){
		populatepackagetemplates(instance);
		populateselectedpackagetemplates(instance);
	}
	
	function populatepackagetemplates(instance) {
		showLoader();

		$("label.error").remove();

		var url = baseUrl + "populatepackagetemplatesforschoolprofile";
			
		$.post(url).done(function(response) {
			hideLoader();
			var html = "";
			if (response.success){

			$.each(response.body, function(index, item) {
				console.log(response.body)
				html += "<option value = '"+ item.id +"'>"+ item.packageTemplateName +"</option>";
			});

				$("#package").html(html);
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

	function populateselectedpackagetemplates(instance) {
		var url = baseUrl + "populateselectedpackagetemplate";
		var teamId = $(instance).attr("team-id");

		$("#team-id").val(teamId);
			
		$.post(url, {teamId : teamId}).done(function(response) {
			if (response.success){
				var packageId = response.body;
				$("#package").val(packageId).trigger("change");
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

	function editPackageTemplate(instance){

		if(!$("#package-template").valid())
			return ;
		
		var url = baseUrl + "schoolaccount/package/update";
		var teamId = $("#team-id").val();
		var selectedPackageTemplateId = $("#package :selected").val();

		showButtonLoader(instance, "Saving");
		
		$.post(url, {teamId : teamId, packageTemplateId : selectedPackageTemplateId}).done(function(response) {
			hideLoader();
			if (response.success){
				alert(response.message);
				$("#editFiscalYearModal").modal("hide");
				window.location.reload();
			}
		}).fail(function(xhr, status, error) {
			alert("Sorry but something went wrong.");
		}).always(function() {
			$(".btnUpdate").html(
					"<i class=\"material-icons\">send</i> Update");
			$(".btnUpdate").prop("disabled", false);
		})
	}
	
</script>

<!-- loader -->
<script type="text/javascript" src="${baseUrl}/resources/dist/js/custom-loader.js"></script>
