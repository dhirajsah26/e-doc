package com.example.edoc.controller;

import com.example.edoc.DTO.DashboardDTO;
import com.example.edoc.DTO.UserRoleSchoolDTO;
import com.example.edoc.constants.MessageType;
import com.example.edoc.dao.AdminDao;
import com.example.edoc.dao.SettingDao;
import com.example.edoc.dao.UserRoleSchoolDao;
import com.example.edoc.models.Admin;
import com.example.edoc.models.Settings;
import com.example.edoc.models.UserRoleSchool;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import com.example.edoc.util.dateutil.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Controller
@Transactional
@RequestMapping(value="/admin")
public class AdminController {

	//	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private static final Logger logger = Logger.getLogger(AdminController.class);

	@Resource SessionFactory sessionFactory;
	@Autowired AdminDao adminDao;
	@Autowired Helper helper;
	@Autowired UserRoleSchoolDao userRoleSchoolDao;
	@Autowired
	SettingDao settingDao;
	@Autowired
	DateUtils dateUtils;


	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String loadViewProfile(@PathVariable("username") String username, Model model,
								  RedirectAttributes redirectAttributes, HttpSession session) {
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}

		String loggedAdminUsername = session.getAttribute("admin_username").toString();
		if(Integer.parseInt(session.getAttribute("admin_role_id").toString()) == 1 || loggedAdminUsername.equals(username)) {//admin role id 1 is Super Admin
			Admin admin = adminDao.selectAdminByUsername(loggedAdminUsername);
			model.addAttribute("title", admin.getName());
			model.addAttribute(admin);
			return "admin/profile";
		}

		return "redirect:/admin/" + loggedAdminUsername;
	}

	@RequestMapping(value="/changepwd", method=RequestMethod.GET)
	public String loadViewChangePassword(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}
		logger.info("inside loadViewChangePassword()");
		model.addAttribute("title", "Change Password");
		return "admin/changepwd";
	}

	@RequestMapping(value="/changepwd", method=RequestMethod.POST)
	public String updatePassword(Model model, RedirectAttributes redirectAttributes, HttpSession session,
								 @RequestParam("current_pwd") String currentPassword,
								 @RequestParam("new_pwd") String newPassword,
								 @RequestParam("confirmed_new_pwd") String confirmedNewPassword) {
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}
		logger.info("inside updatePassword()");

		boolean success = false;
		String message = null;
		Admin admin = adminDao.selectAdminById(Integer.parseInt(session.getAttribute("admin_id").toString()));
		if(admin != null) {
			String encryptedPassword = DigestUtils.md5DigestAsHex((admin.getSalt() + currentPassword).getBytes());
			if(encryptedPassword.equals(admin.getPassword())) {
				if(newPassword.equalsIgnoreCase(confirmedNewPassword)) {
					if(!confirmedNewPassword.equals(currentPassword)) {
						String salt = UUID.randomUUID().toString().substring(0, 10);
						String finalPwd = DigestUtils.md5DigestAsHex((salt + confirmedNewPassword).getBytes());
						admin.setPassword(finalPwd);
						admin.setSalt(salt);
						if(adminDao.updateAdmin(admin)) {
							message = "Password has been updated successfully";
							success = true;
						} else {
							message = "Sorry but failed to update password";
						}
					}else {
						message = "Current password and new password must be different.";
					}
				}else {
					message = "New password and re-entered password did not match";
				}
			}else {
				message = "Current password you entered was incorrect.";
			}
		}else {
			message = "No such admin account exists in our database.";
		}

		if(message != null) {
			Helper.setFlashMessage(success ? "success" : "error" , message, redirectAttributes);
		}

		return "redirect:/admin/changepwd";
	}

	@RequestMapping(value="/{username}/resetpwd", method=RequestMethod.GET)
	public String loadViewResetPassword(@PathVariable("username") String username, Model model,
										RedirectAttributes redirectAttributes, HttpSession session) {
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}
		logger.info("inside loadViewResetPassword()");

		if(Integer.parseInt(session.getAttribute("admin_role_id").toString()) != 1) {
			Helper.setFlashMessage("error", "You don't have enough permission to reset password.", redirectAttributes);
			return "redirect:/admin/all";
		}

		Admin admin = adminDao.selectAdminByUsername(username);
		if(admin == null) {
			Helper.setFlashMessage("error", "No such admin account exists in our database", redirectAttributes);
			return "redirect:/admin/all";
		}

		model.addAttribute("title", "Reset Password");
		model.addAttribute(admin);
		return "admin/resetpwd";
	}

	@RequestMapping(value="/{username}/resetpwd", method=RequestMethod.POST)
	public String resetPassword(@PathVariable("username") String username, Model model,
								RedirectAttributes redirectAttributes, HttpSession session,
								@RequestParam("new_pwd") String newPassword,
								@RequestParam("confirmed_new_pwd") String confirmedNewPassword) {
		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			redirectAttributes.addFlashAttribute("message",Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}
		logger.info("inside resetPassword()");

		/*if(Integer.parseInt(session.getAttribute("admin_role_id").toString()) != 1) {
			Helper.setFlashMessage("error", "You don't have enough permission to reset password.", redirectAttributes);
			return "redirect:/admin/all";
		}*/

		boolean success = false;
		String message = null;
		Admin admin = adminDao.selectAdminByUsername(username);
		if(admin != null) {
			if(newPassword.equalsIgnoreCase(confirmedNewPassword)) {
				String salt = UUID.randomUUID().toString().substring(0, 10);
				String finalPwd = DigestUtils.md5DigestAsHex((salt + confirmedNewPassword).getBytes());
				admin.setPassword(finalPwd);
				admin.setSalt(salt);
				if(adminDao.updateAdmin(admin)) {
					message = "Password has been reset successfully";
					success = true;
				} else {
					message = "Sorry but failed to update password";
				}
			}else {
				message = "New password and re-entered password did not match";
			}
		}else {
			message = "No such admin account exists in our database.";
		}

		if(message != null) {
			Helper.setFlashMessage(success ? "success" : "error" , message, redirectAttributes);
		}

		return "redirect:/admin/" + username + "/resetpwd";
	}

	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String admins(HttpSession session, RedirectAttributes ra, Model model) {

		/*//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setSessionExpiredFlashMessage(ra);
			return "redirect:/admin/login";
		}*/

		logger.info("inside admins()");

		model.addAttribute("title","Admin List");
		model.addAttribute("admins",adminDao.findAll());
		return "admin/index";
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loadViewLogin(HttpSession session){
		/*if (!SessionHelper.isAdminLoggedIn(session)) {
			return "redirect:/admin/dashboard";
		}*/

		System.out.println("HELLO WORLD:::::: " + logger.isDebugEnabled());;

		logger.info("inside loadViewLogin()");
		logger.debug("getWelcome is executed!!!!!!!");

		return "admin/login";
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(RedirectAttributes rdr,HttpSession session,HttpServletRequest request,
						@RequestParam(required = false) String username, @RequestParam(required = false) String password){

		logger.info("inside login()");

		Admin admin = adminDao.selectAdminByUsername(username);
		if(admin != null) {
			String salt = admin.getSalt();
			String pwd = admin.getPassword();

			Settings setting = settingDao.findDate();
			String final_pwd = DigestUtils.md5DigestAsHex((salt + password).getBytes());

			if(final_pwd.equals(pwd)) {
				session.setAttribute("admin_id",admin.getId());/*
				session.setAttribute("admin_role_id", admin.getRole().getId());*/
				session.setAttribute("admin_logged_in", 1);
				session.setAttribute("admin_name", admin.getName());
				session.setAttribute("admin_username", admin.getUsername());
				session.setAttribute("admin_email", admin.getEmail());
				session.setAttribute("phone", admin.getPhone());
				session.setAttribute("operation_date_setting", setting != null ? setting.getOperationDateSetting() : "np");
				logger.info("login success");
				return "redirect:/admin/dashboard";
			}else {
				logger.info("login fails");
				rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "Username or password was incorrect"));
			}

		}else {
			rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "Username or password was incorrect"));
		}

		return "redirect:/admin/login";
	}

	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String loadViewSignUp(Model model,HttpSession session,RedirectAttributes ra){
		//checking admin login
		/*if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setSessionExpiredFlashMessage(ra);
			return "redirect:/admin/login";
		}
		//check admin role permission to this url
		if(!helper.checkUserPermission("admin_signup", session)) {
			return "nopermission";
		}*/
		logger.info("login loadViewSignUp");

		/*List<UserRoleSchoolDTO> roles = userRoleSchoolDao.findAll(true, true);
		model.addAttribute("roles", roles);*/
		model.addAttribute("title","Admin Sign Up");

		return "admin/signup";
	}

	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String adminSignUp(@ModelAttribute Admin admin,RedirectAttributes rdr,HttpServletRequest request,Model model,
							  HttpSession session/*, @RequestParam(value = "role_id") Long roleId*/) {

		//checking admin login
		/*if (!SessionHelper.isAdminLoggedIn(session)) {
			rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "You must log in first"));
			return "redirect:/admin/login";
		}*/
		logger.info("login adminSignUp");

//		int role_id = Integer.parseInt(request.getParameter("role_id"));
		admin.setStatus(true);

		String salt = UUID.randomUUID().toString().substring(0, 8);
		admin.setSalt(salt);

		admin.setPassword(DigestUtils.md5DigestAsHex((salt + admin.getPassword()).getBytes()));

		/*UserRoleSchool userRoleSchool = new UserRoleSchool();
		userRoleSchool.setId(roleId);
		admin.setRole(userRoleSchool);*/

		int id = adminDao.saveAdmin(admin);

		if(id > 0) {
			logger.info("signup success");
			Helper.setFlashMessage(MessageType.SUCCESS, "Signed up successfully.You may log in now.", rdr);
			return "redirect:/admin/login";
		}

		return "redirect:/admin/signup";
	}

	@RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public String loadViewDashboard(Model model,HttpSession session,RedirectAttributes ra){

		//checking admin login
		/*if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setSessionExpiredFlashMessage(ra);
			return "redirect:/admin/login";
		}*/
		logger.info("loadViewDashboard()");
		//check user role permission in this url
		if(!helper.checkUserPermission("admin_dashboard", session)) {
			return "nopermission";
		}
		BigInteger doctorCount = adminDao.countDoctor();
		model.addAttribute("doctorCount",doctorCount);
		BigInteger patientCount = adminDao.countPatient();
		model.addAttribute("patientCount",patientCount);
		BigInteger specialistCount = adminDao.countSpecialist();
		model.addAttribute("specialistCount",specialistCount);
		BigInteger appointmentCount = adminDao.countAppointment();
		model.addAttribute("appointmentCount",appointmentCount);


		model.addAttribute("title","Dashboard");
		return "dashboard";
	}

	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logOut(HttpSession session) {
		session.invalidate();
		logger.info("logout success");
		return "redirect:/";
	}

	@RequestMapping(value="/nopermission",method=RequestMethod.GET)
	public String noPermission(HttpSession session,RedirectAttributes ra,Model model) {

		//checking admin login
		if (!SessionHelper.isAdminLoggedIn(session)) {
			Helper.setSessionExpiredFlashMessage(ra);
			return "redirect:/admin/login";
		}
		logger.info("noPermission");
		model.addAttribute("title","No Permission");
		return "nopermission";
	}







	@RequestMapping(value="/getBarGraph")
	public ResponseEntity<Response> getDiagnosisByPatientId(Long id, HttpSession session){

		boolean success = false;
		String message = null;

		List<DashboardDTO> dashboardDTOS = adminDao.getCont();
		if (dashboardDTOS!=null)
			success = true;
		Response response = new Response(dashboardDTOS,message,success);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
