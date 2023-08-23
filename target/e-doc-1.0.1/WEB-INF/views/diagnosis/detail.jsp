<%@include file="../fragments/header.jsp"%>
	<div class="content-wrapper">
	    <section class="content-header">
	        <h1> Diagnosis Management <span> <i class="fa fa-angle-double-right"></i>
	                Diagnosis Details</span>
	            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
	    </section>
	    <section class="content">
	    	<div class="box">   
	    		<div class="box-header with-border">
		           	<a href="${baseUrl}/diagnosis/"class="btn btn-default" role="button"><i class="material-icons">arrow_back</i>&nbsp;Back</a>
		           	<%--<a href="${baseUrl}/accident/${treatmentDetails.id}/print" class="btn btn-default" style="float:right;" role="button"><i class="material-icons">local_printshop</i>&nbsp;Print</a> --%>
					<div class="table-tools"></div>	     
            	</div>
            	<!-- end of box-header div -->
            	<div class="box-body">
        			<ul class="nav nav-tabs" id="machineTabs">
						<li><a data-toggle="tab" href="accidentDetail"><i class="material-icons">account_balance</i>&nbsp;Diagnosis Detail</a> </li>
					</ul>
					<div class="tab-content">
				  		<div id="machineDetail" class="tab-pane fade in active">
					  		<div class="row">
						  		<div class="col-md-6 ">
						  			<div class="single-info">
								  		<h3> Basic Details</h3>
								  			<div class="info-item">
										  		<label class="lab"> Patient Name  </label>
												<span class="inp"><a href="${baseUrl}/patient/${diagnosis.patientId}/details"> ${diagnosis.PName}</a></span>
									  		</div>
									  		<div class="info-item">
										  		<label class="lab"> Doctor Name  </label>
										  		<span class="inp"><a href="${baseUrl}/doctor/${diagnosis.doctorId}/details"> ${diagnosis.DName}</a></span>
										  	</div>

										  	<div class="info-item">
										  		<label class="lab">Blood Glucose </label>
										  		<span class="inp"> ${diagnosis.bloodGlucose}</span>
										  	</div>
										  	<div class="info-item">
										  		<label class="lab"> Blood Pressure </label>
										  		<span class="inp"> ${diagnosis.bloodPressure}</span>
										  	</div>
											<div class="info-item">
												<label class="lab"> Body Weight </label>
												<span class="inp"> ${diagnosis.bodyWeight}</span>
											</div>
											<div class="info-item">
												<label class="lab"> Body Mass Index </label>
												<span class="inp"> ${diagnosis.bodyMassIndex}</span>
											</div>

								  	</div>  
						  		</div>
						  		<div class="col-md-6">
						  			<div class="single-info">
						  			<p><strong> Images of Prescription </strong> </p>
						  			<c:forEach var="image" items="${fn:split(diagnosis.diagnosisPhoto, ',')}">
								  		<a href="${baseUrl}/files/image/${image}" target="_blank">
								  		<img style="height: 250px; width: 190px;" src="${baseUrl}/files/image/${image}" border="0" alt="documents"></a>
									</c:forEach>	
									</div>
								</div>
						  	</div>	
					  	</div>
					</div>
				</div>
			</div>
		</section>
	</div>


<%@include file="../fragments/footer.jsp"%>