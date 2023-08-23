
<%@include file="../fragments/header.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<h1> Settings <span> <i class="fa fa-angle-double-right"></i>
				settings
			</span> <span class="float-right help-section"><i
				class="material-icons">help</i></span>
		</h1>
	</section>
	<section class="content">
		<div class="box">
			<div class="box-header with-border">
			<label>OPERATION DATE SETTING</label>

				<div class="table-tools"></div>
			</div>
			<div class="box-body"></div>

		 <form action="${baseUrl}/settings/save" method="post" class="form-horizontal">

				<div class="row">
					<div class="col-md-6">
						<div class="single-info">
							<div class="form-group">
								<label class="col-sm-3 control-label">OPERATION DATE
									SETTING</label><br>
								<br>
								<div class="col-sm-9">
									<div class="custom-control custom-radio">
										<%--@declare id="status"--%>
										<input type="radio" ${setting.operationDateSetting == 'en' ? 'checked' : ''} name="operationDateSetting"
											value="en"> <label class="lab-radio" for="status">English
											Date</label> &nbsp;&nbsp;&nbsp; 
											
										<input type="radio"
											name="operationDateSetting" ${setting.operationDateSetting == 'np' ? 'checked' : ''} value="np"> <label
											class="lab-radio" for="status">Nepali Date</label>
									</div>
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-2 control-label">Assign Package</label>  
								<div class="col-sm-3"> 
									<select class="form-control init-select2" name="packageTemplateInfoId">
										<option value="">Select Package</option>
										<c:forEach var="p" items="${packages}">
											<option ${setting.packageTemplateInfo.id == p.id ? 'selected' : ''} value="${p.id}">${p.packageTemplateName}</option> 
										</c:forEach>
										<%-- <c:forEach var="package" items="${packages}">
											<option value="${package.id}">${package.name}</option>
										</c:forEach> --%>
									</select>
								</div>
							</div>


							<button type="submit" class="btn btn-success btn-sm">Update
								Settings</button>
						</div>
					</div>
					<div class="col-md-6"></div>
				</div>

			</form>			
		</div>
	</section>
</div>
<%@include file="../fragments/footer.jsp"%>


