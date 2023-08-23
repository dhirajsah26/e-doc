$(".datepicker, .timepicker").keydown(false);
$(".datepicker, .timepicker").attr("autocomplete", "off");
$(".datepicker").attr("placeholder", "YYYY-MM-DD");
$(".datepicker").trigger('onload');

function initDatePicker(instance, onChangeMethod){
 	switch (operationDateSetting) {
	case "np":
		$(instance).nepaliDatePicker({
     		onChange: function(){
     			if(onChangeMethod) onChangeMethod();
     		},
  	     npdMonth: true,
  	     npdYear: true,
		});
		break;

	case "en":
		$(instance).datepicker({
		    format: "yyyy-mm-dd",
		    startDate: "2000-01-01",
		    autoclose: true,
		    orientation: "bottom auto"
		});
		$(instance).on('changeDate', function() {
			if(onChangeMethod) onChangeMethod();
		});
		break;
		
	default:
		break;
	}
}

function setDate(inputFieldInstance, date, operationDateSetting){
	if(operationDateSetting == 'np'){
		inputFieldInstance.val(date);
	}else{
		inputFieldInstance.datepicker("update", date);
	}
}

function destroyDatePickerInstance(instance, operationDateSetting){
	if(operationDateSetting == 'np'){
		instance.nepaliDatePicker("destory");
	}else{
		instance.datepicker("destory");
	}
}
 