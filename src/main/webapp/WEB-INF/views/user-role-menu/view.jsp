<%@ include file="../fragments/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- =============================================== -->

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Package Template Management <span><i
				class="fa fa-angle-double-right"></i> ${title}</span>
		</h1>
	</section>

	<!-- Main content -->
	<section class="content">
		<!-- Default box -->
		<div class="box">
			<div class="box-header with-border">
				<div class="action-btn">
					<a href="${baseUrl}/admin/package/template"
						class="btn btn-default btn-md pull-left"><i
						class="material-icons">arrow_back</i> Back</a>
					<div class="rounded-action-btn">
						<a
							href="${baseUrl}/admin/package/template/${packageTemplateInfo.id}/edit"
							class="btn btn-success btn-sm" target="_blank"
							data-toggle="tooltip" title="Edit"><i class="material-icons">edit</i></a>

						<a
							href="${baseUrl}/admin/package/template/${packageTemplateInfo.id}/delete"
							class="btn btn-danger btn-sm"
							onclick="return confirm('Are you sure you want to Delete ?')"
							data-toggle="tooltip" title="" data-original-title="Delete"><i
							class="material-icons">delete</i></a>
					</div>
				</div>
			</div>
			<div class="box-body">
				<input type="hidden" value="${packageTemplateInfo.id}" name="id"
					id="package-id" /> ${message}

				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#information"><i
							class="fa fa-user-circle-o"></i><span class="hidden-xs">Basic
								Information</span></a></li>
					<li><a data-toggle="tab" href="#package_templates"><i
							class="fa fa-th"></i><span class="hidden-xs">Selected
								Packages</span></a></li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane fade in active single-info" id="information">
						<div class="row">
							<div class="col-md-6">
								<div class="info-item">
									<label>Package Name</label> <span>${packageTemplateInfo.name}</span>
								</div>
								<div class="info-item">
									<label>Created Date</label> <span>${packageTemplateInfo.createdDate}</span>
								</div>
								<div class="info-item">
									<label>Created By</label> <span>${packageTemplateInfo.createdBy.username}</span>
								</div>
								<div class="info-item">
									<label>Modified Date</label> <span>${packageTemplateInfo.modifiedDate}</span>
								</div>
								<div class="info-item">
									<label>Modified By</label> <span>${packageTemplateInfo.modifiedBy.username}</span>
								</div>

								<div class="info-item">
									<label>Remarks</label> <span>${packageTemplateInfo.remarks}</span>
								</div>
							</div>
						</div>
					</div>

					<div id="package_templates" class="tab-pane fade">
						<table class="table table-bordered awesome-table"
							id="package-template">
							<thead>
								<tr>
									<th>S.N</th>
									<th>Module</th>
									<th>Sub Module</th>
									<th>Menu Line</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>

				</div>
			</div>
			<!-- /.box -->
		</div>

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<script type="text/javascript">
	$(document).ready(function() {
		populatepackagetemplates();
	});

	function populatepackagetemplates() {
		showLoader();

		var url = baseUrl
				+ "/admin/package/template/populatepackagetemplatesforview";
		var id = $("#package-id").val();

		$.post(url, {
			id : id
		}).done(function(response) {
			hideLoader();

			if (response.success)

				$("#package-template").find("tbody").html(response.body);

			if (response.message)
				alert(response.message);
		}).fail(function(xhr, status, error) {
			alert("Sorry but something went wrong.");
		}).always(
				function() {
					$(".btnUpdate").html(
							"<i class=\"material-icons\">send</i> Update");
					$(".btnUpdate").prop("disabled", false);
				})
	}
</script>

<%@ include file="../fragments/footer.jsp"%>