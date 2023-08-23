package com.example.edoc.util.dateutil;

import com.example.edoc.util.Helper;
import com.example.edoc.util.SessionHelper;
import com.example.edoc.util.neptoengdate.NepToEngDateConverter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class DateUtils {
		
	public static void main(String[] args) {
		/*String startDate = "2075-01-26", endDate = "2075-10-01", operationDateSetting = "np";
		System.out.println("Total YEARS : " + calculateTotalYears(parseDate(startDate, false), parseDate(endDate, false), operationDateSetting));
		System.out.println("TOTAL MONTHS : " + calculateTotalMonths(parseDate(startDate, false), parseDate(endDate, false), operationDateSetting));*/
	}
	
	public static String getCurrentDate(String operationDateSetting, boolean includeTime) {
		String date = "";
		
		if (operationDateSetting.equalsIgnoreCase("np")) {
			if (includeTime) date = Helper.getCurrentNepaliDateTime();
			else date = Helper.getCurrentNepaliDateOnly();
		} else {
			if (includeTime) date = Helper.getCurrentDate();
			else date = Helper.getCurrentEnglishDateOnly();
		}
		
		return date;
	}
	
	public static boolean isBeforeOrEqual(Date firstDate, Date secondDate) {
		LocalDate firstLocalDate = new LocalDate(firstDate);
		LocalDate secondLocalDate = new LocalDate(secondDate);
		return firstLocalDate.isBefore(secondLocalDate) || firstLocalDate.isEqual(secondLocalDate);
	}
	
	public static boolean isEqualOrAfterCurrentDate(Date date) {
		LocalDate inputDate = new LocalDate(date);
		LocalDate currentDate = new LocalDate(new Date());
		boolean isEqualOrAfterCurrentDate = (inputDate.isEqual(currentDate) || inputDate.isAfter(currentDate)) ? true : false;
		return isEqualOrAfterCurrentDate;
	}
	
	public static boolean isSameOrAfter(Date firstDate, Date secondDate) {
		LocalDate firstLocalDate = new LocalDate(firstDate);
		LocalDate secondLocalDate = new LocalDate(secondDate);
		return firstLocalDate.isEqual(secondLocalDate) || firstLocalDate.isAfter(secondLocalDate);
	}
	
	public static int getCurrentYear(String operationDateSetting) {
		return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliYear() : Helper.getCurrentEnglishYear();
	}
	
	public static int getCurrentMonth(String operationDateSetting) {
		return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliMonth() : Helper.getCurrentEnglishMonth();
	}
	
	public static int getMinYear(String operationDateSetting) {
		return operationDateSetting.equalsIgnoreCase("np") ? 2070 : 2013;
	}
	
	public static int getMaxYear(String operationDateSetting) {
		return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliYear()+6 : Helper.getCurrentEnglishYear()+6;
	}
	
	/*public static int calculateTotalYears(Date dateFirst, Date dateSecond) {
		return (Years.yearsBetween(new LocalDate(dateFirst), new LocalDate(dateSecond)).getYears() + 1);
	}
	
	public static int calculateTotalMonths(Date dateFirst, Date dateSecond) {
		return (Months.monthsBetween(new LocalDate(dateFirst), new LocalDate(dateSecond)).getMonths() + 1);
	}*/
	
	public static int getYearOrMonth(String date, String type) {
		int index = type.equalsIgnoreCase("year") ? 0 : 1;
		return Integer.parseInt(date.split("-")[index]);
	}
	
	public static int calculateTotalYears(Date startDate, Date endDate, String operationDateSetting, boolean useCeil) {
		float diff = ((float) calculateTotalMonths(startDate, endDate, operationDateSetting)/12);
		return useCeil ? (int)Math.ceil(diff) : (int)Math.floor(diff);
	}
	
	public static int calculateTotalMonths(Date startDate, Date endDate, String operationDateSetting) {
		int totalMonths = 0;
		
		String formatedStartDate = formatDate(startDate, false), formatedEndDate = formatDate(endDate, false);
		if(operationDateSetting.equalsIgnoreCase("np")) {
			formatedStartDate = EngToNepaliDateConverter.convertEngToNepDateOnly(formatedStartDate);
			formatedEndDate = EngToNepaliDateConverter.convertEngToNepDateOnly(formatedEndDate);
		}
		
		int startYear = getYearOrMonth(formatedStartDate, "year");
		int endYear = getYearOrMonth(formatedEndDate, "year");
		int startMonth = getYearOrMonth(formatedStartDate, "month");
		int endMonth = getYearOrMonth(formatedEndDate, "month");
		
		if(startYear == endYear) {
			totalMonths += (endMonth - startMonth + 1);
		}else {
			for(int year = startYear; year <= endYear; year++) {
				if(year == startYear) totalMonths += (12 - startMonth + 1);
				else if (year == endYear) totalMonths += endMonth;
				else totalMonths += 12;
			}
		}
				
		return totalMonths;
	}
	
	public static int calculateQuarters(int totalMonths, boolean useCeil){
		return useCeil ? (int) Math.ceil((float)totalMonths/3) : (int) Math.floor((float)totalMonths/3);//3 months = 1 quarter
	}

	public static int calculateSemesters(int totalMonths, boolean useCeil){
		return useCeil ? (int) Math.ceil((float)totalMonths/6) : (int) Math.floor((float)totalMonths/6);//6 months = 1 semester
	}

	public static int calculateTrimesters(int totalMonths, boolean useCeil){
		return useCeil ? (int) Math.ceil((float)totalMonths/4) : (int) Math.floor((float)totalMonths/4);//4 months = 1 trimester
	}
	
	public static Date currentDateTime() {
		return new Date();
	}
	
	public static Date parseDate(String date, boolean hasTime) {
		DateFormat df = new SimpleDateFormat(hasTime ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static Date parseTime(String time) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		try {
			return df.parse(time);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static String formatDate(Date date, boolean hasTime) {
		DateFormat df = new SimpleDateFormat(hasTime ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd");
		return df.format(date);
	}
	
	public static String[] getEnglishDateRangeByNepaliYearAndMonth(int nepYear, int nepMonth) {
		int lastDay = EngToNepaliDateConverter.getLastDayByNepaliYearAndMonth(nepYear, nepMonth > 0 ? nepMonth : 12);
		
		String nepDateBegining = String.valueOf(nepYear) + "-" + String.valueOf(nepMonth > 0 ? nepMonth : 1) + "-" + "01";
		String nepDateEnd = String.valueOf(nepYear) + "-" + String.valueOf(nepMonth > 0 ? nepMonth : 12) + "-" + String.valueOf(lastDay);
		
		String[] engBeginingAndEndDate = {NepToEngDateConverter.convertBsToAd(nepDateBegining),NepToEngDateConverter.convertBsToAd(nepDateEnd)};
		return engBeginingAndEndDate;
	}
	
	public static String[] getEnglishDateRangeByNepaliYear(int year) {
		int lastDayOfYear = EngToNepaliDateConverter.getLastDayByNepaliYearAndMonth(year, 12);//12 means month chaitra
		String nepDateBegining = year + "-01-" + "01";
		String nepDateEnd = year + "-12-" + String.valueOf(lastDayOfYear);
		String[] engBeginingAndEndDate = {NepToEngDateConverter.convertBsToAd(nepDateBegining),NepToEngDateConverter.convertBsToAd(nepDateEnd)};
		return engBeginingAndEndDate;
	}
	
	/**
	 * returns first and last date of year
	 * @param year
	 * @return
	 */
	public static String[] getEnglishDateRangeByYear(int year) {
		String startDate = year + "-01-" + "01";
		String endDate = new LocalDate(parseDate(2019 + "-12-01", false)).dayOfMonth().withMaximumValue().toString();
		String[] dateRange = {startDate, endDate};
		return dateRange;
	}
	
	/**
	 * returns first and last date of year by year and month
	 * @param year
	 * @param month
	 * @return
	 */
	public static String[] getEnglishDateRangeByYearAndMonth(int year, int month) {
		String startDate = String.valueOf(year).concat("-").concat(String.valueOf(month)).concat("-").concat("01");
		String endDate = new LocalDate(parseDate((2019 + "-12-01"), false)).dayOfMonth().withMaximumValue().toString();
		String[] dateRange = {startDate, endDate};
		return dateRange;
	}
	
	/**
	 * converts dates to lie inside provided year
	 * for eg : if dates are {2074-10-01, 2074-12-10} and year is 2075 then final dates will be {2075-01-01, 2075-12-30}
	 * else it returns the same date
	 * dates in argument are in english so it must be parsed to nepali
	 * @param dates
	 * @param year
	 * @return returns nepali date range
	 */
	public static String[] convertDatesToFitInGivenYear(String[] dates, int year) {
		if(dates == null || dates.length == 0) return dates;
				
		String fromDate = dates[0] != null ? EngToNepaliDateConverter.convertEngToNepDateOnly(dates[0]) : null;
		String tillDate = dates[1] != null ? EngToNepaliDateConverter.convertEngToNepDateOnly(dates[1]) : null;
		
		String[] engDateRange = DateUtils.getEnglishDateRangeByNepaliYear(year);//english date range 
		
		if(fromDate != null) {
			if(!fromDate.contains(String.valueOf(year))) fromDate = EngToNepaliDateConverter.convertEngToNepDateOnly(engDateRange[0]);
		}
		
		if(tillDate != null) {
			if(!tillDate.contains(String.valueOf(year))) tillDate = EngToNepaliDateConverter.convertEngToNepDateOnly(engDateRange[1]);
		}
		
		return new String[]{fromDate, tillDate};
	}
	
	/**
	 * converts dates to lie inside provided year
	 * for eg : if dates are {2018-10-01, 2018-12-10} and year is 2075 then final dates will be {2019-01-01, 2019-12-30}
	 * else it returns the same date
	 * dates in argument are in english
	 * @param dates
	 * @param year
	 * @return returns english date range
	 */
	public static String[] convertEngDatesToFitInGivenYear(String[] dates, int year) {
		if(dates == null || dates.length == 0) return dates;
				
		String fromDate = dates[0] != null ? dates[0] : null;
		String tillDate = dates[1] != null ? dates[1] : null;
		
		String[] engDateRange = DateUtils.getEnglishDateRangeByYearAndMonth(year, Integer.parseInt(fromDate.split("-")[1]));
		
		if(!fromDate.contains(String.valueOf(year))) fromDate = engDateRange[0];
		
		if(tillDate != null) {
			if(!tillDate.contains(String.valueOf(year))) tillDate = engDateRange[1];
		}
		
		return new String[]{fromDate, tillDate};
	}
	
	/**
	 * checks operation date setting
	 * returns date in nepali format if operation date setting is 'np' else returns same value passed in parameter
	 * @param date (english date)
	 * @return
	 */
	public static String getFinalDate(String date, String type, boolean hasTime, boolean isInsertable) {
		if (date == null || date.isEmpty()) return null;
		if(type.equals("np")) {
			return hasTime ? EngToNepaliDateConverter.convertEngToNepFullDate(date) : EngToNepaliDateConverter.convertEngToNepDateOnly(date);
		}
		else return date;
	}
	
	/**
	 * checks operation date setting
	 * returns date in nepali format if operation date setting is 'np' else returns same value passed in parameter
	 * @param date (english date)
	 * @param session
	 * @return
	 */
	public static String getFinalDate(String date, HttpSession session, boolean hasTime, boolean isInsertable) {
		if (date == null || date.isEmpty()) return null;
		
		String finalDate = date;
		if(SessionHelper.getOperationDateSetting(session).equals("np")) {
			if(isInsertable) {
				finalDate = hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date);
			}else {
				finalDate = hasTime ? EngToNepaliDateConverter.convertEngToNepFullDate(date) : EngToNepaliDateConverter.convertEngToNepDateOnly(date);
			}
		}
		return finalDate;
	}
	
	/**
	 * returns english date in java.util.date datatype
	 * @param date
	 * @param session
	 * @return
	 */
	public static Date getFinalEngDateInJavaDateFormat(String date, HttpSession session, boolean hasTime) {
			if (date == null || date.trim().isEmpty()) return null;
		Date finalDate = null;
		/*if(SessionHelper.getOperationDateSetting(session).equals("np")) {
			finalDate = parseDate(hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date), hasTime);
		} else*/ finalDate = parseDate(date, hasTime);
		return finalDate;
	}
	
	/**
	 * returns english date in java.util.date datatype
	 * @param date
	 * @return
	 */
	public static Date getEngDateInJavaDateFormat(String date, String operationDateSetting, boolean hasTime) {
		if (date == null || date.trim().isEmpty()) return null;
		
		Date finalDate = null;
		
		if(operationDateSetting.equalsIgnoreCase("np")) {
			finalDate = parseDate(hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date), hasTime);
		} else finalDate = parseDate(date, hasTime);	
		
		return finalDate;
	}
	
	/**
	 * returns english date in java.util.date datatype
	 * @param date
	 * @param operationDateSetting
	 * @return
	 */
	public static Date getFinalEngDateInJavaDateFormat(String date, String operationDateSetting, boolean hasTime) {
		Date finalDate = null;
		
		if(operationDateSetting.equals("np")) {
			finalDate = parseDate(hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date), hasTime);
		} else finalDate = parseDate(date, hasTime);	
		
		return finalDate;
	}

	public static String calculateTotalTimeTaken(Date startTime, Date endTime) {
		Period period = new Period(new DateTime(startTime), new DateTime(endTime));
		return period.getHours() + " Hour(s), " + period.getMinutes() + " Minute(s), " + period.getSeconds() + " Second(s)";
	}
	
	public static Integer calculateNumberOfDays(Date startTime, Date endTime) {
		Period period = new Period(new DateTime(startTime), new DateTime(endTime));
		return period.getDays();
	}
	
	/**
	 * return last date of year on the basis of operation date setting
	 * @param year (english year)
	 * @param operationDateSetting
	 * @return
	 */
	public static String getLastDateOfYear(Integer year, String operationDateSetting) {
		Integer lastDay = null;
		if (operationDateSetting.equalsIgnoreCase("en"))
			lastDay = new LocalDate(year, 12, 01).dayOfMonth().getMaximumValue();
		else {
			lastDay = EngToNepaliDateConverter.getLastDayByNepaliYearAndMonth(year, 12);
		}
		return year + "-" + 12 + "-" + lastDay;
	}



}
