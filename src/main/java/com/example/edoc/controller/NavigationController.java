package com.example.edoc.controller;

import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.DTO.UserRoleSchoolDTO;
import com.example.edoc.dao.ModuleNewDao;
import com.example.edoc.dao.UserRoleSchoolDao;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value="/navigation")
public class NavigationController {
	
	@Autowired
    ModuleNewDao moduleNewDao;
	@Autowired
    UserRoleSchoolDao userRoleSchoolDao;
	
	/*@RequestMapping(value="/fetch/v2", method=RequestMethod.POST)
	public ResponseEntity<Response> fetchNavItems(HttpSession session) {
		boolean success = false;
		String message = null;
		
		List<ModuleNewDTO> finalModuleNewDTOs = null;
		if (SessionHelper.isAdminLoggedIn(session)) {
			List<ModuleNewDTO> moduleNewDTOs = null;
			UserRoleSchoolDTO userRoleSchool = userRoleSchoolDao.findBySchoolAccountId(SessionHelper.getLoggedInAdminId(session));
			if (userRoleSchool != null) {
				if (!userRoleSchool.isSuperUserRole()) {
					moduleNewDTOs = moduleNewDao.findAllActiveByUserRoleSchoolId(userRoleSchool.getId().longValue(), false);
				} else {
					*//*moduleNewDTOs = moduleNewDao.findAllDTOs(false);*//*
				}
			}
			
			finalModuleNewDTOs = Helper.extractModuleSubModuleAndMenuLine(moduleNewDTOs);
			success = true;
		}
		
		Response response = new Response(finalModuleNewDTOs, message, success);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}*/
	
}
