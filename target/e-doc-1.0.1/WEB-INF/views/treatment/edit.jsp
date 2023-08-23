<%@include file="../fragments/header.jsp" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1> Treatment Management <span> <i class="fa fa-angle-double-right"> </i>&nbsp;Edit Treatment Details</span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <div style="margin:20px; text-align: center;:center;"><h3>Edit Page</h3></div>
                <a href="${baseUrl}/treatment/" class="btn btn-success" role="button"><i class="material-icons">arrow_back</i>&nbsp;Back</a>
                <div class="table-tools"></div>
            </div>
            <!-- end of box-header div -->
            <div class="box-body">
                <ul class="nav nav-tabs" id="machineTabs">
                    <li class="active"><a data-toggle="tab" href="#accidentDetail"><i class="material-icons">library_books
                    </i>&nbsp;Treatment Details</a></li>
                </ul>
                <div class="tab-content">
                    <div id="accidentDetail" class="tab-pane fade in active">
                        <form id="treatmentDetailForm" action="${baseUrl}/treatment/add" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="hidden" name="id" value="${treatmentDetails.id}"/>
                                    <div class="info-item">
                                        <label class="lab">Patient Id.</label><br/>
                                        <select class="form-control inp" name="patient_id" id="patient-Name">
                                            <option class="form-control inp">Select Patient</option>
                                            <c:forEach var="patients" items="${patientList}">
                                                <option class="form-control inp"
                                                        <c:if test="${treatmentDetails.PId == patients.id}">selected</c:if>
                                                        value="${patients.id}">${patients.id}(${patients.name})
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="info-item">
                                        <label class="lab ">Date of prescription</label>
                                        <input type="date" name="prescribed_date"
                                               value="${treatmentDetails.dateOfPrescription}" class="form-control inp">
                                    </div>
                                </div>
                                <%--<div class="col-md-6">
                                    <div class="info-item">
                                        <label class="lab">Time</label>
                                        <input type="time" value="${accidentDetails.accident_time}" name="accident_time" class="form-control inp">
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Location</label>
                                        <input type="text" value="${accidentDetails.location}" name="location" class="form-control inp">
                                    </div>
                                </div>--%>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="info-item">
                                        <label class="lab">Prescription Image</label>
                                        <input name="prescription_image" value="${treatmentDetails.prescriptionPhoto}"
                                               class="file" multiple="multiple" type="file">

                                        <p class="help-block">Attachment should not be more than 1 MB in size.</p>
                                    </div>
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
                            <div class="row">
                                <div class="col-md-12">
                                    <label class="lab">Prescription FeedBack </label>
                                    <textarea name="accidentDetail" id="editor1" class="ckeditor form-control" rows="10"
                                              cols="80">
                                        ${treatmentDetails.prescriptionFeedback}
                                    </textarea>
                                </div>
                            </div>
                            <div class="lab">
                                <button type="submit" class="btn btn-primary">Update</button>
                            </div>
                        </form>
                    </div>
                    <!--End of Machine details Tabs  -->
                    <!--End of Bill Book And Insurance Details Tab  -->
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="../fragments/footer.jsp" %>
