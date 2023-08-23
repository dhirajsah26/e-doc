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
		          <form class="form" action="${baseUrl}/admin/module/v2/${module.id}/edit" method="post">
		          	<div class="form-group">
		        	  	<label>Role<span class="mandatory">*</span></label>
		        	  	<select name="role" class="form-control" onchange="handleRoleChange(this)" required>
		        	  		<c:forEach var="role" items="${roles}">
		        	  			<option <c:if test="${role == module.role}">selected</c:if>>${role}</option>
		        	  		</c:forEach>
		        	  	</select>
		        	  </div>
		        	  
		        	<div class="form-group moduleBlock" <c:if test="${module.role == 'MODULE'}">style='display:none;'</c:if>>
		        		<label>Module<span class="mandatory">*</span></label>
		        		<select class="form-control module init-select2" name="moduleId"  onchange="filterModules('MODULE', false)" <c:if test="${module.role != 'MODULE'}">required</c:if>>
		        			<option value="">Select Module</option>
		        			<c:forEach var="m" items="${modules}">
		        				<option <c:if test="${m.id == (module.role != 'SUB_MODULE' ? module.parentModule.parentModule.id : module.parentModule.id)}">selected</c:if> value="${m.id}">${m.name}</option>
		        			</c:forEach>
		        		</select>
		        	</div>
		        	
		        	<div class="form-group subModuleBlock" <c:if test="${module.role != 'MENU_LINE'}">style='display:none;'</c:if>>
		        		<label>Sub-Module<span class="mandatory">*</span></label>
		        		<select class="form-control subModule init-select2" name="subModuleId" <c:if test="${module.role == 'MENU_LINE'}">required</c:if>>
		        			<option value="">Select Sub Module</option>
		        			<c:forEach var="subModule" items="${subModules}">
		        				<option <c:if test="${subModule.id == module.parentModule.id}">selected</c:if> value="${subModule.id}">${subModule.name}</option>
		        			</c:forEach>
		        		</select>
		        	</div>
		        	  
		          	<div class="form-group">
		          		<label>Name<span class="mandatory">*</span></label>
		          		<input maxlength="100" type="text" name="name" placeholder="Enter module name" value="${module.name}" class="form-control" required>
		          	</div>
		          	<div class="form-group">
		          		<label>Icon<span class="mandatory">*</span></label>
		          		<input type="text" value="${module.icon}" name="icon" class="form-control" placeholder="Enter icon code" required>
		          	</div>
		          	<div class="form-group">
		          		<label>Rank<span class="mandatory">*</span></label>
		          		<input type="text" value="${module.rank}" min="0" name="rank" class="form-control" placeholder="Enter rank" required>
		          	</div>
		          	<div class="form-group targetUrlBlock">
		          		<label>Target Url<span class="targetUrl mandatory" <c:if test="${module.role != 'MENU_LINE'}">style='display:none;'</c:if>>*</span></label>
		          		<input type="text" value="${module.targetUrl}" name="targetUrl" class="form-control" placeholder="Enter module url" ${module.role == 'MENU_LINE' ? 'required' : ''}>
		          	</div>
		          	<div class="form-group">
		          		<label>Permission Key<!-- <span class="mandatory">*</span> --></label>
		          		<input type="text" name="permissionKey" value="${module.permissionKey}" class="form-control" placeholder="Enter permission key">
		          	</div>
		          	<div class="form-group">
		          		<label>Is View Menu<span class="mandatory">*</span></label>
		                <c:choose>
		                	<c:when test="${module.viewMenu}">
		                		<label class="radio-inline"><input type="radio" name="viewMenu" value="1" checked> Yes</label>
		                		<label class="radio-inline"><input type="radio" name="viewMenu" value="0"> No</label>
		                	</c:when>
		                	<c:otherwise>
		                		<label class="radio-inline"><input type="radio" name="viewMenu" value="1"> Yes</label>
		                		<label class="radio-inline"><input type="radio" name="viewMenu" value="0" checked> No</label>
		                	</c:otherwise>
		                </c:choose>
		              </div>
		          	<div class="form-group">
		          		<label>Status<span class="mandatory">*</span></label>
		                <c:choose>
		                	<c:when test="${module.status}">
		                		<label class="radio-inline"><input type="radio" name="status" value="1" checked> Active</label>
		                		<label class="radio-inline"><input type="radio" name="status" value="0"> De-active</label>
		                	</c:when>
		                	<c:otherwise>
		                		<label class="radio-inline"><input type="radio" name="status" value="1"> Active</label>
		                		<label class="radio-inline"><input type="radio" name="status" value="0" checked> In-active</label>
		                	</c:otherwise>
		                </c:choose>
		              </div>
		          	<div class="form-group">
		          		<label>Help Section</label>
		          		<textarea rows="6" class="form-control ckeditor" name="helpSection">${module.helpSection}</textarea>
		          	</div>
		          	<button type="submit" class="btn btn-primary">Update Module</button>
                	<a href="${baseUrl}/admin/module/v2/" class="btn btn-default"><i class="glyphicon glyphicon-arrow-left"></i> Back to List</a>
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