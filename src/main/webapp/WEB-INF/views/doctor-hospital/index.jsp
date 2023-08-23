<%--
  Created by IntelliJ IDEA.
  User: Dhiraj
  Date: 6/22/2020
  Time: 4:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../fragments/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="content-header">
        <h1> Doctor Hospital Management <span>
		 <i class="fa fa-angle-double-right"></i>Doctor's Hospital Detail Details
			</span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <a onclick="createDoctorSpecialization()" class="btn btn-success" role="button"><i class="material-icons">add</i>&nbsp;Add Doctor's Affiliated Hospital</a>
                <div class="table-tools"></div>
            </div>
            <div class="box-body">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home"><i class="material-icons">person </i>&nbsp; Doctor's Hospitals Details</a></li>
                </ul>

                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        ${message}
                        <table id="myEmployeeTable" class="table table-bordered table-striped advanced_table fs-12">
                            <thead>
                            <tr> <th>S.N.</th>
                                <th>Doctor Name</th>
                                <th>Hospital Name</th>
                                <th>City</th>
                                <th>Country</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody> <% int i=1; %>
                            <c:forEach var="doctorHospitalDTO" items="${doctorHospitalDTOList}">
                                <tr>
                                    <td><%= i++ %>.</td>
                                    <td>${doctorHospitalDTO.doctorName}</td>
                                    <td>${doctorHospitalDTO.hospitalName}</td>
                                    <td>${doctorHospitalDTO.city}</td>
                                    <td>${doctorHospitalDTO.country}</td>
                                    <td>${doctorHospitalDTO.startDate}</td>
                                    <td>${doctorHospitalDTO.endDate}</td>

                                    <td class="btn-actions text-center">
                                        <div class="btn-actions-inner">
                                            <button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
                                            <ul class="dropdown-menu">
                                                <li><a type="button" class="text-red"  onclick="deleteHospital(${doctorHospitalDTO.id.longValue()})"><i class="material-icons">delete</i>Delete</a></li>
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

    function deleteHospital(id) {
        console.log(id);
        var msg = confirm("Are you sure you want to delete?");
        if (msg) {

            var url = baseUrl + 'doctor/hospital/delete';
            $.post(url, {id: id})
                .done(function (response) {
                    console.log(response);

                    if (response.message)

                        alert(response.message);
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

<%@include file="modal-add-doctor-hospital.jsp" %>
<%@include file="../fragments/footer.jsp"%>
