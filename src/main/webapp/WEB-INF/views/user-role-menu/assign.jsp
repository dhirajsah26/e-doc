<%@ include file="../fragments/header.jsp"%>
<!-- =============================================== -->

<style>
	#tbl_user_menu {table-layout:fixed;}
	table label {margin-bottom : 0; cursor : pointer;}
	/* td i.material-icons {
		color: #0088fe;
	    border: 1px solid #0088fe;
	    height: 23px;
	    border-radius: 50%;
	    text-align: center;
	    font-size: 12px !important;
	    line-height: 23px;
	    width: 23px;
	    margin-right: 4px;
    } */
</style>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Menu Management<span><i class="fa fa-angle-double-right"></i> ${title}</span>
		</h1>
	</section>

	<!-- Main content -->
	<section class="content">
		<!-- Default box -->
		<div class="box">
			<div class="box-body">
				${message}
				<form action="${baseUrl}/user/role/assign/menu" method="post" id="assign_menu">
			 	<div class="tab-content">
				  <div class="loader"></div>
				  	<div class="row">
				  		<div class="col-md-3">
				  			<div class="form-group">
						  		<label>User Role<span class="mandatory">*</span></label>
						  		<c:set var="roleId" value="${param['role_id']}" />
						  		<select class="form-control init-select2" placeholder="Select User Role" name="roleId" onchange="populateMenus(this)">
						  			<option value="">Select User Role</option>
						  			<c:forEach var="role" items="${userRoles}">
						  				<option <c:if test="${roleId == role.id}">selected</c:if> value="${role.id}">${role.name}</option>
						  			</c:forEach>
						  		</select>
						  	</div>
				  		</div>
				  	</div>
				  	
					<div class="tab-pane fade in active">
						<table id="tbl_user_menu" class="table table-bordered awesome-table">
							<thead>
								<tr>
									<th width="60">S.N</th>
									<th style="width: 25% !important; vertical-align: middle">Module</th>
									<th style="width: 25% !important; vertical-align: middle">Sub Module</th>
									<th style="min-width: 150px; vertical-align: middle">Menu Line</th>
								</tr>
							</thead>
							<tbody>
									
							</tbody> 
						</table>
					</div>
					</div>
					
					<div class="form-group">
						<label>Remarks</label> 
						<textarea class="form-control" rows="2" name="remark" maxlength="500" placeholder="Enter remarks (if any)"></textarea>
					</div>
					
					<button type="button" class="btn btn-blue" id="btnSubmit" onclick="submitForm(this)">Save</button>
					<a href="${baseUrl}/user/role" class="btn btn-default"><i class="material-icons">arrow_back</i>Back</a>
				</form>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<script type="text/javascript">
	var operationDateSetting = '${operationDateSetting}';

	function submitForm(instance){
		showButtonLoader(instance, "Saving");
		$("#assign_menu").trigger("submit");
	} 
	
	function populateMenus(instance) {
		showLoader();
		var userRoleId = $(instance).val();
		var url = baseUrl + "user/role/populatemenus";
		$.post(url, {userRoleId : userRoleId})
		.done(function(response) {
			$("#tbl_user_menu tbody").append(response.body);
			if (response.message) alert(response.message);
		})
		.fail(function(xhr, status, error) {
			alert(SOMETHING_WENT_WRONG);
		})
		.always(function() {
			hideLoader();
		});
	}

	function isAnyImmediateChildChecked(input,selector){
		var rows = $(input).closest("tbody").find("tr");
		//rowSpan = $(input).parent("td").attr("rowspan");
		rowSpan = $(input).closest("td").attr("rowspan");
		if (!rowSpan) rowSpan = 1;
		
		rowIndex = $(input).closest("tr").index();
		for(var i=0; i<rowSpan; i++){
			var checkedFound=false;
			$(rows[rowIndex+i]).find(selector).each(function(){
				if($(this).is(':checked')){
					checkedFound=true;
					return false;
				};   
			});
			if(checkedFound) return true;			
		} 
		return false;
	}

	$(function(){
		var roleId = $("select[name=roleId] :selected").val();
		if (roleId) {
			$("select[name=roleId]").trigger("change");
		}
		
		$(document).on('change','input[type="checkbox"]',function(){
			var rows = $('#tbl_user_menu tbody tr');
			var rowspan = $(this).closest('td').attr('rowspan');
			var row = $(this).closest('tr');
			var type = $(this).attr('class');
			var checked = $(this).is(':checked');

			if (type == 'subModule' && !rowspan)
				rowspan = 1;

			if(type == 'module' || type == 'subModule' || type == 'menuline'){				
				for(var i=0; i<rowspan; i++){
					$(rows[row.index()+i]).find('input[type="checkbox"]').each(function(i,input){						
						var childType = $(input).attr('class');
						var childChecked = $(input).is(':checked');
						if(((checked && !childChecked) || (!checked && childChecked)) && ((type == 'module' && (childType == 'subModule' || childType == 'menuline')) || (type == 'subModule' && childType == 'menuline'))){
							!$(input).is(':checked') ? $(input).prop('checked',true) : $(input).prop('checked',false);
						}
					});
				}

				var pCategory = $("#tbl_user_menu input.module[value='"+$(this).attr('module-value')+"']");
				var pSubCategory = $("#tbl_user_menu input.subModule[value='"+$(this).attr('sub-module-value')+"']");

				if(pSubCategory){
					isAnyImmediateChildChecked(pSubCategory,'.menuline') ? $(pSubCategory).prop('checked',true) : $(pSubCategory).prop('checked',false)
				}
				if(pCategory){
					isAnyImmediateChildChecked(pCategory,'.subModule') ? $(pCategory).prop('checked',true) : $(pCategory).prop('checked',false)
				}
			}
		});
	});

	function submitForm(instance) {
		var selectIsValid = $('input[name="menuId"]:checked').length > 0 ? true : false;     
		if(selectIsValid){	
			if($("#assign_menu").valid()) {
				showButtonLoader(instance, "Saving");
				$("#assign_menu").submit();
			}
	      } else {
	    	var errorMsg = 'Menu selection is compulsory. Please select at least one field.'; 
          	alert(errorMsg);
          }
	}

</script>
<!-- moment js -->
<script src="${baseUrl}/resources/plugins/moment/min/moment.min.js"></script>
<%@ include file="../fragments/footer.jsp"%>