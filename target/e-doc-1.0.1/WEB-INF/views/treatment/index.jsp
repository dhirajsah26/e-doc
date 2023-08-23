<%@include file="../fragments/header.jsp"%>

<div class="content-wrapper">
    <section class="content-header">
        <h1> Treatment Management <span> <i class="fa fa-angle-double-right"></i> Detail </span>
        <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
	<section class="content" >
		<div class="box">
			<div class="box-header with-border">
	    		
		           	<a href="${baseUrl}/treatment/add"class="btn btn-success" role="button"><i class="material-icons">add</i>&nbsp;Add treatment</a>
				<div class="table-tools"></div>	     
            </div>
			<div class="box-body">
				<table id="myTable" class="table table-bordered table-striped advanced_table">
								<thead>
									<tr>
										<th>S.N</th>

										<th>Patient Name</th>
										<th>Doctor Name</th>
										<th>Prescribed Date</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<% int i=1; %>
									<c:forEach items="${treatmentDetail}" var="treatmentDetails">
										<tr>
											<td><%= i++ %></td>

											<td><a href="${baseUrl}/patient/${treatmentDetails.PId}/details"> ${treatmentDetails.patientName}</a></td>
											<td><a href="${baseUrl}/patient/${treatmentDetails.doctorId}/details">${treatmentDetails.DName}</td>
											<td>${treatmentDetails.dateOfPrescription}</td>
											<td class="btn-actions text-center">
												<div class="btn-actions-inner">
													<button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more_horiz</i></button>
													<ul class="dropdown-menu" style="transform: translateY(0px);">
												 		<li><a type="button"  href="${baseUrl}/treatment/${treatmentDetails.id}/treatmentDetail"><i class="fa fa-info" aria-hidden="true"></i>Details</a></li>
												      	<li><a type="button"  href="${baseUrl}/treatment/${treatmentDetails.id}/edit" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i>Edit</a></li>
												       	<li><a type="button" class="text-red"  onclick="deleteTreatment(${treatmentDetails.id})"><i class="fa fa-trash-o"></i>Delete</a></li>
												    </ul>
												</div>
											</td>   
										</tr>
									</c:forEach>
								</tbody>
							</table>
			</div>
		</div>
	</section>
</div>
<script>
	
	function deleteTreatment(id) {

        var msg = confirm("Are you sure you want to delete?");
        if (msg) {

            var url = baseUrl + 'treatment/delete';
            $.post(url, {id: id})
                .done(function (response) {
                    console.log(response);
                    location.reload();
                    if (response.message) alert(response.message);
                })
                .fail(function (xhr, status, error) {
                    alert("Sorry but something went wrong.");
                })
                .always(function () {
                	location.reload();
                });

        }

    }
	</script>



<%@include file="../fragments/footer.jsp"%>
			 