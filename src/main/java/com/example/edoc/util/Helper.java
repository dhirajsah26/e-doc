package com.example.edoc.util;


import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.DTO.UserPermissionDTO;
import com.example.edoc.DTO.UserRoleSchoolDTO;
import com.example.edoc.constants.MessageType;
import com.example.edoc.dao.UserRoleSchoolDao;
import com.example.edoc.enums.ModuleRole;
import com.example.edoc.models.ModuleNew;
import com.example.edoc.models.UserPermission;
import com.example.edoc.models.UserRolePermissionNew;
import com.example.edoc.util.dateutil.EngToNepaliDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class Helper {

	@Autowired UserRoleSchoolDao userRoleSchoolDao;

	public static void setSessionExpiredFlashMessage(RedirectAttributes redirectAttributes) {
		Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
	}

	public static Date parseDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getCurrentDateTime() {
		return new Date();
	}

	public static void setFlashMessage(String type, String message, RedirectAttributes redirectAttributes) {
		if (message != null && !message.trim().isEmpty())
			redirectAttributes.addFlashAttribute("message", getFlashMessage(type, message));

	}

	public static Object getFlashMessage(String type, String message) {

		String className = "";

		switch (type) {
			case MessageType.SUCCESS:
				className = "alert-success";
				break;

			case MessageType.ERROR:
				className = "alert-danger";
				break;

			case MessageType.INFO:
				className = "alert-info";
				break;

			case MessageType.WARNING:
				className = "alert-warning";
				break;

			default:
				break;
		}

		return "<div class='alert " + className + " alert-dismissible fade in'>" + message + "</div>";
	}

	public static String getLabelByStatus(String type) {
		if (type == null || type.isEmpty())
			type = MessageType.PENDING;

		String className = "";
		String status = "";


		switch (type) {
			case MessageType.PENDING:
				status = "Pending";
				className = "label-warning";
				break;

			case MessageType.REJECTED:
				status = "Rejected";
				className = "label-danger";
				break;

			case MessageType.APPROVED:
				status = "Approved";
				className = "label-success";
				break;

			case MessageType.WINNER:
				status = "Winner";
				className = "label-primary";
				break;

			default:
				className = "label-default";
				break;
		}

		return "<label class='label " + className + "'>" + status + "</label>";
	}

/*	function getDisplayableStatus(status, type) {
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
	}*/

	public static String getDisplayableStatus(boolean status, String type) {
		String className = "", text = "";

		if (status) {
			className = "label-success";
			if (type.equalsIgnoreCase("YES_NO")) text = "Yes";
			else text = "Active";
		} else {
			className = "label-danger";
			if (type.equalsIgnoreCase("YES_NO")) text = "No";
			else text = "In-Active";
		}

		return "<label class='label "+ className +"'>"+ text +"</label>";
	}

	public List<ModuleNew> reDesignModule(List<UserRolePermissionNew> userRolePermissionNews) {
		List<ModuleNew> finalModuleNews = new ArrayList<ModuleNew>();

		Map<Long, ModuleNew> map = new HashMap<>();
		userRolePermissionNews.forEach(userRolePermission -> {
			UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
			userPermissionDTO.setId(userRolePermission.getUserPermission().getId());
			userPermissionDTO.setName(userRolePermission.getUserPermission().getName());
			userRolePermission.getUserPermission().getModuleNew().setUserPermissionDTO(userPermissionDTO);

			ModuleNew ancestor = null, parent = null, child = null;

			switch (userRolePermission.getUserPermission().getModuleNew().getRole()) {
				case MODULE:
					ancestor = userRolePermission.getUserPermission().getModuleNew();
					break;

				case SUB_MODULE:
					ancestor = userRolePermission.getUserPermission().getModuleNew().getParentModule();
					parent = userRolePermission.getUserPermission().getModuleNew();
					break;

				case MENU_LINE:
					ancestor = userRolePermission.getUserPermission().getModuleNew().getParentModule().getParentModule();
					parent = userRolePermission.getUserPermission().getModuleNew().getParentModule();
					child = userRolePermission.getUserPermission().getModuleNew();
					break;

				default:
					break;
			}

			ModuleNew reservedAncestor = ancestor;
			List<ModuleNew> reservedParents = new ArrayList<>();
			int currentParentIndexOnReservedList = 0;

			if (map.containsKey(ancestor.getId())) {

				reservedAncestor = map.get(ancestor.getId());
				reservedParents = reservedAncestor.getChildModuleNews() != null ? reservedAncestor.getChildModuleNews() : new ArrayList<>();

				int i = 0;
				for (ModuleNew moduleNe : reservedParents) {
					if (moduleNe.getId().equals(parent.getId())) {
						parent = moduleNe;
						//currentParentIndexOnReservedList = i;
						break;
					}
					i++;
				}
				currentParentIndexOnReservedList = i;
			}

			if (parent != null) {
				List<ModuleNew> reservedSubModuleParams = new ArrayList<>();
				if (parent.getChildModuleNews() != null) reservedSubModuleParams = parent.getChildModuleNews();
				if (child != null) reservedSubModuleParams.add(child);
				parent.setChildModuleNews(reservedSubModuleParams);
				if (reservedParents.size() > currentParentIndexOnReservedList) reservedParents.remove(currentParentIndexOnReservedList);
				if (parent != null) reservedParents.add(currentParentIndexOnReservedList, parent);
				reservedAncestor.setChildModuleNews(reservedParents);
			}

			map.put(ancestor.getId(), reservedAncestor);
		});

		//add data to final moduleNew list
		map.forEach((key, val) -> {
			finalModuleNews.add(val);
		});

		//sort by rank
		java.util.Collections.sort(finalModuleNews, (a, b) -> (a.getRank() - b.getRank()));

		return finalModuleNews;
	}

	public List<ModuleNew> reDesignModuleNew(List<UserPermission> userPermissions) {
		List<ModuleNew> finalModuleNews = new ArrayList<ModuleNew>();

		Map<Long, ModuleNew> map = new HashMap<>();
		userPermissions.forEach(userPermission -> {
			ModuleNew module = userPermission.getModuleNew();

			UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
			userPermissionDTO.setId(userPermission.getId());
			userPermissionDTO.setName(userPermission.getName());
			module.setUserPermissionDTO(userPermissionDTO);

//			System.out.println(userPermissionDTO);

			ModuleNew ancestor = null, parent = null, child = null;

			switch (module.getRole()) {
				case MODULE:
					ancestor = module;
					break;

				case SUB_MODULE:
					ancestor = module.getParentModule();
					parent = module;
					break;

				case MENU_LINE:
					ancestor = module.getParentModule().getParentModule();
					parent = module.getParentModule();
					child = module;
					break;

				default:
					break;
			}

			ModuleNew reservedAncestor = ancestor;
			List<ModuleNew> reservedParents = new ArrayList<>();
			int currentParentIndexOnReservedList = 0;

			if (map.containsKey(ancestor.getId())) {

				reservedAncestor = map.get(ancestor.getId());
				reservedParents = reservedAncestor.getChildModuleNews() != null ? reservedAncestor.getChildModuleNews() : new ArrayList<>();

				int i = 0;
				for (ModuleNew moduleNe : reservedParents) {
					if (moduleNe.getId().equals(parent.getId())) {
						parent = moduleNe;
						//currentParentIndexOnReservedList = i;
						break;
					}
					i++;
				}
				currentParentIndexOnReservedList = i;
			}

			if (parent != null) {
				List<ModuleNew> reservedSubModuleParams = new ArrayList<>();
				if (parent.getChildModuleNews() != null) reservedSubModuleParams = parent.getChildModuleNews();
				if (child != null) reservedSubModuleParams.add(child);
				parent.setChildModuleNews(reservedSubModuleParams);
				if (reservedParents.size() > currentParentIndexOnReservedList) reservedParents.remove(currentParentIndexOnReservedList);
				if (parent != null) reservedParents.add(currentParentIndexOnReservedList, parent);
				reservedAncestor.setChildModuleNews(reservedParents);
			}

			map.put(ancestor.getId(), reservedAncestor);
		});

		//add data to final moduleNew list
		map.forEach((key, val) -> {
			finalModuleNews.add(val);
		});

		//sort by rank
		java.util.Collections.sort(finalModuleNews, (a, b) -> (a.getRank() - b.getRank()));

		return finalModuleNews;
	}

	public boolean checkUserPermission(String permissionKey, HttpSession session) {
		if (true) return true;

		// get school account by id
		int id = SessionHelper.getLoggedInAdminId(session);
		UserRoleSchoolDTO userRoleSchool = userRoleSchoolDao.findBySchoolAccountId(id);
		if(userRoleSchool != null) {
			if (userRoleSchool.isSuperUserRole()) return true;
			return userRoleSchoolDao.hasAccessPrivilege(userRoleSchool.getId().longValue(), permissionKey);
		}

		return false;
	}

	public static List<ModuleNewDTO> extractModuleSubModuleAndMenuLine(List<ModuleNewDTO> moduleNewDTOs) {
		if (moduleNewDTOs == null) return null;

		List<ModuleNewDTO> finalModuleNewDTOs = new ArrayList<>();

		Map<Long, ModuleNewDTO> modulesMap = new HashMap<>();
		Map<Long, ModuleNewDTO> subModulesMap = new HashMap<>();
		for (ModuleNewDTO moduleNewDTO : moduleNewDTOs) {
			Long key = moduleNewDTO.getId().longValue();

			switch (moduleNewDTO.getRole()) {
				case "MODULE":
					if (!modulesMap.containsKey(key)) {
						//moduleNewDTO.setTotalSubModules(0);
						//moduleNewDTO.setTotalMenuLines(0);
						modulesMap.put(key, moduleNewDTO);
					}
					break;

				case "SUB_MODULE":
					Long moduleId = moduleNewDTO.getParentModuleId().longValue();
					if (modulesMap.containsKey(moduleId)) {
						ModuleNewDTO module = modulesMap.get(moduleId);
						if (module.getSubModules() == null) module.setSubModules(new ArrayList<>());
						module.getSubModules().add(moduleNewDTO);
						//module.setTotalSubModules(module.getTotalSubModules()+1);

						if (!subModulesMap.containsKey(key))
							subModulesMap.put(key, moduleNewDTO);
					}
					break;

				case "MENU_LINE":
					Long subModuleId = moduleNewDTO.getParentModuleId().longValue();
					if (subModulesMap.containsKey(subModuleId)) {
						ModuleNewDTO subModule = subModulesMap.get(subModuleId);
						if (subModule.getSubModules() == null) subModule.setSubModules(new ArrayList<>());
						subModule.getSubModules().add(moduleNewDTO);

					/*if (modulesMap.containsKey(subModule.getParentModuleId().longValue())) {
						//ModuleNewDTO parentModule = modulesMap.get(subModule.getParentModuleId().longValue());
						//parentModule.setTotalMenuLines(parentModule.getTotalMenuLines()+1);
					}*/
					}
					break;

				default:
					break;
			}
		}

		modulesMap.forEach((k, v) -> finalModuleNewDTOs.add(v));

		//sort module by rank
		Collections.sort(finalModuleNewDTOs, (a, b) -> a.getRank() - b.getRank());

		return finalModuleNewDTOs;
	}

	/* ******************************************************/

	public static String getCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH🇲🇲ss");
		Date date = new Date();
		String final_date = df.format(date);
		return final_date;
	}

	public static String getCurrentEnglishDateOnly() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String final_date = df.format(date);
		return final_date;
	}
	public static int getCurrentEnglishYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	public static int getCurrentEnglishMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}
	public static String byteToBase64Image(byte[] data) {
		if (data == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("data:image/png;base64,");
		String encoded = Base64.getEncoder().encodeToString(data);
		sb.append(encoded);
		return sb.toString();
	}
	public static String getCurrentNepaliDateTime() {
		return getCurrentDateOnly() + " " + getCurrentTimeOnly();
	}
	public static String getCurrentTimeOnly() {
		DateFormat df = new SimpleDateFormat("HH🇲🇲ss");
		Date date = new Date();
		String final_time = df.format(date);
		return final_time;
	}

	public static int getCurrentNepaliYear() {
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int nepaliYear = 2074;
		try {
			nepaliYear = Integer.parseInt(EngToNepaliDateConverter.engToNep(year, month, day).substring(0, 4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nepaliYear;
	}
	public static String getCurrentDateOnly() {
		String final_date = "";
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		try {
			final_date =EngToNepaliDateConverter.engToNep(year, month, day);
		} catch (Exception e) {
		}
		return final_date;
	}
	public static int getCurrentYear() {
		return getCurrentNepaliYear();
	}
	public static String getCurrentNepaliDateOnly() {
		Date d = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String currentDate = "2074-01-01";
		try {
			currentDate = EngToNepaliDateConverter.engToNep(year, month, day);// yyyy,mm,dd
		} catch (Exception e) {

		}

		return currentDate;
	}
	public static int getCurrentNepaliDay() {
		List<Integer> dateList = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(getCurrentNepaliDateOnly(), "-");
		while (tokenizer.hasMoreTokens()) {
			dateList.add(Integer.parseInt(tokenizer.nextToken()));
		}
		return dateList.get(2);// 0 year, 1 month, 2 day
	}
	public static int getCurrentNepaliMonth() {
		List<Integer> dateList = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(getCurrentNepaliDateOnly(), "-");
		while (tokenizer.hasMoreTokens()) {
			dateList.add(Integer.parseInt(tokenizer.nextToken()));
		}
		return dateList.get(1);// 0 year, 1 month, 2 day
	}




}
