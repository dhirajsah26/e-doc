package com.example.edoc.controller;

import com.example.edoc.dao.AdminDao;
import com.example.edoc.dao.SettingDao;
import com.example.edoc.models.Settings;
import com.example.edoc.util.Helper;
import com.example.edoc.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/*
* @author : Sanjay shah
* @date : Sep 18, 2019
*/
@Controller
@RequestMapping(value="admin/old")
public class AdminControllerOld {
	
	@Autowired
    SettingDao settingDao;
	@Autowired
    AdminDao adminDao;

	@RequestMapping(value = "login")
	public String aminLogin(HttpSession session) {
		Settings setting = settingDao.findDate();

		session.setAttribute("admin_id", 1);
			session.setAttribute("admin_logged_in", 1);
			session.setAttribute("admin_email", "admin@gmail.com");
			session.setAttribute("admin_name", "Mr. Admin");
			session.setAttribute("admin_username", "admin");
			session.setAttribute("operation_date_setting", setting != null ? setting.getOperationDateSetting() : "np");
			return "redirect:/admin/home";
		}

	@RequestMapping(value = "/home")
	public String adminHomePage(HttpSession session, RedirectAttributes redirectAttributes) {
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setFlashMessage("error", "You must log in first.", redirectAttributes);
			return "redirect:login";
		}
		
		return "dashboard";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "home";
	}

}



