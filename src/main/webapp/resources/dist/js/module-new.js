function handleRoleChange(instance) {
	$(".moduleBlock, .subModuleBlock").hide();
	$(".module, .subModule").attr({required : false});
	$(".targetUrl.mandatory").hide();
	//$("input[name=targetUrl]").attr({value : '', required : false});
	
	var value = $(instance).val();
	
	if (value === 'MENU_LINE') {
		$(".targetUrlBlock").show();
		$("input[name=targetUrl]").attr({required : true});
		$(".targetUrl.mandatory").show();
		
		$(".moduleBlock").show();
		$(".subModuleBlock").show();
	} else if (value === 'SUB_MODULE'){
		$(".moduleBlock").show();
	}
	
	if (value != 'MODULE') {
		if (value === 'MENU_LINE') {
			$(".subModule").attr("required", true);
		}
			
		$(".module").attr("required", true);
		filterModules('SUB_MODULE', true);
	}
}

function filterModules(role, loadModule) {	
	var parentRole = role === 'SUB_MODULE' ? 'MODULE' : 'SUB_MODULE';  		
	var url = baseUrl + "admin/module/v2/filter";
	
	var parentModuleId = null;
	if (!loadModule) {
		if ($(".module").prop("selectedIndex") <= 0) {
			$(".subModule").html("<option value=\"\">Select Sub Module</option>");
			return;
		}
		parentModuleId = $(".module option:selected").val();
	}
		
	$.post(url, {role : parentRole, parentModuleId : parentModuleId, activeOnly : true})
	.done(function(response) {
		if (response.success) {
			var options = "<option value=\"\">Select Module</option>";
			$.each(response.body, function(i, item) {
				options += "<option value='"+ item.id +"'>"+ item.name +"</option>";
			});
			  				
			if (loadModule) {
				$(".module").html(options);
			} else {
				$(".subModule").html(options);
			}
		}
		if (response.message) alert(response.message);
	})
	.fail(function(){
		alert(SOMETHING_WENT_WRONG);
	})
	.always(function(){
		
	});
}