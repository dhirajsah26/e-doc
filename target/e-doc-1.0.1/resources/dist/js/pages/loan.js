
	function deleteDepositReasonModal(instance){
		 if(confirm('Are you sure you want to delete?')){
		      var loanId = $(instance).attr("data-id");	
		      $("#loanDeleteId").val(loanId );
		      $('#deleteLoanModal').modal('toggle');
		   }
	}

	function deleteLoan(instance,isLoanPage){
		var loan_id = $("#loanDeleteId").val();
		var remark = $("#loan_remarks").val();
		var url = baseUrl + "loanmanagement/delete";
		$.post(url,{loanId:loan_id,remarks:remark})
		 .done(function(response){
			 if(response.success){ 
				 if(isLoanPage){
					 getAllLoanManagemenByBankAccountId();
				 }else{
					 getAllLoanManagement();
				 }
				if(response.message) alert(response.message);
				 $('#deleteLoanModal').modal('hide');
				  $('#deleteLoanReasonForm').trigger("reset");
			 }
		 })
		 .fail(function(xhe,status,error){
			alert(SOMETHING_WENT_WRONG);
		 })
		 .always(function(){
		 })
		
	}