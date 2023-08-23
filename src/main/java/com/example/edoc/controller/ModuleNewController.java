package com.example.edoc.controller;

import com.example.edoc.DTO.ModuleNewAdvancedDTO;
import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.dao.AdminDao;
import com.example.edoc.dao.ModuleNewDao;
import com.example.edoc.enums.ModuleRole;
import com.example.edoc.models.Admin;
import com.example.edoc.models.ModuleNew;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="admin/module/v2")
public class ModuleNewController {
	
	@Autowired
    ModuleNewDao moduleNewDao;
	@Autowired
    AdminDao adminDao;
	@Autowired
    Helper settings;

	@RequestMapping(value="create",method=RequestMethod.GET)
	public String loadViewCreateModule(Model model,HttpSession session,RedirectAttributes redirectAttributes){
		
		//checking admin login
		if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		String key = "module_add";
		if(!settings.checkUserPermission(key, session))
			return "admin_nopermission";
		
		model.addAttribute("title","Create Module, Sub-Module & Others");
		return "module-new/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String createModule(@ModelAttribute ModuleNew moduleNew, RedirectAttributes redirectAttributes, Model model, HttpSession session,
                               @RequestParam(required=false) Long moduleId, @RequestParam(required=false) Long subModuleId, @RequestParam(required=false) List<Integer> moduleRoles){
		
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		ModuleNew parentModule = null;
		
		if (subModuleId != null) {
			parentModule = new ModuleNew();
			parentModule.setId(subModuleId);
		} else if (moduleId != null) {
			parentModule = new ModuleNew();
			parentModule.setId(moduleId);
		}
		
		moduleNew.setParentModule(parentModule);
		moduleNew.setCreatedDate(new Date());
		
		Admin admin =  SessionHelper.getLoggedInAdmin(session);
		
		moduleNew.setCreatedBy(admin);
		
		Long id = moduleNewDao.save(moduleNew);
		if (id != null) {			
			Helper.setFlashMessage("success", "Module saved successfully.", redirectAttributes);
		} else {
			Helper.setFlashMessage("success", "Sorry but failed to save.", redirectAttributes);
		}
		
		return "redirect:/admin/module/v2/";
	}
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String loadViewIndexModule(Model model,HttpSession session,RedirectAttributes redirectAttributes){
		
		//checking admin login
		if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		String key = "module_list";
		if(!settings.checkUserPermission(key, session))
			return "admin_nopermission";
		
		List<ModuleNewAdvancedDTO> modules = moduleNewDao.selectAll(false);
		
		model.addAttribute("title","Modules");
//		model.addAttribute("modules", moduleNewDao.findAll(false));
		model.addAttribute("modules", modules);
		return "module-new/index";
	}
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String loadViewEditModule(@PathVariable("id") Long id,Model model,HttpSession session,RedirectAttributes redirectAttributes){
		
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		String key = "module_edit";
		if(!settings.checkUserPermission(key, session))
			return "admin_nopermission";
		
		ModuleNew moduleNew = moduleNewDao.findByIdWithJoins(id);
		if (moduleNew == null) {
			Helper.setFlashMessage("error", Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION, redirectAttributes);
			return "redirect:/admin/module/v2/";
		}
		
		List<ModuleNewDTO> modules = null;
		List<ModuleNewDTO> subModules = null;
		
		if (!moduleNew.getRole().equals(ModuleRole.MODULE)) {
			modules = moduleNewDao.findAllByRoleParentModuleIdAndStatus(ModuleRole.MODULE.toString(), null, false);
			if (moduleNew.getRole().equals(ModuleRole.MENU_LINE)) {
				subModules = moduleNewDao.findAllByRoleParentModuleIdAndStatus(ModuleRole.SUB_MODULE.toString(), moduleNew.getParentModule().getParentModule().getId(), false);
			}
		}
						
		model.addAttribute("title","Edit Module, Sub-Module & Menuline");
		model.addAttribute("module", moduleNew);
		model.addAttribute("modules", modules);
		model.addAttribute("subModules", subModules);
		return "module-new/edit";
	}
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.POST)
	public String updateModule(@PathVariable Long id, @ModelAttribute ModuleNew moduleNew, Model model, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(required=false) Long moduleId, @RequestParam(required=false) Long subModuleId, @RequestParam(required=false) List<Integer> moduleRoles){
		
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		if (id == null) {
			Helper.setFlashMessage("error", Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION, redirectAttributes);
			return "redirect:/admin/module/v2/";
		}
		
		//delete all assigned roles and save new ones
		moduleNewDao.deleteAllAssignedRolesByModuleId(id);
		
		ModuleNew parentModule = null;
		
		if (subModuleId != null) {
			parentModule = new ModuleNew();
			parentModule.setId(subModuleId);
		} else if (moduleId != null) {
			parentModule = new ModuleNew();
			parentModule.setId(moduleId);
		}
		
		moduleNew.setParentModule(parentModule);
		moduleNew.setCreatedDate(Helper.getCurrentDateTime());
		
		moduleNew.setId(id);
		moduleNew.setModifiedDate(Helper.getCurrentDateTime());
		
		Admin admin = SessionHelper.getLoggedInAdmin(session);
		
		moduleNew.setModifiedBy(admin);
		
		if (moduleNewDao.update(moduleNew)) {			
			Helper.setFlashMessage("success", "Module updated successfully.", redirectAttributes);
		} else {
			Helper.setFlashMessage("success", "Sorry but failed to update.", redirectAttributes);
		}
		
		return "redirect:/admin/module/v2/";
	}
	
	@RequestMapping(value="{id}/delete",method=RequestMethod.GET)
	public String deleteModule(@PathVariable("id") Long id,RedirectAttributes redirectAttributes,HttpSession session) {
		
		//checking admin login
		if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		String key = "module_delete";
		if(!settings.checkUserPermission(key, session))
			return "admin_nopermission";
		
		try {
			if (moduleNewDao.deleteById(id)) {				
				Helper.setFlashMessage("success", "Module deleted successfully.", redirectAttributes);
			} else {
				Helper.setFlashMessage("error", "Sorry but failed to delete.", redirectAttributes);
			}
		} catch(Exception e) {
			Helper.setFlashMessage("error", "Sorry but failed to delete.", redirectAttributes);
		}
		
		return "redirect:/admin/module/v2/";
	}
	
	@RequestMapping(value="filter", method=RequestMethod.POST)
	public ResponseEntity<Response> filterByRoleAndStatus(@RequestParam String role, @RequestParam boolean activeOnly, @RequestParam(required=false) Long parentModuleId) {
		boolean success = false;
		String message = null;
		
		List<ModuleNewDTO> moduleNewDTOs = null;
		try {
			moduleNewDTOs = moduleNewDao.findAllByRoleParentModuleIdAndStatus(role, parentModuleId, activeOnly);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		Response response = new Response(moduleNewDTOs, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="help", method=RequestMethod.POST)
	public ResponseEntity<Response> fetchHelpContent(@RequestParam String activeUrl) {		
		String helpContent = moduleNewDao.fetchHelpContentByTargetUrl(activeUrl);
		Response response = new Response(helpContent, null, true);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="fetchallmodule", method=RequestMethod.POST)
	public ResponseEntity<Response> filterAllModule(@RequestParam String role, @RequestParam boolean activeOnly) {
		boolean success = false;
		String message = null;
		
		List<ModuleNewDTO> moduleNewDTOs = null;
		try {
			moduleNewDTOs = moduleNewDao.findAllModule(role, activeOnly);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		Response response = new Response(moduleNewDTOs, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="fetchallmodulerelateddatasbyid", method=RequestMethod.POST)
	public ResponseEntity<Response> fetchallmodulerelateddatasbyid(@RequestParam(value = "moduleId", required = false) Long moduleId) {
		boolean success = false;
		String message = null;
		
		List<ModuleNewAdvancedDTO> moduleNewAdvancedDTOs = null;
		try {
			moduleNewAdvancedDTOs = moduleNewDao.filterByModuleId(moduleId);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		Response response = new Response(moduleNewAdvancedDTOs, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="fetchallparentmodules", method=RequestMethod.POST)
	public ResponseEntity<Response> fetchallparentmodulenames() {
		boolean success = false;
		String message = null;
		
		List<ModuleNewAdvancedDTO> moduleNewDTOs = null;
		try {
			moduleNewDTOs = moduleNewDao.fetchAllParentModules();
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		Response response = new Response(moduleNewDTOs, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="populateselectedmodulenew", method=RequestMethod.POST)
	public ResponseEntity<Response> populateselectedmodulenew(@RequestParam Long id) {
		boolean success = false;
		String message = null;
		
		ModuleNew moduleNew = null;
		
		try {
			moduleNew = moduleNewDao.findById(id);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		Response response = new Response(moduleNew, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="quickupdate", method=RequestMethod.POST)
	public ResponseEntity<Response> quickeditselectedmodulenew(@RequestParam Long id, @RequestParam String name, @RequestParam String icon,
			@RequestParam String targetUrl, @RequestParam String permissionKey, @RequestParam String rank, @RequestParam boolean status,
				@RequestParam boolean viewMenu, HttpSession session) {
		
		boolean success = false;
		String message = null;
		
		
		try {
			Integer adminId = SessionHelper.getLoggedInAdminId(session);
			boolean update = moduleNewDao.quickUpdateModuleNew(id, name, icon, targetUrl, permissionKey, rank, status, viewMenu, new Date(), adminId);
			
			if(update) {
				message = "Module updated successfully.";
				success = true;
			} else {
				message = "Sorry but failed to update.";
			}
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		Response response = new Response(null, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
