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
                                <label class="col-sm-3 control-label">Specialization Name<span
                                        class="mandatory">*</span>
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" autofocus class="form-control required" name="specializationName"
                                           required>
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

    function createSpecialization() {  /* function name should added to link */

        $("#addSpecializationModal").modal("show");  /* modal id */

    }

    function addSpecialization(instance) {
        if (!$("#formAddSpecialization").valid()){
            return;
        }

        var data = $("#formAddSpecialization").serialize();
        var url = baseUrl + "specialization/save";
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

  /*  $("form#formAddSpecialization").submit(function (e) {
        e.preventDefault();
        console.log(this);
        var formData = new FormData(this);
        console.log(formData);
        var url = baseUrl + "specialization/save";

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