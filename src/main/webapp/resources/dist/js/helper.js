function setModalTitle(modalInstance, title) {
	$(modalInstance).find(".modal-title").html(title);
}

var YES_NO = "yes_no";
var ACTIVE_INACTIVE = "active_inactive";

//export buttons for excel,pdf,...
function initExportButtons(table, tableToolsInstance=$(".table-tools")) {
    var buttons = new $.fn.dataTable.Buttons(table, {
        buttons: [{
                extend: 'copyHtml5',
                text: '<i class="fa fa-files-o"></i>',
                titleAttr: 'Copy Table',
                exportOptions: {
                    columns: ':not(:last-child)',
                }
            },
            {
                extend: 'excelHtml5',
                text: '<i class="fa fa-file-excel-o"></i>',
                titleAttr: 'Generate Excel',
                exportOptions: {
                    columns: ':not(:last-child)',
                }
            },
            {
                extend: 'csvHtml5',
                text: '<i class="fa fa-file-text-o"></i>',
                titleAttr: 'Export to CSV',
                exportOptions: {
                    columns: ':not(:last-child)',
                }
            },
            {
                extend: 'pdfHtml5',
                text: '<i class="fa fa-file-pdf-o"></i>',
                titleAttr: 'Generate PDF',
                exportOptions: {
                    columns: ':not(:last-child)',
                }
            },
            {
                extend: 'print',
                text: '<i class="fa fa-print"></i>',
                titleAttr: 'Print',
                exportOptions: {
                    columns: ':not(:last-child)',
                }
            }
        ]
    });
    $(tableToolsInstance).html(buttons.container());
    $('.dt-buttons .btn').attr('data-toggle', 'tooltip');
}

//default datatable settings
defaultDTSetting={
	"pageLength": 10,
	"columnDefs": [ {
          "targets"  : [0,-1],
          "orderable": false,
          "className": "text-center"
        }]
} 

/*function initDataTable(tableInstance, tableToolsInstance) {
	$(".table-tools").html("");
	if (tableToolsInstance) $(tableToolsInstance).html("");
	
	var newtable = tableInstance.dataTable({
		"pageLength": 10,
		"responsive": true,
	    "order": [],
	    "columnDefs": [ {
	      "targets"  : [0,-1],
	      "orderable": false,
	      "className": "text-center"
	    }]
	});
	initExportButtons(newtable, tableToolsInstance);
}*/

function reInitDataTable(tableInstance, tableToolsInstance) {
	initDataTable(tableInstance, tableToolsInstance);
}

function initDataTable(instance,tableToolsInstance=$(".table-tools"),setting=defaultDTSetting){
	var table = $(instance).dataTable( {
		"responsive": true,
        "order": [],
        defaultDTSetting
    });
	tableToolsInstance.empty();
	
//	if(table.length && hasSuperUserRole && tableToolsInstance) {
	if(table.length && tableToolsInstance) {
		initExportButtons(table,tableToolsInstance);
	}
	return table;
}

function destroyDataTable(instance) {
	$(instance).find("tbody").html("")
	instance.DataTable().destroy();
}

function capitalizeFirstLetter(text) {
	return text.charAt(0).toUpperCase() + text.slice(1);
}

function getDisplayableStatus(status, type) {
	var className = "", text = "";
	
	if (status) {
		className = "label-success";
		if (type == YES_NO) text = "Yes";
		else text = "Active";
	} else {
		className = "label-danger";
		if (type == YES_NO) text = "No";
		else text = "In-Active";
	}
	
	return "<label class='label "+ className +"'>"+ text +"</label>";
}

function resetForm(instance) {
	$(instance).trigger("reset");
    $("label.error").remove();
    $("span.error").html();
	
	//reset select2 integrated select boxes
	$(instance).find("select").trigger("change");
	
	//reset icheck integrated radio buttons	
	$(instance).find("input[type=radio], input[type=checkbox]").iCheck("update");
}

function ordinalSuffixOf(i) {
    var j = i % 10, k = i % 100;
    if (j == 1 && k != 11) return "st";
    if (j == 2 && k != 12) return "nd";
    if (j == 3 && k != 13) return "rd";
    return "th";
}