function deleteDeposit(instance,isBankAccountInfoPage){
	var deposit_id = $("#viewDeleteId").val();
	var remark = $("#remarks_id").val();
	var url =baseUrl + "deposit/delete";
	$.post(url,{depositId:deposit_id,remarks:remark})
	 .done(function(response){
		 if(response.success){ 
			if(isBankAccountInfoPage){
				depositInfo();
			}else getAllDepositByTeamId();		
			if(response.message) alert(response.message);
			 $('#deleteModal').modal('hide');
			  $('#deleteModal').trigger("reset");
		 }
	 })
	 .fail(function(xhe,status,error){
		alert(SOMETHING_WENT_WRONG);
	 })
	 .always(function(){
		 hideLoader(); 
	 })
	
}

function deleteDepositReasonModal(instance){
	 if(confirm('Are you sure you want to delete?')){
	      var depositId = $(instance).attr("data-id");	 
	      $("#viewDeleteId").val(depositId );
	      $('#deleteModal').modal('toggle');
	   }
}
