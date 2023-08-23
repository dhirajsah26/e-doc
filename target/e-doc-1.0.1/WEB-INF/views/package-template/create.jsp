<%@ include file="../fragments/header.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Package Template Management <span><i class="fa fa-angle-double-right"></i>
				${title}</span></h1>
	</section>

	<!-- Main content -->
	<section class="content">
		<!-- Default box -->
		<div class="box">
			<div class="box-body">
				<form
					action="${baseUrl}/admin/package/template/create"
					method="post" id="package-template">

					<div class="form-group">
						<label>Package Name <span class="mandatory">*</span></label> <input
							type="text" class="form-control" name="packageTemplateName" required>
					</div>

					<div class="alert alert-danger no-marginn showProficiencyError" id="proficiencyErrorDiv"></div>

					<div class="tab-content">
					<div class="loader"></div>
						<div id="package_templates" class="tab-pane fade in active">
							<table class="table table-bordered awesome-table" id="package-template">
								<thead>
									<tr>
										<th>S.N</th>
										<th>Module</th>
										<th>Sub Module</th>
										<th>Menu Line</th>

									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
					
					<div class="form-group">
						<label>Remarks</label> 
						<textarea class="form-control" cols="6" name="remark" maxlength="500"></textarea>
					</div>
					
					<div>
						<button type="button" class="btn btn-blue" onclick="submitForm(this)">Save</button>
						<a href="${baseUrl}/admin/package/template"
							class="btn btn-default"><i class="material-icons">arrow_back</i>Back
							To List</a>
					</div>
				</form>
			</div>
		</div>
	</section>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		populatepackagetemplates();
		$("#package-template").validate();
		$("#proficiencyErrorDiv").hide();
	});

	function populatepackagetemplates() {
		showLoader();

		var url = baseUrl + "/admin/package/template/populatepackagetemplates";

		$.post(url).done(function(response) {
			hideLoader();

			if (response.success)

			$("#package-template").find("tbody").html(response.body);

			if (response.message)
				alert(response.message);
		}).fail(function(xhr, status, error) {
			alert("Sorry but something went wrong.");
		}).always(
				function() {
					$(".btnUpdate").html(
							"<i class=\"material-icons\">send</i> Update");
					$(".btnUpdate").prop("disabled", false);
				})
	}

	function isAnyImmediateChildChecked(input,selector){
		var rows = $(input).closest("tbody").find("tr");
		//rowSpan = $(input).parent("td").attr("rowspan");
		rowSpan = $(input).closest("td").attr("rowspan");
		rowIndex = $(input).closest("tr").index();
		for(var i=0; i<rowSpan; i++){
			if ($(rows[rowIndex+i]).find(selector).is(':checked')) return true;
		} 
		return false;
	}

	$(function(){
		$(document).on('change','input[type="checkbox"]',function(){
			var rows = $('#package-template tbody tr');
			var rowspan = $(this).closest('td').attr('rowspan');
			var row = $(this).closest('tr');
			var type = $(this).attr('class');
			var checked = $(this).is(':checked');

			if(type == 'category' || type == 'subCategory' || type == 'gradingParameter'){				
				for(var i=0; i<rowspan; i++){
					$(rows[row.index()+i]).find('input[type="checkbox"]').each(function(i,input){						
						var childType = $(input).attr('class');
						var childChecked = $(input).is(':checked');
						if(((checked && !childChecked) || (!checked && childChecked)) && ((type == 'category' && (childType == 'subCategory' || childType == 'gradingParameter')) || (type == 'subCategory' && childType == 'gradingParameter'))){
							!$(input).is(':checked') ? $(input).prop('checked',true) : $(input).prop('checked',false);
						}
					});
				}

				var pCategory = rows.find("input.category[value='"+$(this).attr('category-value')+"']");
				var pSubCategory = rows.find("input.subCategory[value='"+$(this).attr('sub-category-value')+"']");

				if(pSubCategory){
					isAnyImmediateChildChecked(pSubCategory,'.gradingParameter') ? $(pSubCategory).prop('checked',true) : $(pSubCategory).prop('checked',false)
				}
				if(pCategory){
					isAnyImmediateChildChecked(pCategory,'.subCategory') ? $(pCategory).prop('checked',true) : $(pCategory).prop('checked',false)
				}
			}
		});
	});

	function submitForm(instance) {
		var errorMsg = 'Package selection is compulsory. Please select atleast one field.'; 
		var selectIsValid = $('input[name="menuId"]:checked').length > 0 ? true : false;     
	    
	      if(selectIsValid){
	    	$("#proficiencyErrorDiv").hide();  
	    	$(".showProficiencyError").html('');
			if($("#package-template").valid()) {
					showButtonLoader(instance, "Saving");
					 $("#package-template").submit();
				}
	      }else {
          	$("#proficiencyErrorDiv").show();
            $(".showProficiencyError").html(errorMsg);
     }
	}

	// for removing error on filling vacant
	$(document).on('change', 'input[name="menuId"]',function(){	
	    if ($(this).val() != '') {
	      	$(this).next('.error').html('');	
	      	 $(".showProficiencyError").html(''); 
	      	$("#proficiencyErrorDiv").hide();   
	    }
	});
	
</script>

<!-- loader -->
<script type="text/javascript" src="${baseUrl}/resources/dist/js/custom-loader.js"></script>

<%@ include file="../fragments/footer.jsp"%>