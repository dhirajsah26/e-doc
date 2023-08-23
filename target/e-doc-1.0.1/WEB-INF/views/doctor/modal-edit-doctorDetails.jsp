h<!--Model PopUp -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- modal pop up -->
<div class="modal fade " id="doctorInfoModal" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Update Doctor Detail</h4>
            </div>
            <div class="modal-body">

                <form id="formPersonalDetails" method="post" class="form-horizontal" enctype="multipart/form-data">
                    <input id="doctorInfoId" name="id" type="hidden" value="">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Name<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" autofocus class="form-control " name="name"
                                           placeholder="Enter Full Name" required>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Practicing From Date<span
                                        class="mandatory">*</span> </label>
                                <div class="col-sm-9">
                                    <input class="form-control " id="dateOfBirth"
                                          type="date"
                                           name="practisingFromDate" placeholder="Date of Birth" required>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Professional Statement<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control " name="professionalStatement"
                                           placeholder="Enter Full Address" required>
                                </div>
                            </div>
                        </div>
                    </div>

            </form>

        </div>
        <div class="modal-footer">
            <!-- <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons"> clear </i>&nbsp;Close</button> -->
            <button type="button" class="btn btn-success" onclick="updateDoctorDetail(this)"><i
                    class="material-icons">
                send </i>&nbsp;Update
            </button>
        </div>
    </div>
</div>
</div>
<!--modal pop up ends here -->
<script>

    function populateDoctorIdAndProceed(instance, id) {  /* function name should added to link */
        $("#doctorInfoId").val(id); /* hidden id */
        $("#doctorInfoModal").modal("show");  /* modal id */
        showLoader();
        var path = baseUrl + "doctor/fetchbyid";
        $.post(path, {id: id})
            .done(function (response) {
                if (response.success) {
                    let doctor = response.body;
                    console.log(doctor)

                    let formInstance = $("#formPersonalDetails");
                    formInstance.find("input[name=name]").val(doctor.name);
                    formInstance.find("input[name=practisingFromDate]").val(doctor.practicingFromDate);
                    formInstance.find("input[name=professionalStatement]").val(doctor.professionalStatement);

                }

            })
            .fail(function (xhr, status, error) {
                alert(SOMETHING_WENT_WRONG);
            })
            .always(function () {
                hideLoader();
            });
    }

    function updateDoctorDetail(instance) {


        var data = $("#formPersonalDetails").serialize();
        var url = baseUrl + "doctor/save";
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

   /* $("form#formPersonalDetails").submit(function (e) {
        e.preventDefault();
        console.log(this);
        var formData = new FormData(this);
        console.log(formData);


        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (response) {
                if (response.success) {
                    location.reload();
                    alert(response.message);
                } else {
                    if (response.message)
                        alert(response.message);
                }
            },
            error: function () {
                alert("Sorry but something went wrong.");
            },
            cache: false,
            contentType: false,
            processData: false
        }).always(function () {

        });
    });*/

</script>