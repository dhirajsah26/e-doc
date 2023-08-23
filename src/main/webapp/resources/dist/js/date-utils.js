var NEPALI_MONTH = ["Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"];
var NEPALI_MONTHS_INC_ALL = ["All", "Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"];
var ENGLISH_MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
var ENGLISH_MONTHS_INC_ALL = ["All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

function getNepaliMonthName(month) {
	return NEPALI_MONTH[month-1];
}

function getEnglishMonthName(month) {
	return ENGLISH_MONTHS[month-1];
}

function getFinalDate(date, operationDateSetting, timeNotRequired){
	if (!date) return "";
	
	if (operationDateSetting === 'np') {
		var splitted = date.split(" ");
		if(splitted.length == 2){
			
			if(!timeNotRequired)
				return AD2BS(splitted[0]) + " " + splitted[1];
			else
			return AD2BS(splitted[0]);
			
		}else{
			return AD2BS(splitted[0]);
		}
	}
	return date;
}

function getFormatedStringDate(date, operationDateSetting) {
	var splittedDate = date.split("-");	
	var year = parseInt(splittedDate[0]);
	var day = parseInt(splittedDate[2]);
	var month = operationDateSetting == 'np' ? getNepaliMonthName(parseInt(splittedDate[1])) : getEnglishMonthName(parseInt(splittedDate[1]));
	return day + "<sup>" + ordinalSuffixOf(day) + "</sup>" + " " + month + " " + year;
}

function fromAndToDateValidation(instance) {
    var momentDate = momentNepaliToEnglish($(instance).val());	      
    var dateDifference = moment().diff(momentDate, 'years', true);	      
    if(dateDifference<0){
       alert('Date must be less than or equals to current date.');
       $(instance).val(AD2BS(moment().format('YYYY-MM-DD')));
    } 
    
    var instanceName = $(instance).attr("name");
    var row = $(instance).closest(".row");
    var fromDate = row.find("input[name='start_date']").val();
    var toDate = row.find("input[name='end_date']").val();
    if((instanceName=="start_date" && toDate!="") || (instanceName=="end_date" && fromDate!="")){
  	  if(instanceName=="start_date"){
  		  fromDate = $(instance).val();
  	  }else{
  		  toDate = $(instance).val();
  	  }
  	  
  	  var momentFromDate = momentNepaliToEnglish(fromDate);
  	  var momentToDate = momentNepaliToEnglish(toDate);
  	  
  	  var dateDifference = momentToDate.diff(momentFromDate,'day');
	      if(dateDifference<0){
	         alert('Invalid date. From Date can not be greater than To Date.');
	         
	         if(instanceName=="start_date"){
	         	$(instance).val(toDate);
	         }else{
	        	$(instance).val(fromDate);
	         }
	      } 
    }
 }
	
function momentNepaliToEnglish(value){
	   return moment(new Date(BS2AD(value)).toISOString()); 
}

function valid() {
	$('.datepicker').each(function(){
		var checkValidDate; 
		if(operationDateSetting == 'np'){
			checkValidDate = isEqualOrBeforeCurrentDate(BS2AD($("#end-date").val()));
			
			if(!checkValidDate){
				isValid = false;
				alert("From date must be less than or equals to current date.");
				$('#end-date').val(getCurrentDate(operationDateSetting));
			}else{
				$(".date-error").html("");
			}
		}else{
			checkValidDate = isEqualOrBeforeCurrentDate($("#end-date").val());
			if(!checkValidDate){
				isValid = false;
				alert("From must be less than or equals to current date.");
				setDate(toDateInstance, operationDateSetting == 'np' ? AD2BS(fromDate) : fromDate, operationDateSetting);
			}else{
				//$(".date-error").html("");
			}
		}
	});	
}

function getEngDate(date){
	if (!date) return null;
	if (operationDateSetting === 'np')
		return BS2AD(date);
	return date;
}
