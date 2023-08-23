package com.example.edoc.controller;

import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.SpecializationDao;
import com.example.edoc.models.Specialization;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import com.example.edoc.util.Strings;
import com.example.edoc.util.dateutil.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "specialization")
public class SpecializationController {
    private static final Logger logger =  LoggerFactory.getLogger(SpecializationController.class);
    @Autowired
    Helper helper;

    @Autowired
    SpecializationDao specializationDao;

    String login = null;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (SessionHelper.isAdminLoggedIn(session)) {
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            login="doctor";
        }

        model.addAttribute("Heading",login );

        List<Specialization> specializations= specializationDao.findAll();
        model.addAttribute("specializations",specializations);

        return "specialization/index";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpdatePersonalDetails(@ModelAttribute Specialization specialization,HttpSession session,
                                                                RedirectAttributes redirectAttributes) {

        boolean success = false;
        String message = null;

        try {

            specialization.setCreatedDate(new Date());


                if (specializationDao.save(specialization)) {
                    success = true;
                    message = "patient details saved successfully.";


                } else
                    message = "Sorry but failed to save.";


        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }
        Response response = new Response(null, message, success);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<Response> deleteSpecialization(@RequestParam Long id, Model model) {

        boolean success = false;
        String message = null;

        if (specializationDao.delete(id)) {
            success = true;
            message = "Patient deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
