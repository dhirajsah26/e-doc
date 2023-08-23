<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../fragments/header.jsp" %>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="content-header">
        <h1> Patient Management <span> <i class="fa fa-angle-double-right"></i>
                Add patient          </span>
            <span class="float-right help-section"><i class="material-icons">help</i></span></h1>
    </section>

    <section class="content">
        <div class="box">
            <div class="box-header with-border">

                <a href="${baseUrl}/patient/" class="btn btn-success" role="button"><i class="material-icons">arrow_back</i>&nbsp;back</a>
            </div>
            <div class="box-body">
                <ul class="nav nav-tabs" id="myTabs">
                    <li class="active"><a data-toggle="tab" href="#home"><i class="material-icons">person</i>&nbsp;Personal
                        Details</a></li>

                </ul>
                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        <form id="formPersonalDetails" method="post" class="form-horizontal"
                              enctype="multipart/form-data">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Name<span class="mandatory">*</span>
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" autofocus class="form-control " name="name"
                                                   placeholder="Enter Full Name" data-validation="required" data-validation-error-msg="You did not enter a password">
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
                                            <input class="form-control datepicker" id="dateOfBirth"
                                                   onload="initDatePicker(this,'${operationDateSetting}', null)"
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
                                                <input name="file1" type="file">
                                                <small>Attachment should not be more than 4 MB in size.</small>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="">
                                <button type="button" class="btn btn-primary"
                                        onclick="saveOrUpdatePersonalDetails(this)">Save
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- /.box-body -->
        </div>
    </section>
</div>
<!-- /.content-wrapper -->

<script>


    function saveOrUpdatePersonalDetails(instance) {
        $("#formPersonalDetails").trigger("submit");
    }

    $("form#formPersonalDetails").submit(function (e) {
        e.preventDefault();
        console.log(this);
        var formData = new FormData(this);
        console.log(formData);
        var url = baseUrl + "patient/personaldetails/save";
        var indexUrl= baseUrl+"patient/"

        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (response) {
                if (response.success) {
                    console.log(response.body);
                   location.href=indexUrl;
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

<%@include file="../fragments/footer.jsp" %>