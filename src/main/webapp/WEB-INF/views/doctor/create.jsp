<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../fragments/header.jsp" %>h
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

                <a href="${baseUrl}/doctor/" class="btn btn-success" role="button"><i
                        class="material-icons">arrow_back</i>&nbsp;back</a>
            </div>
            <div class="box-body">
                <ul class="nav nav-tabs" id="myTabs">
                    <li class="active"><a data-toggle="tab" href="#home"><i class="material-icons">person</i>&nbsp;Personal
                        Details</a></li>


                </ul>
                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        <form id="formDoctorDetails" method="post" class="form-horizontal"
                              enctype="multipart/form-data">
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
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Date Of Practicing<span
                                                class="mandatory">*</span> </label>
                                        <div class="col-sm-9">
                                            <input class="form-control " id="dateOfPractising"
                                                   type="date"
                                                   name="practisingFromDate" placeholder="Date of Birth" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12">

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Professional Statement<span class="mandatory">*</span>
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control " name="professionalStatement"
                                                  required>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="">
                                <button type="button" class="btn btn-primary"
                                        onclick="saveOrUpdateDoctor(this)">Save
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

    $(function () {
        $("#myTabs").tab({show: [1, 2]});
    })



    function saveOrUpdateDoctor(instance) {
        $("#formDoctorDetails").trigger("submit");
    }

    $("form#formDoctorDetails").submit(function (e) {
        e.preventDefault();
        console.log(this);
        var formData = new FormData(this);
        console.log(formData);
        var url = baseUrl + "doctor/save";
        var indexUrl= baseUrl + "doctor/";

        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (response) {
                if (response.success) {
                    console.log(response.body);
                    window.location.href= indexUrl;
                    //switch to next tab

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