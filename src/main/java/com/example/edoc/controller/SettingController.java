package com.example.edoc.controller;

import com.example.edoc.DTO.PackageTemplateBasicDTO;
import com.example.edoc.dao.PackageTemplateInfoDao;
import com.example.edoc.dao.SettingDao;
import com.example.edoc.models.PackageTemplateInfo;
import com.example.edoc.models.Settings;
import com.example.edoc.util.Helper;
import com.example.edoc.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value="settings")
public class SettingController {
	
	@Autowired
    SettingDao settingDao;
	@Autowired
    PackageTemplateInfoDao packageTemplateInfoDao;
	
	
	@RequestMapping(value ="", method=RequestMethod.GET)
	public String loadSettingView(HttpSession session, Model model) {
		
		List<PackageTemplateBasicDTO> packageTemplateBasicDTOs  = packageTemplateInfoDao.findAllPackageTemplates();
		model.addAttribute("packages", packageTemplateBasicDTOs);
		
		System.out.println(packageTemplateBasicDTOs);
		
		Settings setting = settingDao.findDate();
		model.addAttribute("setting", setting);
		
		return "settings/settings";
	}

	@RequestMapping(value ="save", method=RequestMethod.POST)
	public String addSetting( HttpSession session, @ModelAttribute Settings sett, RedirectAttributes redirectAttributes, @RequestParam(required = false) Long packageTemplateInfoId) {
		
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", "You must log in first.", redirectAttributes);
			return "redirect:login";
		}
		
		PackageTemplateInfo packageTemplateInfo = null;
		if (packageTemplateInfoId != null) {
			packageTemplateInfo = new PackageTemplateInfo();
			packageTemplateInfo.setId(packageTemplateInfoId);
		}
		
		Settings setting = settingDao.findDate();		
		if (setting != null) {
			setting.setPackageTemplateInfo(packageTemplateInfo);
			setting.setOperationDateSetting(sett.getOperationDateSetting());
			setting.setModifiedDate(new Date());
			setting.setModifiedBy(SessionHelper.getLoggedInAdmin(session));
			settingDao.update(setting);
		} else {
			setting = new Settings();
			setting.setPackageTemplateInfo(packageTemplateInfo);
			setting.setCreatedDate(new Date());
			setting.setCreatedBy(SessionHelper.getLoggedInAdmin(session));
			settingDao.save(sett);
		}
		
		session.setAttribute("operation_date_setting", sett.getOperationDateSetting());

		return "redirect:/settings/";
	}



}
