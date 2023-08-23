h<!--Model PopUp -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- modal pop up -->
<div class="modal fade " id="addHospitalModal" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Hospital</h4>
            </div>
            <div class="modal-body">

                <form id="formAddHospital" method="post" class="form-horizontal" enctype="multipart/form-data">
                    <div class="row">
                        <c:choose>
                            <c:when test="${Heading == 'admin'}">

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label"> Doctor </label>
                                        <div class="col-sm-8">
                                            <select name="doctor_id" class="form-control init-select2 required">
                                                <option value="">Select Doctor</option>
                                                <c:forEach var="doctor" items="${doctorList}">
                                                    <option value="${doctor.id}">${doctor.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                            </c:when>

                        </c:choose>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Hospital Name<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" autofocus class="form-control required" name="hospitalName"
                                           required>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">City<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" autofocus class="form-control required" name="city"
                                           required>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Country<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" autofocus class="form-control " name="country"
                                           required>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Start Date<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="date" autofocus class="form-control " name="startDate"
                                           required>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">End Date<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="date" autofocus class="form-control " name="endDate"
                                           required>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <!-- <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons"> clear </i>&nbsp;Close</button> -->
                <button type="button" class="btn btn-success" onclick="addHospital(this)">
                    <i class="material-icons">save </i>&nbsp;Save
                </button>
            </div>
        </div>
    </div>
</div>
<!--modal pop up ends here -->
<script>

    function createDoctorSpecialization() {  /* function name should added to link */

        $("#addHospitalModal").modal("show");  /* modal id */

    }

    function addHospital(instance) {
 if (!$("#formAddHospital").valid()){
     return;
 }
        var data = $("#formAddHospital").serialize();
        var url = baseUrl + "doctor/hospital/save";
        $.post(url, data)
            .done(function(response) {
                console.log(response);

                if (response.success) {
                    location.reload();
                    alert(response.message);
                }
            })
            .fail(function(xhr, status, error) {
                if (response.message)
                    alert(response.message);
                /*alert("The Date and time is already booked");*/
            })
            .always(function() {
                //window.location.reload(true);

            })
    }



</script>