<%@include file="../fragments/header.jsp" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1> Diagnosis Management <span> <i class="fa fa-angle-double-right"> </i>&nbsp;Edit Diagnosis Details</span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <div style="margin:20px; text-align: center;:center;"><h3>Edit Page</h3></div>
                <a href="${baseUrl}/diagnosis/" class="btn btn-success" role="button"><i class="material-icons">arrow_back</i>&nbsp;Back</a>
                <div class="table-tools"></div>
            </div>
            <!-- end of box-header div -->
            <div class="box-body">
                <ul class="nav nav-tabs" id="machineTabs">
                    <li class="active"><a data-toggle="tab" href="#accidentDetail"><i class="material-icons">library_books
                    </i>&nbsp;Diagnosis Details</a></li>
                </ul>
                <div class="tab-content">
                    <div id="accidentDetail" class="tab-pane fade in active">
                        <form id="diagnosisDetailForm" action="${baseUrl}/diagnosis/add" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="hidden" name="id" value="${diagnosisDTO.id}"/>
                                    <div class="info-item">
                                        <label class="lab">Patient Id.</label><br/>
                                        <select class="form-control inp" name="patient_id" id="patient-Name">
                                            <option class="form-control inp">Select Patient</option>
                                            <c:forEach var="patients" items="${patientList}">
                                                <option class="form-control inp"
                                                        <c:if test="${diagnosisDTO.patientId == patients.id}">selected</c:if>
                                                        value="${patients.id}">${patients.id}(${patients.name})
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="info-item">
                                        <label class="lab ">Blood Pressure</label>
                                        <input type="text" name="blood_pressure"
                                               value="${diagnosisDTO.bloodPressure}"
                                               class="form-control inp">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="info-item">
                                        <label class="lab">Blood Glucose</label>
                                        <input type="text" name="blood_glucose"
                                               value="${diagnosisDTO.bloodGlucose}"
                                               class="form-control inp ">
                                    </div>
                                    <div class="info-item">
                                        <label class="lab">Body Weight</label>
                                        <input type="text" name="body_weight" value="${diagnosisDTO.bodyWeight}"
                                               class="form-control inp">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="info-item">
                                        <label class="lab"> Body Mass Index</label>
                                        <input name="body_mass_index"  class="form-control inp" value="${diagnosisDTO.bodyMassIndex}" type="text">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="info-item">
                                        <label class="lab">Diagnosis Photo</label>
                                        <input name="diagnosis_photo" value="${diagnosisDTO.diagnosisPhoto}"
                                               class="file" multiple="multiple" type="file">

                                        <p class="help-block">Attachment should not be more than 1 MB in size.</p>
                                    </div>
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
