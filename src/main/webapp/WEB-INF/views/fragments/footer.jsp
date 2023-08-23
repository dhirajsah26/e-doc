<footer class="main-footer">
    <div class="row">
        <div class="col-md-5">
            <strong>Copyright &copy; 2020 <a target="_blank" href="#">e Doc
                </a>.</strong> All rights reserved.
        </div>
        <div class="col-md-4 version">
            <b></b> 1.0.1
        </div>
    </div>
</footer>

<!-- ./wrapper -->


<script src="${pageContext.request.contextPath}/resources/dist/js/Chart.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/dist/js/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/dist/js/sparkline.js"></script>

<script src="${pageContext.request.contextPath}/resources/dist/js/jquery.knob.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/dist/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath}/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- SlimScroll -->
<script src="${pageContext.request.contextPath}/resources/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>

<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/resources/dist/js/adminlte.js"></script>

 <!--  validation  -->
 <!-- <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script> -->
 <script type="text/javascript" src="${baseUrl}/resources/plugins/validation/dist/jquery.validate.min.js"></script>
 <script type="text/javascript" src="${baseUrl}/resources/plugins/validation/dist/additional-methods.min.js"></script>

<!-- DataTables with exporting -->
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/dataTables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/dataTables.buttons.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/buttons.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/jszip.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/pdfmake.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/vfs_fonts.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/buttons.html5.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/buttons.print.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/dataTable/js/dataTables.fixedColumns.min.js"></script>				

<!-- switch -->
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/plugins/switch/lib/ToggleSwitch.css"/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/plugins/switch/lib/ToggleSwitch.js"></script>

<%--<!-- Nepali Date Picker -->
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/plugins/nepali-datepicker/nepali.datepicker.v2.2.min.css"/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/plugins/nepali-datepicker/nepali.datepicker.v2.2.min.js"></script>--%>

<!-- English Date Picker -->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/bower_components/bootstrap-datepicker/css/bootstrap-datepicker.min.css"/>

<!-- ckeditor -->
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/ckeditor/ckeditor.js"></script> --%>
<!-- <script src="https://cdn.ckeditor.com/4.12.0/full/ckeditor.js"></script> -->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/plugins/ckeditor_4.12.0_full/ckeditor/ckeditor.js"></script>

<!-- loader -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/dist/js/custom-loader.js"></script>

<!-- Date picker-->
     <script src="${baseUrl}/resources/dist/js/date-picker-setting.js"></script>  
     
 <!--dateutils.js -->
<script src="${baseUrl}/resources/dist/js/date-utils.js"></script>

<!-- iCheck -->
<script src="${pageContext.request.contextPath}/resources/plugins/iCheck/icheck.min.js"></script>

<!-- helper.js -->
<script src="${pageContext.request.contextPath}/resources/dist/js/helper.js"></script>

<!-- strings.js -->
<script src="${pageContext.request.contextPath}/resources/dist/js/strings.js"></script>

<!-- menuFix.js -->
<script src="${pageContext.request.contextPath}/resources/dist/js/menuFix.js"></script>


<noscript>
    <meta http-equiv="refresh" content="0;url='${pageContext.request.contextPath}/javascript/blocked'">
</noscript>


<script type="text/javascript">
	$(document).ready(function() {
		//responsive table    
	    $('.dataTables_wrapper .dataTable').wrap( "<div class='table-responsive'></div>" );
		initDataTable(".advanced_table");

		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	        $($.fn.dataTable.tables(true)).DataTable()
	           .columns.adjust()
	           .responsive.recalc();
	    });
	});
	

	// Tooltips Initialization
	$(function () {
	$('[data-toggle="tooltip"]').tooltip()
	})

$(function(){


$("a[href='"+ requestPath +"']").parent("li").addClass("active current");

$(".sidebar-menu li.active").closest(".treeview").addClass("active menu-open");

menuFix();

})

$(function(){


$("a[href='"+ requestPath +"']").parent("li").addClass("active current");

$(".sidebar-menu li.active").closest(".treeview").addClass("active menu-open");

menuFix();

	$(".init-select2").each(function() {
		var placeholder = $(this).attr("placeholder");
		$(this).select2(placeholder ? {placeholder} : {});
	});


})
</script>	
