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
        <h1> Doctor Qualification Management <span>
		 <i class="fa fa-angle-double-right"></i>Doctor Qualification Details
			</span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <a onclick="createDoctorQualification()" class="btn btn-success" role="button"><i class="material-icons">add</i>&nbsp;Add Doctor's Qualification</a>
                <div class="table-tools"></div>
            </div>
            <div class="box-body">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home"><i class="material-icons">person </i>&nbsp;Qualification Details</a></li>
                </ul>

                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        ${message}
                        <table id="myEmployeeTable" class="table table-bordered table-striped advanced_table fs-12">
                            <thead>
                            <tr> <th>S.N.</th>
                                <th>Doctor Name</th>
                                <th>Qualification Name</th>
                                <th>Institute Name</th>
                                <th>Procurement year </th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody> <% int i=1; %>
                            <c:forEach var="doctorQualificationDTO" items="${doctorQualificationDTOList}">
                                <tr>
                                    <td><%= i++ %>.</td>
                                    <td>${doctorQualificationDTO.doctorName}</td>
                                    <td>${doctorQualificationDTO.qualificationName}</td>
                                    <td>${doctorQualificationDTO.instituteName}</td>
                                    <td>${doctorQualificationDTO.procurementYear}</td>


                                    <td class="btn-actions text-center">
                                        <div class="btn-actions-inner">
                                            <button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
                                            <ul class="dropdown-menu">
                                                <li><a type="button" class="text-red"  onclick="deleteQualification(${doctorQualificationDTO.id})"><i class="material-icons">delete</i>Delete</a></li>
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

<%@include file="modal-add-doctor-qualifiaction.jsp" %>
<%@include file="../fragments/footer.jsp"%>
