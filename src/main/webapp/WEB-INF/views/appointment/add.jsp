

<%@include file="../fragments/header.jsp" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1> Appointment Management <span> <i class="fa fa-angle-double-right"></i> Add </span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <a href="${baseUrl}/appointment/" class="btn btn-default" role="button"><i class="material-icons">arrow_back</i>&nbsp;Back</a>

                <div class="table-tools"></div>
            </div>
            <div class="box-body">
                <form id="appointmentDetailForm" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="info-item">
                                <label class="lab">Patient</label><br/>
                                <select class="form-control inp " name="patientId" data-validation="required"
                                        data-validation-error-msg="Please select Patient" id="patient_id" required>
                                    <option class="form-control inp" value="">Select Patient</option>
                                    <c:forEach var="patientDetail" items="${patientList}">
                                        <option class="form-control inp" value="${patientDetail.id}">${patientDetail.id}(${patientDetail.name})</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="info-item">
                                <label class="lab">Doctor Specialization</label><br/>
                                <select class="form-control inp " onchange="onChangePopulateDoctor(this)" required name="specializationId" data-validation="required"
                                        data-validation-error-msg="Please select Doctor" id="specialization_id">
                                    <option class="form-control inp" value="">Select Specialization</option>
                                    <c:forEach var="specialization" items="${specializationList}">
                                        <option class="form-control inp" value="${specialization.id}">${specialization.id}(${specialization.specializationName})</option>

                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                        <div class="col-md-6">
                            <div class= "info-item">
                                <label class="lab ">Date</label>
                                <input type="date" required name="appointmentDate" data-validation="required" data-validation-error-msg="You did not enter a password" class="form-control inp">
                            </div>
                            <div class="info-item">
                                <label class="lab">Time</label>
                                <input type="time" name="appointmentTime" required data-validation="required" data-validation-error-msg="Please select machine" class="form-control inp timepicker">
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="info-item">
                                <label class="lab"> Description</label>
                                <input name="description" type="text" class="form-control inp">

                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="info-item">
                                <label class="lab">Doctor</label><br/>
                                <select class="form-control inp " name="doctorId" data-validation="required"
                                        data-validation-error-msg="Please select Doctor" id="doctor_id">
                                    <%--<option class="form-control inp" value="">Select Doctor</option>
                                    <c:forEach var="doctorList" items="${doctorList}">
                                        <option class="form-control inp" value="${doctorList.id}">${doctorList.id}(${doctorList.name})</option>
                                    </c:forEach>--%>
                                </select>
                            </div>
                        </div>

                    </div>
                    <div class="lab">
                        <button type="button" class="btn btn-primary " onclick="checkAvailability(this)">Save</button>
                    </div>

                </form>

            </div>
        </div>
    </section>
</div>
<script>


    function checkAvailability(instance){
        if (!$("#appointmentDetailForm").valid()){
            return;
        }
        var data = $("#appointmentDetailForm").serialize();
        console.log(data);
        var url = baseUrl + "appointment/checkAppointment";

        $.post(url, data)
            .done(function(response) {
                console.log(response);

                if (response.success) {
                    alert("The Date and time is already booked");

                }
                else {
                    saveAppointmentDetail();

                }
            })
            .fail(function(xhr, status, error) {
                alert("The Date and time is already booked");
            })
            .always(function() {
                //window.location.reload(true);

            })

    }

    $.validate({
        validateOnBlur: false, // disable validation when input looses focus
        errorMessagePosition: 'inline',// Instead of 'inline' which is default
        scrollToTopOnError: true // Set this property to true on longer forms
    });


    function saveAppointmentDetail(instance){
        var data = $("#appointmentDetailForm").serialize();

        var url = "${baseUrl}" + "/appointment/save";

        $.post(url, data)
            .done(function(response) {


                if (response.success) {
                    alert("Appointment has been placed !!!");
                    location.href="${baseUrl}" + "/appointment/";
                }
            })
            .fail(function(xhr, status, error) {
                alert("Something went wrong !!!");
            })
            .always(function() {
                //window.location.reload(true);

            })

    }

    function onChangePopulateDoctor(instance){
        var sp_id = $('#specialization_id').find(":selected").val();


        var url = "${baseUrl}" + "/appointment/getDoctorBySpecializationId";
        $.post(url, { specialization_id: sp_id})
            .done(function(response) {

                if (response.success) {

                    populateDoctorOptions(response.body);

                }
            })
            .fail(function(xhr, status, error) {
                alert("Something went wrong !!!");
            })
            .always(function() {
                //window.location.reload(true);

            })

    }
    function populateDoctorOptions(items) {
        var options = "<option value=''>Select Doctor</option>";
        if (items) {
            items.forEach(function(item) {
                options += "<option class=\"form-control inp\" value='"+ item.id +"'>"+ item.name +"</option>";
            })
        }
        $("select[name=doctorId]").html(options);

    }



</script>
<%@include file="../fragments/footer.jsp" %>

           