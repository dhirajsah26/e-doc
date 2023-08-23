<%@include file="../fragments/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
<section class="content-header">
		<h1> Doctor Management <span>
		 <i class="fa fa-angle-double-right"></i>Doctor Details
			</span>
		<span class="float-right help-section"><i class="material-icons">help</i></span></h1>
	</section>
    <section class="content">
        <div class="box">
           <div class="box-header with-border">

				<div class="table-tools"></div> 
		   </div>
        	<div class="box-body">
				<ul class="nav nav-tabs">
					  <li class="active"><a data-toggle="tab" href="#home"><i class="material-icons">person </i>&nbsp;Doctor Details</a></li>
					</ul>
		
				<div class="tab-content">
					<div id="home" class="tab-pane fade in active">

						${message}
							<table id="myEmployeeTable" class="table table-bordered table-striped advanced_table fs-12">
							<thead>
									<tr> <th>S.N.</th>
										<th>Name</th>
										<th>Practising From Date</th>
										<th>Specialization Name</th>
										<th>Action</th>
									</tr>
							</thead>
							<tbody> <% int i=1; %>

								<c:forEach var="doctorlist" items="${doctorList}">
									<tr>
										<td><%= i++ %>.</td>
										<td>${doctorlist.name}</td>
										<td>${doctorlist.practicingFromDate}</td>
										<td>${doctorlist.specializationName}</td>
										<td class="btn-actions text-center">
											<div class="btn-actions-inner">
											<button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
										 <ul class="dropdown-menu">
										 <li><a type="button"  href="${baseUrl}/doctor/${doctorlist.id}/details"><i class="material-icons">details</i>Details</a></li>
											 <li><a type="button" style="display:${none} " href="${baseUrl}/appointment/add/"><i class="material-icons">add</i>Make Appointment</a></li>
											<li><a type="button" style="display:${none}" onclick="populateDoctorIdAndProceed(this, ${doctorlist.id})"><i class="material-icons">edit</i>Edit</a> </li>
											<li><a type="button" class="text-red"  onclick="deleteDoctor(${doctorlist.id})"><i class="material-icons">delete</i>Delete</a></li>
											 </ul>
										 </div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
        <!-- /.box-body -->
      </div>
</section>
</div>
<!-- /.content-wrapper -->

 <script type="text/javascript">

	 function deleteDoctor(id) {

		 var msg = confirm("Are you sure you want to delete?");
		 if (msg) {

			 var url = baseUrl + 'doctor/delete';
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

<%@include file="modal-edit-doctorDetails.jsp" %>
<%@include file="../fragments/footer.jsp" %>
