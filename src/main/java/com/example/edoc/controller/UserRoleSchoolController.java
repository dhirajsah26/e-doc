package com.example.edoc.controller;

import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.DTO.UserRoleSchoolDTO;
import com.example.edoc.constants.MessageType;
import com.example.edoc.dao.*;
import com.example.edoc.models.*;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import com.example.edoc.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value="/user/role")
public class UserRoleSchoolController {
	
	@Autowired
    UserRoleSchoolDao userRoleSchoolDao;
	@Autowired
    UserRolePermissionNewDao userRolePermissionDao;
	@Autowired
    UserPermissionDao userPermissionDao;
	@Autowired
    ModuleNewDao moduleNewDao;
	@Autowired
    PackageTemplateInfoDao packageTemplateInfoDao;
	@Autowired UserRoleMenuInfoDao userRoleMenuInfoDao;
	@Autowired
    Helper helper;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String index(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		//checking user login
		/*if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setSessionExpiredFlashMessage(redirectAttributes);
			return "redirect:/admin/login";
		}
		*/
		if (!helper.checkUserPermission("view_user_roles", session))
			return "nopermission";
		
		model.addAttribute("title"," User Roles");
		model.addAttribute("userRoles", userRoleSchoolDao.selectAllRole());
		return "user-role/index";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public ResponseEntity<Response> saveFeeType(HttpSession session, RedirectAttributes redirectAttributes, @ModelAttribute UserRoleSchool userRoleSchool) {
		boolean success = false;
		String message = null;



		if (helper.checkUserPermission("create_user_role", session)) {
			Admin admin = SessionHelper.getLoggedInAdmin(session);
			userRoleSchool.setCreatedBy(admin);
			userRoleSchool.setCreatedDate(Helper.getCurrentDateTime());
			Long id = userRoleSchoolDao.save(userRoleSchool);
			if(id > 0){
				message = "User role saved successfully.";
				success = true;
			}else message = "Sorry but failed to save user role .Please retry" ;
		} else message = Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION;





		/*if(SessionHelper.isAdminLoggedIn(session)){

		 }else message = Strings.SESSION_EXPIRED; */
				
		return new ResponseEntity<>(new Response(null, message, success), HttpStatus.OK);
	}
	
	@RequestMapping(value="fetchbyid", method = RequestMethod.POST)
	public ResponseEntity<Response> fetchAllUserRoleById(@RequestParam("userRoleId") Long userRoleId ,HttpSession session){
		boolean success = false;
		String message = null;
		UserRoleSchool userRoleSchool = null;
		
		if(SessionHelper.isAdminLoggedIn(session)) {
			try {
				userRoleSchool = userRoleSchoolDao.selectById(userRoleId);
				success = true;
			}catch(Exception e) {
				e.printStackTrace();
				message = Strings.SOMETHING_WENT_WRONG;
			}
		}else message = Strings.SESSION_EXPIRED;
		
		return new ResponseEntity<>(new Response(userRoleSchool, message, success), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseEntity<Response> editUserRole(@RequestParam("userRoleId") Long userRoleId, HttpSession session, 
		@ModelAttribute UserRoleSchool userRoleSchool) {
		boolean success = false;
		String message = null;

		if(SessionHelper.isAdminLoggedIn(session)) {
			if (helper.checkUserPermission("update_user_role", session)) {
				Admin admin = SessionHelper.getLoggedInAdmin(session);
				userRoleSchool.setId(userRoleId);
				userRoleSchool.setModifiedBy(admin);
				userRoleSchool.setModifiedDate(new Date());
				
				if(userRoleSchoolDao.update(userRoleSchool)){
					message = "User role updated successfully.";
					success = true;
				}else{
					message =  "Sorry but failed to update user role.Please retry";
				}
			} else message = Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION;
		}else message = Strings.SESSION_EXPIRED;
				
		return new ResponseEntity<>(new Response(null, message, success), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ResponseEntity<Response> deleteUserRole(@RequestParam("userRoleId") Long userRoleId, HttpSession session) {
		boolean success = false;
		String message = null;
		
		if(SessionHelper.isAdminLoggedIn(session)) {
			if (helper.checkUserPermission("delete_user_role", session)) {
				UserRoleSchool userRoleSchool = userRoleSchoolDao.selectById(userRoleId);
				if (userRoleSchool != null) {
					if(userRoleSchoolDao.delete(userRoleSchool)){
						message = "Deleted successfully.";
						success = true;
					} else {
						message =  "Sorry but failed to delete user role.Please retry";
					}
				} else message = Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION;
			} else message = Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION;
		}else message = Strings.SESSION_EXPIRED;
				
		return new ResponseEntity<>(new Response(null, message, success), HttpStatus.OK);
	}
	
	@RequestMapping(value="findall", method = RequestMethod.POST)
	public ResponseEntity<Response> fetchAllUserRoleById(HttpSession session){
		boolean success = false;
		String message = null;
		List<UserRoleSchool> userRoleSchools = null;
		
		if(SessionHelper.isAdminLoggedIn(session)) {
			try {
				userRoleSchools = userRoleSchoolDao.selectAllRole();
				success = true;
			}catch(Exception e) {
				e.printStackTrace();
				message = Strings.SOMETHING_WENT_WRONG;
			}
		}else message = Strings.SESSION_EXPIRED;
		
		return new ResponseEntity<>(new Response(userRoleSchools, message, success), HttpStatus.OK);
	}
	
	@RequestMapping(value="name/checkavailability", method=RequestMethod.POST)
	public ResponseEntity<Response> checkNameAvailability(HttpSession session, @RequestParam("id") Long userrRoleId, @RequestParam("name") String name){
		boolean success = false;
		String message = null;
		
		if(SessionHelper.isAdminLoggedIn(session)) {
			success = userRoleSchoolDao.isNameAvailable(name, userrRoleId);
			if(!success) message = "Name already in use";
		}else  message = Strings.SESSION_EXPIRED;
		
		Response response = new Response(null, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String userPermissionInfo(@PathVariable("id") Long userRoleId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		// checking user login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			redirectAttributes.addFlashAttribute("message", Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}
		
		if (!helper.checkUserPermission("view_user_role_info", session)) {
			return "nopermission";
		}

		UserRoleSchool userRoleSchool = userRoleSchoolDao.selectById(userRoleId);
		if (userRoleSchool == null) {
			return "redirect:/user/role/";
		}
		
		model.addAttribute("title", "User role info");
		model.addAttribute("userRoleSchool", userRoleSchool);

		return "user-role/info";
	}
	
	@RequestMapping(value="viewalluserrolepermission", method = RequestMethod.POST)
	public ResponseEntity<Response> fetchAllDataByTeamIdAndUserRoleId(@RequestParam("userRoleId") Long userRoleId, HttpSession session){
		boolean success = false;
		String message = null;
		StringBuilder builder = new StringBuilder();
		List<UserRolePermissionNew> userRolePermissions = null;
		
		if(SessionHelper.isAdminLoggedIn(session)) {
			userRolePermissions = userRolePermissionDao.selectAllUserRolePermissionByRoleId(userRoleId);
			List<ModuleNew> assignedModuleNew = helper.reDesignModule(userRolePermissions);
			
			if(assignedModuleNew != null && assignedModuleNew.size() > 0) {
				int i = 1;
				for (ModuleNew moduleNew : assignedModuleNew) {
					
					int parentRowSpan = 0;
					if (moduleNew.getChildModuleNews() != null && moduleNew.getChildModuleNews().size() > 0) {
						for (ModuleNew moduleNe : moduleNew.getChildModuleNews())
							parentRowSpan += moduleNe.getChildModuleNews().size() > 0 ? moduleNe.getChildModuleNews().size() : 1;
					} else {
						parentRowSpan = 1;
					}
					
					builder.append("<tr><td rowspan='" + parentRowSpan + "'>" + i++ + ".</td>" + "<td rowspan='" + parentRowSpan
							+ "' colspan=''><span>&nbsp<i class=\'material-icons\'>"+ moduleNew.getIcon()+"</i>" + moduleNew.getName() + "</span></td>");
					
					if (moduleNew.getChildModuleNews() != null && moduleNew.getChildModuleNews() .size() > 0) {
					for (ModuleNew moduleN: moduleNew.getChildModuleNews()) {
						int rowSpan = moduleN.getChildModuleNews().size() > 0 ? moduleN.getChildModuleNews().size() : 1;
						
						builder.append("<td rowspan='" + rowSpan + "'" 
								+ "><span> &nbsp<i class=\'material-icons\'>"+ moduleNew.getIcon()+"</i>" + moduleN.getName() + "</span></td>");
						
						if (moduleN.getChildModuleNews() != null && moduleN.getChildModuleNews().size() > 0) {
							for (ModuleNew module : moduleN.getChildModuleNews()) {
								if (module.getUserPermissionDTO() != null) {
									
									builder.append("<td><span>" + module.getUserPermissionDTO().getName()+ "</td></span></tr> <tr>");
								} else {
									builder.append("<td><td>");
								}
							}
						} else {
							if (moduleN.getUserPermissionDTO() != null) {
								
								builder.append("<td><span>" + moduleN.getUserPermissionDTO().getName()+ "</td></span></tr> <tr>");
							} else {
								builder.append("<td><td>");
							}
						}
					}
				} else {
					
					if (moduleNew.getUserPermissionDTO() != null) {
						
						builder.append("<td></td><td><span>" + moduleNew.getUserPermissionDTO().getName()+ "</td></span> </tr> ");
					} else {
					builder.append("<td></td></tr><tr>");
				      }
				}
				builder.append("</tr>");
			  }
	      }	
	   }else  message = Strings.SESSION_EXPIRED;
		String finalBody = builder.toString();
		
		return new ResponseEntity<>(new Response(finalBody, message, success), HttpStatus.OK);
	}
	
	@RequestMapping(value="assign/menu",method=RequestMethod.GET)
	public String createAssignMenu(Model model,RedirectAttributes redirectAttributes,HttpSession session){
		//checking admin login
		if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		if (!helper.checkUserPermission("assign_user_menu", session)) {
			return "nopermission";
		}
		
		//fetch all user roles except super user role
		List<UserRoleSchoolDTO> userRoles = userRoleSchoolDao.findAll(true, false);
		
		model.addAttribute("title","Assign Menu");
		model.addAttribute("userRoles",userRoles);
		
		return "user-role-menu/assign";
	}
	
	@RequestMapping(value = "populatemenus", method = RequestMethod.POST)
	public ResponseEntity<Response> fetchModules(HttpSession session, @RequestParam(required = false) Long userRoleId) {
		boolean success = false;
		String message = null;
		StringBuilder builder = null;
		
		Long packageTemplateInfoId = 0001L;
		if (packageTemplateInfoId != null) {
			//fetch all package related modules
			List<ModuleNewDTO> moduleNewDTOs = moduleNewDao.findAllByPackageId(packageTemplateInfoId);
			moduleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
			
			//fetch all selected module ids
			UserRoleMenuInfo userRoleMenuInfo = userRoleMenuInfoDao.selectByUserRoleId(userRoleId);
			List<Long> seletedModuleIds = null;
			if (userRoleMenuInfo != null)
				seletedModuleIds = userRoleMenuInfoDao.findAllAssignedModuleIdByUserRoleMenuInfoId(userRoleMenuInfo.getId());
			if (seletedModuleIds == null) seletedModuleIds = new ArrayList<>();
			
			if (moduleNewDTOs != null) {				
				builder = new StringBuilder();
				int  i = 1;
				
				String checked = "";
				for (ModuleNewDTO module : moduleNewDTOs) {
					Integer moduleRowSpan = module.getSubModules() != null ? module.getSubModules().size() : 0;
					if (moduleRowSpan == 0) moduleRowSpan = 1;
					
					List<ModuleNewDTO> subModules = module.getSubModules();
					Integer totalSubModules = subModules != null ? subModules.size() : 0;
					
					checked = seletedModuleIds.contains(module.getId().longValue()) ? "checked" : "";
					
					builder.append("<tr>");
					builder.append("<td rowspan='"+ moduleRowSpan +"'>"+ i++ +".</td>");
					builder.append("<td rowspan='"+ moduleRowSpan +"'><label><input "+ checked +" type='checkbox' name='menuId' class='module' value='"+ module.getId() +"'> <i class='material-icons'>"+ module.getIcon() +"</i>"+ module.getName() +"</label></td>");
					
					ModuleNewDTO firstSubModule = null;
					if (totalSubModules > 0) {
						firstSubModule = subModules.get(0);
						checked = seletedModuleIds.contains(firstSubModule.getId().longValue()) ? "checked" : "";
						builder.append("<td><label><input "+ checked +" type='checkbox' class='subModule' name='menuId' value='"+ firstSubModule.getId() +"' module-value='"+ module.getId() +"'> <i class='material-icons'>"+ firstSubModule.getIcon() +"</i>"+ firstSubModule.getName() +"</label></td>");
					} else builder.append("<td></td>");
					
					//append first sub-module related permission(s) to td if available
					builder.append("<td>");
					if (firstSubModule != null && firstSubModule.getSubModules() != null)
						for (ModuleNewDTO menuLine : firstSubModule.getSubModules()) {
							checked = seletedModuleIds.contains(menuLine.getId().longValue()) ? "checked" : "";
							builder.append("<label class='ml-15'><input "+ checked +" type='checkbox' module-value='"+ module.getId() +"' sub-module-value='"+ firstSubModule.getId() +"' class='menuline' name='menuId' value='"+ menuLine.getId() +"'> <i class='material-icons'>"+ menuLine.getIcon() +"</i>"+ menuLine.getName() +"</label>");
						}
					builder.append("</div></td>");
					builder.append("</tr>");
					
					//first sub-module is already appended on parent row. if sub-modules are more than 1 then loop other sub-modules.
					if (totalSubModules > 1) {
						int index = 0;
						for (ModuleNewDTO subModule : subModules) {
							if (index == 0) {
								index++;
								continue;
							}

							checked = seletedModuleIds.contains(subModule.getId().longValue()) ? "checked" : "";
							
							builder.append("<tr>");
							builder.append("<td><label><input "+ checked +" type='checkbox' name='menuId' value='"+ subModule.getId() +"' class='subModule' module-value='"+ module.getId() +"'> <i class='material-icons'>"+ subModule.getIcon() +"</i>"+ subModule.getName() +"</label></td>");
							
							builder.append("<td>");
							
							//append related menu-lines to single td
							if (subModule.getSubModules() != null)
								for (ModuleNewDTO menuLine : subModule.getSubModules()) {
									checked = seletedModuleIds.contains(menuLine.getId().longValue()) ? "checked" : "";
									builder.append("<label class='ml-15'><input "+ checked +" type='checkbox' name='menuId' module-value='"+ module.getId() +"' sub-module-value='"+ subModule.getId() +"' class='menuline' value='"+ menuLine.getId() +"'> <i class='material-icons'>"+ menuLine.getIcon() +"</i>"+ menuLine.getName() +"</label>");
								}
							
							builder.append("</td>");
							builder.append("</tr>");
						}
					}
					
				}
				
				success = true;
			}
			
		} else message = "Software package has not been assigned to your account. Please contact Digital Nepal Support Team.";

		Response response = new Response(builder != null ? builder.toString() : null, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="assign/menu", method=RequestMethod.POST)
	public String saveUserRoleMenu(HttpSession session,RedirectAttributes redirectAttributes, @RequestParam Long roleId,
		@RequestParam(value = "menuId", required = false) List<Long> menuIds, @RequestParam(value = "remark", required = false) String remarks) {
		
		// check user login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		if (!helper.checkUserPermission("assign_user_menu", session)) {
			return "nopermission";
		}
		
		UserRoleMenuInfo userRoleMenuInfo = userRoleMenuInfoDao.selectByUserRoleId(roleId);
		boolean saved_status = true;
		
		if(userRoleMenuInfo != null){			
			//variable to store assigned module ids
			List<Long> assignedModuleIds = new ArrayList<>();
			
			//store user role menu on the basis of module id
			Map<Long, UserRoleMenu> userRoleMenuMap = new HashMap<>();//structure : Map<moduleNewId, UserRoleMenu>
			if (userRoleMenuInfo.getUserRoleMenus() != null) {
				userRoleMenuInfo.getUserRoleMenus().forEach(userRoleMenu -> {
					userRoleMenuMap.put(userRoleMenu.getModuleNew().getId(), userRoleMenu);
					assignedModuleIds.add(userRoleMenu.getModuleNew().getId());
				});
			}
						
			List<UserRoleMenu> userRoleMenus = new ArrayList<>();
			if (menuIds != null) {
				for (Long moduleId : menuIds) {
					UserRoleMenu userRoleMenu = userRoleMenuMap.get(moduleId);
					
					if (userRoleMenu == null) {
						userRoleMenu = new UserRoleMenu();
						
						ModuleNew moduleNew = new ModuleNew();
						moduleNew.setId(moduleId);
						
						userRoleMenu.setModuleNew(moduleNew);
						userRoleMenu.setUserRoleMenuInfo(userRoleMenuInfo);
					}
					
					userRoleMenus.add(userRoleMenu);
				}
			}
			
			//store deletable module id if saved menu's module id not present on current update
			List<Long> deletableUserRoleMenuModuleIds = new ArrayList<>();
			for (Long moduleId : assignedModuleIds) {
				if (!menuIds.contains(moduleId)) {
					deletableUserRoleMenuModuleIds.add(moduleId);
				}
			}
						
			userRoleMenuInfo.setUserRoleMenus(userRoleMenus);
			userRoleMenuInfo.setRemarks(remarks);
			userRoleMenuInfo.setModifiedDate(Helper.getCurrentDateTime());
			
			Admin admin = SessionHelper.getLoggedInAdmin(session);
			userRoleMenuInfo.setModifiedBy(admin);
			
			if (userRoleMenuInfoDao.update(userRoleMenuInfo)) {
				//delete all related menu having module id in deletableUserRoleMenuModuleIds list
				if (deletableUserRoleMenuModuleIds.size() > 0) {
					userRoleMenuInfoDao.deleteAllMenuByUserRoleMenuInfoIdAndModuleIds(userRoleMenuInfo.getId(), deletableUserRoleMenuModuleIds);
				}
			}				
		}else {
			
			//save user role menu when it have not saved before
			userRoleMenuInfo = new UserRoleMenuInfo();
			Admin admin = SessionHelper.getLoggedInAdmin(session);
			UserRoleSchool userRoleSchool = userRoleSchoolDao.selectById(roleId);
			userRoleMenuInfo.setRemarks(remarks);
			userRoleMenuInfo.setCreatedDate(Helper.getCurrentDateTime());
			userRoleMenuInfo.setCreatedBy(admin);
			userRoleMenuInfo.setUserRoleSchool(userRoleSchool);
			
			ModuleNew moduleNew = null;
			List<UserRoleMenu> userRoleMenus = new ArrayList<>();
			for (Long menuId : menuIds) {
				if(menuId != null) {
					UserRoleMenu userRoleMenu = new UserRoleMenu();
					moduleNew = moduleNewDao.findById(menuId);
					userRoleMenu.setModuleNew(moduleNew);
					userRoleMenu.setUserRoleMenuInfo(userRoleMenuInfo);
					userRoleMenus.add(userRoleMenu);
				}
			}
			
			userRoleMenuInfo.setUserRoleMenus(userRoleMenus);
			Long id = userRoleMenuInfoDao.save(userRoleMenuInfo);
			if(id.compareTo(0L) <= 0) {
				saved_status = false;
			}
		}
		
		if(saved_status) {			
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("success", "User role menu assigned successfully."));
		}else {
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("error", "Sorry but failed to assign user role menu."));
		}
			  
		return "redirect:/user/role/";
	 }
	
	/*
	 * Required last dao function from module new
	 */
	
	@RequestMapping(value="/viewallassignedmenus", method=RequestMethod.POST)
	public ResponseEntity<Response> populateAllAssignedMenuForInfo(HttpSession session, @RequestParam Long userRoleId) {
		if(!SessionHelper.isAdminLoggedIn(session))
			return new ResponseEntity<>(new Response(null, Strings.SESSION_EXPIRED, false), HttpStatus.OK);
		
		StringBuilder html = new StringBuilder();
		UserRoleMenuInfo userRoleMenuInfo = userRoleMenuInfoDao.selectByUserRoleId(userRoleId);
		
		//selected module ids
		List<Long> seletedModuleIds = null;
		if (userRoleMenuInfo != null)
			seletedModuleIds = userRoleMenuInfoDao.findAllAssignedModuleIdByUserRoleMenuInfoId(userRoleMenuInfo.getId());
		if (seletedModuleIds == null) seletedModuleIds = new ArrayList<>();
		
		List<ModuleNewDTO> moduleNewDTOs = userRoleMenuInfo != null ? moduleNewDao.findAllSelectedByUserRoleMenuId(userRoleMenuInfo.getId()) : null;		
		List<ModuleNewDTO> finalModuleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
		
		int i =1;
		if(finalModuleNewDTOs != null) {
			for (ModuleNewDTO moduleNewDTO : finalModuleNewDTOs) {
				int parentRowSpan = 1;
				
				if (moduleNewDTO.getSubModules() != null) {
					for (ModuleNewDTO subModule : moduleNewDTO.getSubModules()) {	
						if(subModule.getSubModules() != null) parentRowSpan += subModule.getSubModules().size();
						else parentRowSpan +=1;
					}
				}
				
				if(parentRowSpan>1) parentRowSpan--;
				
				if(seletedModuleIds.contains(moduleNewDTO.getId().longValue())) {	
					
					html.append("<tr>").
							append("<td rowspan='" + parentRowSpan + "' class='text-center'>"+ i++ +"</td>");
					html.append("<td rowspan='" + parentRowSpan + "'><input type='checkbox' name='moduleId' value='"+ moduleNewDTO.getId() +"' disabled checked>&nbsp;<i class='material-icons'>"+ moduleNewDTO.getIcon()+"</i>&nbsp;" + moduleNewDTO.getName() +"</td>");	
				} else { 
					continue;
				}
				
				if(moduleNewDTO.getSubModules() != null) {
					
					Boolean firstSubModule = true;
					
					for (ModuleNewDTO subModule : moduleNewDTO.getSubModules()) {						
						
						if(!firstSubModule) html.append("</tr><tr>");						
						
						int rowSpan = subModule.getSubModules() != null  && subModule.getSubModules().size() > 0 ? subModule.getSubModules().size() : 1;
						
						if(seletedModuleIds.contains(subModule.getId().longValue())) {
							html.append("<td rowspan='" + rowSpan + "'><input type='checkbox' name='subModuleId' value='"+ subModule.getId() +"' disabled checked>&nbsp;<i class='material-icons'>"+ subModule.getIcon()+"</i>&nbsp;" + subModule.getName() +"</td>");	
						}
					
						if(subModule.getSubModules() != null) {
							Boolean firstMenuLine=true;
							
							for(ModuleNewDTO menuLine : subModule.getSubModules()) {
								if(!firstMenuLine) html.append("</tr><tr>");
								
								if(seletedModuleIds.contains(menuLine.getId().longValue()))
									html.append("<td><input type='checkbox'  name='menuLineId' value='"+ menuLine.getId() +"' disabled checked>&nbsp;<i class='material-icons'>"+ menuLine.getIcon() +"</i>&nbsp;" + menuLine.getName() +"</td>");	
								
								firstMenuLine=false;
							}							
						} else {
							html.append("<td></td></tr>");
						}
						firstSubModule=false;
					}
				} else {
					html.append("<td></td><td></td></tr>");
				}
			}
		}
		
		return new ResponseEntity<>(new Response(html, null, true), HttpStatus.OK);
	}
	
}
