package com.example.edoc.controller;

import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.DTO.PackageTemplateBasicDTO;
import com.example.edoc.constants.MessageType;
import com.example.edoc.dao.AdminDao;
import com.example.edoc.dao.ModuleNewDao;
import com.example.edoc.dao.PackageTemplateInfoDao;
import com.example.edoc.models.Admin;
import com.example.edoc.models.ModuleNew;
import com.example.edoc.models.PackageTemplate;
import com.example.edoc.models.PackageTemplateInfo;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import com.example.edoc.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/admin/package/template")
public class PackageTemplateController {
	
	@Autowired
    ModuleNewDao moduleNewDao;
	@Autowired
    Helper helper;
	@Autowired
    PackageTemplateInfoDao packageTemplateInfoDao;
	@Autowired
    AdminDao adminDao;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		// checking user login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		model.addAttribute("title", "Package Templates");
		//model.addAttribute("packageTemplateInfos", packageTemplateInfoDao.findAllPackageTemplateInfo());
		model.addAttribute("packageTemplateInfos", packageTemplateInfoDao.findAll());
		return "package-template/index";
	}
	
	@RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
	public String viewPackageTemplate(HttpSession session, RedirectAttributes redirectAttributes, Model model,
			@PathVariable("id") Long id) {
		// checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		PackageTemplateInfo packageTemplateInfo = packageTemplateInfoDao.findById(id);

		if (packageTemplateInfo == null) {
			return "redirect:/admin/package/template";
		}
		
		model.addAttribute("packageTemplateInfo", packageTemplateInfo);
		model.addAttribute("title", "View Package Template");
		return "package-template/view";
	}
	
	@RequestMapping(value="/populatepackagetemplatesforview", method=RequestMethod.POST)
	public ResponseEntity<Response> populatePackageTemplatesForView(HttpSession session, RedirectAttributes rdr,
                                                                    @RequestParam("id") Long id) {
		
		if(!SessionHelper.isAdminLoggedIn(session)) {
			return new ResponseEntity<>(new Response(null, Strings.SESSION_EXPIRED, false), HttpStatus.OK);
		}
		
		List<PackageTemplateBasicDTO> moduleNewIds = packageTemplateInfoDao.getAllModuleIdsByPackageTemplateInfoId(id);
		
		List<BigInteger> seletedModuleIds = new ArrayList<>();
		
		for (PackageTemplateBasicDTO packageTemplateDTO : moduleNewIds) {
			seletedModuleIds.add(packageTemplateDTO.getMenuId());
		}
		
		List<ModuleNewDTO> finalModuleNewDTOs = null;
		
		List<ModuleNewDTO> moduleNewDTOs = moduleNewDao.findAllSelected(id);
		finalModuleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
		
		StringBuilder html = new StringBuilder();
		
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
				
				if(seletedModuleIds.contains(moduleNewDTO.getId())) {	
					
					html.append("<tr>").
							append("<td rowspan='" + parentRowSpan + "' class='text-center'>"+ i++ +"</td>");
					html.append("<td rowspan='" + parentRowSpan + "'><input type='checkbox' class='category' name='moduleId' value='"+ moduleNewDTO.getId() +"' disabled checked>&nbsp;<i class='material-icons'>"+ moduleNewDTO.getIcon()+"</i>&nbsp;" + moduleNewDTO.getName() +"</td>");	
				} else { 
					continue;
				}
				
				if(moduleNewDTO.getSubModules() != null) {
					
					Boolean firstSubModule = true;
					
					for (ModuleNewDTO subModule : moduleNewDTO.getSubModules()) {						
						
						if(!firstSubModule) html.append("</tr><tr>");						
						
						int rowSpan = subModule.getSubModules() != null  && subModule.getSubModules().size() > 0 ? subModule.getSubModules().size() : 1;
						
						if(seletedModuleIds.contains(subModule.getId())) {
							html.append("<td rowspan='" + rowSpan + "'><input type='checkbox' class='subCategory' name='subModuleId' value='"+ subModule.getId() +"' disabled checked>&nbsp;<i class='material-icons'>"+ subModule.getIcon()+"</i>&nbsp;" + subModule.getName() +"</td>");	
						}
					
						if(subModule.getSubModules() != null) {
							Boolean firstMenuLine=true;
							
							for(ModuleNewDTO menuLine : subModule.getSubModules()) {
								if(!firstMenuLine) html.append("</tr><tr>");
								
								if(seletedModuleIds.contains(menuLine.getId())) {
									html.append("<td><input type='checkbox' class='gradingParameter' name='menuLineId' value='"+ menuLine.getId() +"' disabled checked>&nbsp;<i class='material-icons'>"+ menuLine.getIcon() +"</i>&nbsp;" + menuLine.getName() +"</td>");	
								}
								
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
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String loadViewPackageTemplate(HttpSession session,RedirectAttributes rdr,Model model) {
		
		//checking user login
		if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, rdr);
			return "redirect:/admin/login";
		}
		
		List<ModuleNewDTO> moduleNewDTOs = moduleNewDao.findAllActive();
		List<ModuleNewDTO> finalModuleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
		
		model.addAttribute("title","Add Package Template");	
		model.addAttribute("finalModuleNewDTOs", finalModuleNewDTOs);
		
		return "package-template/create";
	}
	
	@RequestMapping(value="/populatepackagetemplates", method=RequestMethod.POST)
	public ResponseEntity<Response> populatePackageTemplates(HttpSession session,RedirectAttributes rdr) {
		
		if(!SessionHelper.isAdminLoggedIn(session)) {
			return new ResponseEntity<>(new Response(null, Strings.SESSION_EXPIRED, false), HttpStatus.OK);
		}
		
		List<ModuleNewDTO> finalModuleNewDTOs = null;
		
		List<ModuleNewDTO> moduleNewDTOs = moduleNewDao.findAllActive();
		finalModuleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
		
		StringBuilder html = new StringBuilder();
		
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
				
				html.append("<tr>").
						append("<td rowspan='" + parentRowSpan + "' class='text-center'>"+ i++ +"</td>");
				html.append("<td rowspan='" + parentRowSpan + "'><label><input type='checkbox' class='category' name='menuId' value='"+ moduleNewDTO.getId() +"'>&nbsp;<i class='material-icons'>"+ moduleNewDTO.getIcon() +"</i>&nbsp;" + moduleNewDTO.getName() +"</label></td>");
				
				if(moduleNewDTO.getSubModules() != null) {
					
					Boolean firstSubModule = true;
					
					for (ModuleNewDTO subModule : moduleNewDTO.getSubModules()) {						
						
						if(!firstSubModule) html.append("</tr><tr>");						
						
						int rowSpan = subModule.getSubModules() != null  && subModule.getSubModules().size() > 0 ? subModule.getSubModules().size() : 1;
						
						html.append("<td rowspan='" + rowSpan + "'><label><input type='checkbox' class='subCategory' name='menuId' value='"+ subModule.getId() +"' category-value='"+ moduleNewDTO.getId() +"'>&nbsp;<i class='material-icons'>"+ subModule.getIcon() +"</i>&nbsp;" + subModule.getName() +"</label></td>");
					
						if(subModule.getSubModules() != null) {
							Boolean firstMenuLine=true;
							
							for(ModuleNewDTO menuLine : subModule.getSubModules()) {
								if(!firstMenuLine) html.append("</tr><tr>");
								
								html.append("<td><label><input type='checkbox' class='gradingParameter' name='menuId' value='"+ menuLine.getId() +"' category-value='"+ moduleNewDTO.getId() +"' sub-category-value='"+ subModule.getId() +"'>&nbsp;<i class='material-icons'>"+ menuLine.getIcon() +"</i>&nbsp;" + menuLine.getName() +"</label></td>");
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
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String savePackageTemplates(HttpSession session,RedirectAttributes rdr, 
		@RequestParam(value = "menuId", required = false) List<Long> menuIds, @RequestParam(value = "remark", required = false) String remarks, 
		@RequestParam(value = "packageTemplateName", required = false) String name) {
			if(!SessionHelper.isAdminLoggedIn(session)) {
				Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, rdr);
				return "redirect:/admin/login";
			}
					
			Admin createdBy = SessionHelper.getLoggedInAdmin(session);
			
			PackageTemplateInfo packageTemplateInfo = new PackageTemplateInfo();
			packageTemplateInfo.setRemarks(remarks);
			packageTemplateInfo.setName(name);
			packageTemplateInfo.setCreatedDate(Helper.getCurrentDateTime());
			packageTemplateInfo.setCreatedBy(createdBy);
						
			List<PackageTemplate> packageTemplates = new ArrayList<>();
			
			for (Long menuId : menuIds) {
				if(menuId != null) {
					PackageTemplate packageTemplate = new PackageTemplate();
					ModuleNew moduleNew = moduleNewDao.findById(menuId);
					packageTemplate.setModuleNew(moduleNew);
					packageTemplate.setPackageTemplateInfo(packageTemplateInfo);
					packageTemplates.add(packageTemplate);
				}
			}
			
		  packageTemplateInfo.setPackageTemplates(packageTemplates);
		
		  Long id = packageTemplateInfoDao.save(packageTemplateInfo);
		  
		  if (id > 0) { 
			  Helper.getFlashMessage(MessageType.ERROR, "Package Template Info saved successfully");
		  }
		  
		return "redirect:/admin/package/template";
	}
	
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String loadViewEditPackagetemplates(HttpSession session, RedirectAttributes redirectAttributes, Model model, @PathVariable("id") Long id) {
		// checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}
		
		PackageTemplateInfo packageTemplateInfo = packageTemplateInfoDao.findById(id);

		if (packageTemplateInfo == null) {
			return "redirect:/admin/package/template";
		}
		
		model.addAttribute("packageTemplateInfo", packageTemplateInfo);
		model.addAttribute("title", "Edit Package Template");
		return "package-template/edit";
	}
	
	@RequestMapping(value="/populatepackagetemplatesforedit", method=RequestMethod.POST)
	public ResponseEntity<Response> populatePackageTemplatesForEdit(HttpSession session,RedirectAttributes rdr, 
			@RequestParam("id") Long id) {
		
		if(!SessionHelper.isAdminLoggedIn(session)) {
			return new ResponseEntity<>(new Response(null, Strings.SESSION_EXPIRED, false), HttpStatus.OK);
		}
		
		List<PackageTemplateBasicDTO> moduleNewIds = packageTemplateInfoDao.getAllModuleIdsByPackageTemplateInfoId(id);
		
		List<BigInteger> seletedModuleIds = new ArrayList<>();
		
		for (PackageTemplateBasicDTO packageTemplateDTO : moduleNewIds) {
			seletedModuleIds.add(packageTemplateDTO.getMenuId());
		}
		
		List<ModuleNewDTO> finalModuleNewDTOs = null;
		
		List<ModuleNewDTO> moduleNewDTOs = moduleNewDao.findAllActive();
		finalModuleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
		
		StringBuilder html = new StringBuilder();
		
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
				
				html.append("<tr>").
						append("<td rowspan='" + parentRowSpan + "' class='text-center'>"+ i++ +"</td>");
				
				if(seletedModuleIds.contains(moduleNewDTO.getId())) {
					html.append("<td rowspan='" + parentRowSpan + "'><label><input type='checkbox' class='category' name='menuId' value='"+ moduleNewDTO.getId() +"' checked>&nbsp;<i class='material-icons'>"+ moduleNewDTO.getIcon() +"</i>&nbsp;" + moduleNewDTO.getName() +"</label></td>");	
				}else {
					html.append("<td rowspan='" + parentRowSpan + "'><label><input type='checkbox' class='category' name='menuId' value='"+ moduleNewDTO.getId() +"'>&nbsp;<i class='material-icons'>"+ moduleNewDTO.getIcon() +"</i>&nbsp;" + moduleNewDTO.getName() +"</label></td>");
				}
				
				if(moduleNewDTO.getSubModules() != null) {
					
					Boolean firstSubModule = true;
					
					for (ModuleNewDTO subModule : moduleNewDTO.getSubModules()) {						
						
						if(!firstSubModule) html.append("</tr><tr>");						
						
						int rowSpan = subModule.getSubModules() != null  && subModule.getSubModules().size() > 0 ? subModule.getSubModules().size() : 1;
						
						if(seletedModuleIds.contains(subModule.getId())) {
							html.append("<td rowspan='" + rowSpan + "'><label><input type='checkbox' class='subCategory' name='menuId' value='"+ subModule.getId() + "' category-value='"+ moduleNewDTO.getId() + "' checked>&nbsp;<i class='material-icons'>"+ subModule.getIcon() +"</i>&nbsp;" + subModule.getName() +"</label></td>");	
						}else {
							html.append("<td rowspan='" + rowSpan + "'><label><input type='checkbox' class='subCategory' name='menuId' value='"+ subModule.getId() + "' category-value='"+ moduleNewDTO.getId() + "'>&nbsp;<i class='material-icons'>"+ subModule.getIcon() +"</i>&nbsp;" + subModule.getName() +"</label></td>");
						}
					
						if(subModule.getSubModules() != null) {
							Boolean firstMenuLine=true;
							
							for(ModuleNewDTO menuLine : subModule.getSubModules()) {
								if(!firstMenuLine) html.append("</tr><tr>");
								
								if(seletedModuleIds.contains(menuLine.getId())) {
									html.append("<td><label><input type='checkbox' class='gradingParameter' name='menuId' value='"+ menuLine.getId() + "' category-value='"+ moduleNewDTO.getId() +"' sub-category-value='"+ subModule.getId() + "' checked> &nbsp;<i class='material-icons'>"+ menuLine.getIcon() + "</i>&nbsp;" + menuLine.getName() +"</label></td>");	
								}else {
									html.append("<td><label><input type='checkbox' class='gradingParameter' name='menuId' value='"+ menuLine.getId() + "' category-value='"+ moduleNewDTO.getId() +"' sub-category-value='"+ subModule.getId() + "'> &nbsp;<i class='material-icons'>"+ menuLine.getIcon() + "</i>&nbsp;" + menuLine.getName() +"</label></td>");
								}
								
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
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
	public String editPackageTemplates(HttpSession session,RedirectAttributes redirectAttributes, 
		@RequestParam(value = "menuId", required = false) List<Long> menuIds, @RequestParam(value = "remark", required = false) String remarks, 
		@RequestParam(value = "packageTemplateName", required = false) String name, @RequestParam(value = "id", required = false) Long id) {
		
			if(!SessionHelper.isAdminLoggedIn(session)) {
				Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
				return "redirect:/admin/login";
			}
		
			ModuleNew moduleNew = null;
			Map<Long, Long> map = new HashMap<>();
			List<Long> packageTemplateIds = new ArrayList<>();
			PackageTemplateInfo packageTemplateInfo = packageTemplateInfoDao.findById(id);
			Admin modifiedBy = adminDao.selectAdminByUsername(session.getAttribute("admin_username").toString());
			
			packageTemplateInfo.setRemarks(remarks);
			packageTemplateInfo.setName(name);
			packageTemplateInfo.setModifiedDate(Helper.getCurrentDateTime());
			packageTemplateInfo.setModifiedBy(modifiedBy);
			
			Map<Long, PackageTemplate> selectedPackageTemplates = new HashMap<>();
			for (PackageTemplate packageTemplate : packageTemplateInfo.getPackageTemplates()) {
				map.put(packageTemplate.getModuleNew().getId() , packageTemplate.getId());
				packageTemplateIds.add(packageTemplate.getId());
				selectedPackageTemplates.put(packageTemplate.getId(), packageTemplate);
			}
			
			List<PackageTemplate> packageTemplates = new ArrayList<>();
			List<Long> selectedPackageTemplateIds = new ArrayList<>();
			
			if(menuIds != null) {
				for (Long menuId : menuIds) {
					if(menuId != null) {
						
						PackageTemplate packageTemplate = map.containsKey(menuId) ? selectedPackageTemplates.get(map.get(menuId)) :  new PackageTemplate();
						
						if(packageTemplate.getId() != null) {
							selectedPackageTemplateIds.add(packageTemplate.getId());
						}
						
						moduleNew = moduleNewDao.findById(menuId);
						packageTemplate.setModuleNew(moduleNew);
						packageTemplate.setPackageTemplateInfo(packageTemplateInfo);
						packageTemplates.add(packageTemplate);
					}
				}
			}
			
			List<Long> newPackageTemplateId = new ArrayList<>();
			
			for (Long packageTemplateId : packageTemplateIds) {
				if (!selectedPackageTemplateIds.contains(packageTemplateId))
					newPackageTemplateId.add(packageTemplateId);
			}
			
			packageTemplateInfo.setPackageTemplates(packageTemplates);

			if (packageTemplateInfoDao.update(packageTemplateInfo)) {
				if(newPackageTemplateId.size() > 0)
					packageTemplateInfoDao.deleteRemovedPackageTemplatesFromPackageTemplatesInfo(newPackageTemplateId);
								
				Helper.setFlashMessage(MessageType.SUCCESS, "Package Template Info updated successfully.", redirectAttributes);
			} else {
				Helper.setFlashMessage(MessageType.SUCCESS, "Sorry but failed to update package template.", redirectAttributes);
			}
		  	
			return "redirect:/admin/package/template";
	}
	
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String deletePackageTemplates(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
		// checking user login
		if(!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
			return "redirect:/admin/login";
		}

		PackageTemplateInfo packageTemplateInfo = packageTemplateInfoDao.findById(id);

		if (packageTemplateInfo == null) {
			Helper.setFlashMessage(MessageType.ERROR, Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION, redirectAttributes);
			return "redirect:/admin/package/template";
		}

		if (packageTemplateInfo != null) {
			try {
				if (packageTemplateInfoDao.delete(packageTemplateInfo)) {
					Helper.setFlashMessage(MessageType.SUCCESS, Strings.DELETED_SUCCESSFULLY, redirectAttributes);
									} else Helper.setFlashMessage(MessageType.ERROR, Strings.FAILED_TO_DELETE, redirectAttributes);
			} catch(Exception e) {
				Helper.setFlashMessage(MessageType.ERROR, "It's a parent data and has been used in other module!", redirectAttributes);
			}
		}
		
		return "redirect:/admin/package/template";
	}


}