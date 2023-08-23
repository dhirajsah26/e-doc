h<!--Model PopUp -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- modal pop up -->
<div class="modal fade " id="addSpecializationModal" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Specialization</h4>
            </div>
            <div class="modal-body">

                <form id="formAddSpecialization" method="post" class="form-horizontal" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">

                                <label class="col-sm-4 control-label"> Doctor </label>
                                <div class="col-sm-8">
                                    <select name="doctor_id" class="form-control init-select2 " required>
                                        <option value="">Select Doctor</option>
                                        <c:forEach var="doctor" items="${doctorList}">
                                            <option value="${doctor.id}">${doctor.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">

                                <label class="col-sm-4 control-label"> Specialization </label>
                                <div class="col-sm-8">
                                    <select name="specialization_id" class="form-control init-select2 "required>
                                        <option value="">Select Specialization</option>
                                        <c:forEach var="specialization" items="${specializationList}">
                                            <option value="${specialization.id}">${specialization.specializationName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <!-- <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons"> clear </i>&nbsp;Close</button> -->
                <button type="button" class="btn btn-success" onclick="addSpecialization(this)">
                    <i class="material-icons">save </i>&nbsp;Save
                </button>
            </div>
        </div>
    </div>
</div>
<!--modal pop up ends here -->
<script>

    function createDoctorSpecialization() {  /* function name should added to link */

        $("#addSpecializationModal").modal("show");  /* modal id */

    }

    function addSpecialization(instance) {
        if (!$("#formAddSpecialization").valid()){
            return;
        }

        var data = $("#formAddSpecialization").serialize();
        var url = baseUrl + "specialization/doctor/save";
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