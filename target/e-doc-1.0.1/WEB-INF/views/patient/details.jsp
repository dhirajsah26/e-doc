<%@include file="../fragments/header.jsp" %>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="content-header">
        <h1> patient Management <span>
		 <i class="fa fa-angle-double-right"></i>Details
			</span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <a href="${baseUrl}/patient/" class="btn btn-success" role="button"><i
                        class="material-icons">arrow_back</i>Back</a>
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
                    <li><a data-toggle="tab" href="#menu1"><i class="material-icons"> book</i>&nbsp;Treatment
                        Details</a></li>
                    <li><a data-toggle="tab" href="#menu2"><i class="material-icons">account_balance</i>&nbsp;Diagnosis
                        Details</a></li>
                </ul>

                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="single-info">
                                    <div class="info-item align-center">
                                        <c:choose>
                                            <c:when test="${patient.image != null}">
                                                <a class="align-center" href="${baseUrl}/files/${patient.image}"
                                                   target="_blank">
                                                    <img width="200px;"
                                                         class="profile-user-img img-thumbnail center img-circle img-responsive"
                                                         src="${baseUrl}/files/image/${patient.image}" border="0"
                                                         alt="patient Image">
                                                </a>
                                            </c:when>

                                            <c:otherwise>
                                                <a class="align-center" href="${baseUrl}/files/default.jpeg"
                                                   target="_blank">
                                                    <img class="profile-user-img img-thumbnail center img-circle img-responsive"
                                                         src="${baseUrl}/files/default.jpeg" border="0"
                                                         alt="patient Image">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                            <%--<a class="align-center" href="${baseUrl}/files/default.jpeg"
                                               target="_blank">
                                                <img class="profile-user-img img-thumbnail center img-circle img-responsive"
                                                     src="${baseUrl}/files/default.jpeg" border="0"
                                                     alt="patient Image"></a>
--%>
                                    </div>
                                    <h3 class="text-center"><strong>${patient.name}</strong></h3>
                                 <%--
                                    <h5 class="text-center">${patient.jobTitle.jobTitle}</h5>--%>

                                    <div class="info-item">
                                        <label class="lab">Date Of Birth </label>
                                        <span class="inp">${patient.dateOfBirth}</span>
                                    </div>


                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="single-info">
                                    <div class="info-item">
                                        <label class="lab">Gender </label>
                                        <span class="inp">${patient.gender}</span>
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Mobile Number</label>
                                        <span class="inp">${patient.mobileNumber}</span>
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Phone Number</label>
                                        <span class="inp">${patient.landlineNumber}</span>
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Address</label>
                                        <span class="inp">${patient.address}</span>
                                    </div>

                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="single-info">

                                    <div class="info-item">
                                        <label class="lab">Modified Date</label>
                                        <span class="inp">${patient.modifiedDate}</span>
                                    </div>



                                </div>

                            </div>


                        </div>

                    </div>

                    <div id="menu1" class="tab-pane fade">


                        <table id="myAcademicTable" class="table table-bordered table-striped advanced_table fs-12">
                            <thead>
                            <tr><th>S.N.</th>
                                <th>Prescription FeedBack</th>
                                <th>Prescription date</th>
                                <th>Prescription Photo</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody> <% int k=1; %>

                            <c:forEach var="treatmentss" items="${treatmentByPatient}">
                                <tr>
                                    <td> <%= k++ %>.</td>
                                    <td>${treatmentss.prescriptionFeedback}</td>
                                    <td>${dateUtils.getFinalDate(treatmentss.dateOfPrescription,operationDateSetting, false, false)}</td>
                                    <td>${treatmentss.prescriptionPhoto}</td>
                                    <td class="btn-actions text-center">
                                        <div class="btn-actions-inner">
                                            <button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
                                            <ul class="dropdown-menu">
                                                <li><a type="button"  href="${baseUrl}/treatment/${treatmentss.id}/treatmentDetail"><i class="material-icons">details</i>Details</a></li>
                                                <li><a type="button" href="${baseUrl}/treatment/${treatmentss.id}/edit"><i class="material-icons">edit</i>Edit</a> </li>
                                                <li><a type="button" class="text-red"  href="${baseUrl}/treatment${treatmentss.id}/delete"><i class="material-icons">delete</i>Delete</a></li>
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
                                <th>Blood Pressure</th>
                                <th>Blood Glucose</th>
                                <th>Body Weight</th>
                                <th>Body Mass Index</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody> <% int j=1; %>
                            <c:forEach var="diagnosis" items="${daignosisDTO}">
                                <tr>
                                    <td> <%= j++ %>.</td>
                                    <td>${diagnosis.bloodPressure}</td>
                                    <td>${diagnosis.bloodGlucose}</td>
                                    <td>${diagnosis.bodyWeight}</td>
                                    <td>${diagnosis.bodyMassIndex}</td>
                                    <td class="btn-actions text-center">
                                        <div class="btn-actions-inner">
                                            <button type="button" class="btn btn-link" data-toggle="dropdown"><i class="material-icons hidden">add_circle</i><i class="material-icons icon_more">more</i></button>
                                            <ul class="dropdown-menu">
                                                <li><a type="button"  href="${baseUrl}/diagnosis/${diagnosis.id}/diagnosisDetail"><i class="material-icons">details</i>Details</a></li>
                                                <li><a type="button"  href="${baseUrl}/diagnosis/${diagnosis.id}/edit"><i class="material-icons">edit</i>Edit</a> </li>
                                                <li><a type="button" class="text-red"  href="${baseUrl}/diagnosis${diagnosis.id}/delete"><i class="material-icons">delete</i>Delete</a></li>

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
