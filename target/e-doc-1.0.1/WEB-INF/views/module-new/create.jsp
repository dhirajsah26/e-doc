<%@page import="com.example.edoc.enums.ModuleRole"%>
<%@ include file="../fragments/header.jsp" %>

<% pageContext.setAttribute("roles", ModuleRole.values()); %>

<!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        ${title}
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-pencil"></i> Module</a></li>
        <li class="active">Create</li>
      </ol>
    </section>
    
<!-- Main content -->
    <section class="content">
    	<div class="row">
      	<div class="col-md-6">
      		<!-- Default box -->
		      <div class="box">
		      	${message}
		        <div class="box-body">
		          <form class="form" action="${baseUrl}/admin/module/v2/create" method="post">
		          	<div class="form-group">
		        	  	<label>Role<span class="mandatory">*</span></label>
		        	  	<select name="role" class="form-control" onchange="handleRoleChange(this)" required>
		        	  		<c:forEach var="role" items="${roles}">
		        	  			<option>${role}</option>
		        	  		</c:forEach>
		        	  	</select>
		        	  </div>
		        	  
		        	<div class="form-group moduleBlock" style="display:none;">
		        		<label>Module<span class="mandatory">*</span></label>
		        		<select class="form-control module init-select2" name="moduleId"  onchange="filterModules('MODULE', false)">
		        			<option value="">Select Module</option>
		        		</select>
		        	</div>
		        	
		        	<div class="form-group subModuleBlock" style="display:none;">
		        		<label>Sub-Module<span class="mandatory">*</span></label>
		        		<select class="form-control subModule init-select2" name="subModuleId">
		        			<option value="">Select Sub Module</option>
		        		</select>
		        	</div>
		        	  
		          	<div class="form-group">
		          		<label>Name<span class="mandatory">*</span></label>
		          		<input maxlength="100" type="text" name="name" placeholder="Enter module name" class="form-control" required>
		          	</div>
		          	<div class="form-group">
		          		<label>Icon<span class="mandatory">*</span></label>
		          		<input type="text" name="icon" class="form-control" placeholder="Enter icon code" required>
		          	</div>
		          	<div class="form-group">
		          		<label>Rank<span class="mandatory">*</span></label>
		          		<input type="text" value="0" min="0" name="rank" class="form-control" placeholder="Enter rank" required>
		          	</div>
		          	
		          	<div class="form-group targetUrlBlock">
		          		<label>Target Url<span class="targetUrl mandatory" style="display:none;">*</span></label>
		          		<input type="text" name="targetUrl" class="form-control" placeholder="Enter module url">
		          	</div>
		          	<div class="form-group">
		          		<label>Permission Key<!-- <span class="mandatory">*</span> --></label>
		          		<input type="text" name="permissionKey" class="form-control" placeholder="Enter permission key">
		          	</div>
		          	<div class="form-group">
		          		<label>Is View Menu<span class="mandatory">*</span></label>
		                <label class="radio-inline"><input type="radio" name="viewMenu" value="1" checked> Yes</label>
		                <label class="radio-inline"><input type="radio" name="viewMenu" value="0"> No</label>
		            </div>
		          	<div class="form-group">
		          		<label>Status<span class="mandatory">*</span></label>
		                  <label class="radio-inline"><input type="radio" name="status" value="1" checked> Active</label>
		                  <label class="radio-inline"><input type="radio" name="status" value="0"> In-active</label>
		              </div>
		          	<div class="form-group">
		          		<label>Help Section</label>
		          		<textarea rows="6" class="form-control ckeditor" name="helpSection"></textarea>
		          	</div>
		          	<button type="submit" class="btn btn-primary">Save</button>
                	<a href="${baseUrl}/admin/module/v2/" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> Back</a>
		          </form>
		        </div>
		        <!-- /.box-body -->
		      </div>
		      <!-- /.box -->
		    </div>
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <script src="${baseUrl}/resources/dist/js/module-new.js"></script>
  
  <%@ include file="../fragments/footer.jsp" %>