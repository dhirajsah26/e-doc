h<!--Model PopUp -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- modal pop up -->
<div class="modal fade " id="patientInfoModal" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Update Patient Detail</h4>
            </div>
            <div class="modal-body">

                <form id="formPersonalDetails" method="post" class="form-horizontal" enctype="multipart/form-data">
                    <input id="patientInfoId" name="id" type="hidden" value="">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Name<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" autofocus class="form-control " name="name"
                                           placeholder="Enter Full Name" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">Gender </label>
                                <div class="col-sm-9">
                                    <div class="custom-control custom-radio">
                                        <input type="radio" checked name="gender" value="male">
                                        <label class="lab-radio" for="status">Male</label>
                                        <input type="radio" name="gender" value="female">
                                        <label class="lab-radio" for="status">Female</label>
                                        <input type="radio" name="gender" value="others">
                                        <label class="lab-radio" for="status">Others</label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">Date Of Birth<span
                                        class="mandatory">*</span> </label>
                                <div class="col-sm-9">
                                    <input class="form-control " id="dateOfBirth"
                                        type="date"
                                           name="dateOfBirthStr" placeholder="Date of Birth" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">Address<span class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control " name="address"
                                           placeholder="Enter Full Address" required>
                                </div>
                            </div>

                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Mobile Number<span
                                        class="mandatory">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control " name="mobileNumber"
                                           placeholder="Enter Mobile Number" required pattern="[789][0-9]{9}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">Land Line Number</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control " name="landlineNumber"
                                           placeholder="Enter Phone Number"
                                           pattern="^[0-9]{6}|[0-9]{8}|[0-9]{10}$">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">Image </label>
                                <div class="col-sm-9">

                                    <div class="">
                                        <input name="file1" id="patientImg" type="file">
                                        <small>Attachment should not be more than 4 MB in size.</small>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <!-- <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="material-icons"> clear </i>&nbsp;Close</button> -->
                <button type="button" class="btn btn-success" onclick="updatePersonalDetail(this)"><i
                        class="material-icons">
                        send </i>&nbsp;Update
                </button>
            </div>
        </div>
    </div>
</div>
<!--modal pop up ends here  -->
<script>

    function populatePatientIdAndProceed(instance, id) {  /* function name should added to link */
        $("#patientInfoId").val(id); /* hidden id */
        $("#patientInfoModal").modal("show");  /* modal id */
        showLoader();
        var path = baseUrl + "patient/fetchbyid";
        $.post(path, { id: id })
            .done(function (response) {
                if (response.success) {
                    let patient = response.body;
                    console.log(patient)

                    let formInstance = $("#formPersonalDetails");
                    formInstance.find("input[name=name]").val(patient.name);
                    formInstance.find("input[name=dateOfBirthStr]").val(patient.dateOfBirth);
                    formInstance.find("input[name=gender]").val(patient.gender);
                    formInstance.find("input[name=address]").val(patient.address);
                    formInstance.find("input[name=mobileNumber]").val(patient.mobileNumber);
                    formInstance.find("input[name=landlineNumber]").val(patient.landlineNumber);
                    let patientImageInstance = $("#patientImg");
                    if (patient.image) {
                        let imageSource = baseUrl + "files/" + image.image;
                        patientImageInstance.attr("src", imageSource);
                        patientImageInstance.show();
                    } else {
                        patientImageInstance.show();
                    }
                }

            })
            .fail(function (xhr, status, error) {
                alert(SOMETHING_WENT_WRONG);
            })
            .always(function () {
                hideLoader();
            });
    }

    function updatePersonalDetail(instance) {
        $("#formPersonalDetails").trigger("submit");
        console.log("hello");
    }

    $("form#formPersonalDetails").submit(function (e) {
        e.preventDefault();
        console.log(this);
        var formData = new FormData(this);
        console.log(formData);
        var url = baseUrl + "patient/personaldetails/save";

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
    });

</script>