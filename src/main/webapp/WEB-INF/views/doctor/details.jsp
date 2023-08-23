<%@include file="../fragments/header.jsp" %>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="content-header">
        <h1> Doctor Management <span>
		 <i class="fa fa-angle-double-right"></i>Details
			</span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <a href="${baseUrl}/doctor/" class="btn btn-success" role="button"><i
                        class="material-icons">arrow_back</i>Back</a>
                <a href="${baseUrl}/appointment/add/" class="btn btn-success" role="button"><i
                        class="material-icons">add</i> Make Appointment</a>
                <div class="rounded-action-btn">
                    <a data-toggle="tooltip" title="Delete" href="###" class="btn btn-danger" role="button"><i
                            class="material-icons">delete_sweep</i></a>
                </div>
                <div class="rounded-action-btn">
                    <a data-toggle="tooltip" title="Edit" href="###" class="btn btn-success" role="button"><i
                            class="material-icons">edit</i></a>
                </div>
                <div class="rounded-action-btn">
                    <a data-toggle="tooltip" title="Assign Payroll" href="###" class="btn btn-primary" role="button"><i
                            class="material-icons">payment</i></a>
                </div>
            </div>

            <div class="box-body">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home"><i class="material-icons">person </i>&nbsp;Personal
                        Details</a></li>
                    <li><a data-toggle="tab" href="#menu1"><i class="material-icons">book</i>Qualification Details&nbsp;</a></li>
                    <li><a data-toggle="tab" href="#menu2"><i class="material-icons">account_balance </i>&nbsp;Hospital Affiliation
                        Details</a></li>

                </ul>

                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="single-info">

                                    <h3 class="text-center lab" ><strong>${doctor.name}</strong></h3>

                                    <div class="info-item">
                                        <label class="lab">Practicing From </label>
                                        <span class="inp">${doctor.practicingFromDate}</span>
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Professional Statement </label>
                                        <span class="inp">${doctor.professionalStatement}</span>
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Professional Statement </label>
                                    <c:forEach var="doctorSpecializationList" items="${doctorSpecialization}">

                                            <span class="inp">${doctorSpecializationList.specializationName}</span>

                                    </c:forEach>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>

                    <div id="menu1" class="tab-pane fade">


                        <table id="myAcademicTable" class="table table-bordered table-striped advanced_table fs-12">
                            <thead>
                            <tr><th>S.N.</th>
                                <th>Qualification Name</th>
                                <th>Institute Name</th>
                                <th>Procurement year </th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody> <% int k=1; %>

                            <c:forEach var="doctorQualificationDTO" items="${doctorQualificationDTOList}">
                                <tr>
                                    <td> <%= k++ %>.</td>
                                    <td>${doctorQualificationDTO.qualificationName}</td>
                                    <td>${doctorQualificationDTO.instituteName}</td>
                                    <td>${dateUtils.getFinalDate(doctorQualificationDTO.procurementYear,operationDateSetting, false, false)}</td>
                                    <td class="btn-actions text-center">
                                        <div class="btn-actions-inner">
                                            <button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
                                            <ul class="dropdown-menu">

                                                <li><a type="button" class="text-red" onclick="deleteQualification(${doctorQualificationDTO.id})" ><i class="material-icons">delete</i>Delete</a></li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>



                    </div>
                    <div id="menu2" class="tab-pane fade">

                        <table id="myBankTable" class="table table-bordered table-striped advanced_table fs-12">
                            <thead>
                            <tr><th>S.N.</th>
                                <th>Hospital Name</th>
                                <th>City</th>
                                <th>Country </th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody> <% int j=1; %>
                            <c:forEach var="doctorHospitalDTO" items="${doctorHospitalDTOList}">
                                <tr>
                                    <td> <%= j++ %>.</td>
                                    <td>${doctorHospitalDTO.hospitalName}</td>
                                    <td>${doctorHospitalDTO.city}</td>
                                    <td>${doctorHospitalDTO.country}</td>
                                    <td>${doctorHospitalDTO.startDate}</td>
                                    <td>${doctorHospitalDTO.endDate}</td>
                                    <td class="btn-actions text-center">
                                        <div class="btn-actions-inner">
                                            <button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
                                            <ul class="dropdown-menu">
                                                 <li><a type="button" class="text-red"  onclick="deleteHospital(${doctorHospitalDTO.id})"><i class="material-icons">delete</i>Delete</a></li>

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
<%@include file="../fragments/footer.jsp" %>

<script>
    function deleteHospital(id) {

        var msg = confirm("Are you sure you want to delete?");
        if (msg) {

            var url = baseUrl + 'doctor/hospital/delete';
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

    function deleteQualification(id) {

        var msg = confirm("Are you sure you want to delete?");
        if (msg) {

            var url = baseUrl + 'doctor/qualification/delete';
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