<%@include file="../fragments/header.jsp"%>

<div class="content-wrapper">
    <section class="content-header">
        <h1> Treatment Management <span> <i class="fa fa-angle-double-right"></i> Add </span>
        <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
	<section class="content" >
		<div class="box">
        	<div class="box-header with-border">
        	        
            	<a href="${baseUrl}/treatment/" class="btn btn-default" role="button"><i class="material-icons">arrow_back</i>&nbsp;Back</a>
            	
               	<div class="table-tools"> </div>	     	     
            </div>  
            <div class="box-body">
	            <form id="treatmentDetail" action="${baseUrl}/treatment/add" method="post"  enctype="multipart/form-data">
	            	<div class="row">		
	            		<div class="col-md-6">
		            		<div class="info-item">               
		            			<label class="lab">Patient</label><br/>
		            			<select class="form-control inp " name="patient_id" data-validation="required" data-validation-error-msg="Please select Patient" id="patientName">
		            				<option class="form-control inp" value="">Select Patient</option>
		             				<c:forEach var="patientDetail" items="${patientList}">
		            					<option class="form-control inp" value="${patientDetail.id}">${patientDetail.id}(${patientDetail.name}) </option>
		            				</c:forEach>
		            			</select>
		            			      
		            		</div>
		            		<div class= "info-item">
		            			<label class="lab ">Date of prescription</label>
		            			<input type="date" name="prescribed_date" data-validation="required"  class="form-control inp">
		            		</div>
	            		</div>
	            		<%--<div class="col-md-6">
		            		&lt;%&ndash;<div class="info-item">
		            			<label class="lab">Time</label>
		            			<input type="time" name="accident_time" data-validation="required" data-validation-error-msg="Please select machine" class="form-control inp timepicker">
		            		</div>&ndash;%&gt;
		            		<div class="info-item">
		            			<label class="lab">Location</label>
		            			<input type="text" name="location" data-validation="required" data-validation-error-msg="Please select machine" class="form-control inp">
		            		</div>
		            	</div>--%>
					</div>
		            	<hr/>  
		            	<div class="row">
			            	<div class="col-md-12">
			            		<div class="info-item">
		                                <label class="lab">Prescription Image</label>
		                                <input name="prescription_image" class="file " multiple="multiple" type="file" >
		                                <p class="help-block">Attachment should not be more than 1 MB in size.</p>
		                         </div >
		                     </div>
	                     </div>
					<div class="row">
						<div class="info-item">
							<label class="lab">Doctor</label><br/>
							<select class="form-control inp " name="doctor_id" data-validation="required"
									data-validation-error-msg="Please select Patient" id="doctorName">
								<option class="form-control inp" value="">Select Doctor</option>
								<c:forEach var="doctorDetail" items="${doctorList}">
									<option class="form-control inp" value="${doctorDetail.id}">${doctorDetail.id}(${doctorDetail.name})</option>
								</c:forEach>
							</select>

						</div>
					</div>
	                     <hr/>  
	                     <div class="row">
	                     	 <div class="col-md-12">
	                     		<label class="lab">Prescription FeedBack  </label>
			            		<textarea   name="prescriptionFeedback"  class="ckeditor form-control required" ></textarea>
	                     	</div>   
	                     </div>
	                     <div class="lab">
	                     	<button type="submit" class="btn btn-primary ">Save</button>
	                     </div>
                         
	            </form>
            	           
            </div>
		</div>
	</section>
</div>
<script>

function test(instance) {
	console.log($("#treatmentDetail").serialize())
}

$.validate({
    validateOnBlur : false, // disable validation when input looses focus
    errorMessagePosition : 'inline' ,// Instead of 'inline' which is default
    scrollToTopOnError : true // Set this property to true on longer forms
  });
	/*function saveAccidentDetail(instance){
		$("#treatmentDetail").trigger("submit");
	}
	
	$("form#treatmentDetail").submit(function (e) {
       //$(instance).html("<i class=\"fa fa-spinner fa-spin\"></i>");
       //$(instance).prop("disabled", true);

       e.preventDefault();
       console.log(this);
       var formData = new FormData(this);
       console.log(formData);

       var url = "${baseUrl}" + "/accident/detail";

       $.ajax({
           url: url,
           type: 'POST',
           data: formData,
           success: function (response) {
               if (response.success) {
                   //switch to next tab
                   //$('#myTabs li:eq(3) a').tab('show');
                   alert("Data added Succesfully.")
                   
               } else {
                   if (response.message)
                       alert(response.message);
               }
           },
           error: function () {
               alert("Sorry but something went wrong.");
           },
           cache: false,
           contentType: false,
           processData: false
       }).always(function () {
          
       });

   });
	*/

</script>
<%@include file="../fragments/footer.jsp"%>

           