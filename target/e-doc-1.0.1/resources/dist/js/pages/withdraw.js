function deleteWithdraw(instance,isBankAccountInfoPage ){
	var withdrawId = $("#withdrawDeleteId").val();
	var remarks = $("#withdraw_remarks_id").val();
	var url = baseUrl + "withdraw/delete";
	$.post(url,{withdrawId:withdrawId, remarks:remarks})
	 .done(function(response){
		 if(response.success){
			 if(isBankAccountInfoPage ) withdrawInfo();	 
			 else getAllWithdrawByTeamId();	 
		 }	 
		 if (response.message)  alert(response.message); 
		 $('#deleteWithdrawReasonForm').trigger("reset");
		 $("#deleteWithdrawModal").modal('hide');	 
	 })
	 .fail(function(xhe,status,error){
		alert(SOMETHING_WENT_WRONG);
	 })
	 .always(function(){
	 })
}

function deleteWithdrawReasonModal(instance){
	 if(confirm('Are you sure you want to delete ?')){
	      var withdrawId = $(instance).attr("data-id");	   
	      $("#withdrawDeleteId").val(withdrawId);
	      $('#deleteWithdrawModal').modal('toggle');
	   }
}

