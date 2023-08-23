package com.example.edoc.controller;

import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.DTO.DoctorSpecializationDTO;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.DoctorDao;
import com.example.edoc.dao.DoctorSpecializationDao;
import com.example.edoc.dao.SpecializationDao;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.DoctorSpecialization;
import com.example.edoc.models.Specialization;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import com.example.edoc.util.Strings;
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
@RequestMapping(value = "specialization/doctor")
public class DoctorSpecializationController {

    @Autowired
    DoctorSpecializationDao doctorSpecializationDao;
    @Autowired
    Helper helper;
    @Autowired
    DoctorDao doctorDao;
    @Autowired
    SpecializationDao specializationDao;

    String login = null ;

    private static final Logger logger =  LoggerFactory.getLogger(SpecializationController.class);


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (SessionHelper.isAdminLoggedIn(session)) {
            login="admin";
            List<DoctorSpecializationDTO> doctorSpecializations= doctorSpecializationDao.findAllByDoctorAndSpecialization();
            model.addAttribute("doctorSpecializations",doctorSpecializations);

            List<Doctor> doctorList = doctorDao.findAll();
            model.addAttribute("doctorList",doctorList);

        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            List<DoctorSpecializationDTO> doctorSpecializations= doctorSpecializationDao.findAllByDoctorAndSpecialization();
            model.addAttribute("doctorSpecializations",doctorSpecializations);

            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<DoctorSpecializationDTO> doctorSpecializations= doctorSpecializationDao.findByDoctorIDAndSpecialization(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("doctorSpecializations",doctorSpecializations);

            List<DoctorDTO> doctorList = doctorDao.findDoctorByID(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("doctorList",doctorList);

            login="doctor";
        }
        model.addAttribute("Heading",login );





        List<Specialization> specializationList = specializationDao.findAll();
        model.addAttribute("specializationList",specializationList);

        return "doctor-specialization/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpdatePersonalDetails(@ModelAttribute DoctorSpecialization doctorSpecialization, HttpSession session,
                                                                RedirectAttributes redirectAttributes,@RequestParam Long doctor_id,@RequestParam Long specialization_id) {

        boolean success = false;
        String message = null;

        try {
            Doctor doctor= doctorDao.findById(doctor_id);
            doctorSpecialization.setDoctor(doctor);
            Specialization specialization = specializationDao.findById(specialization_id);
            doctorSpecialization.setSpecialization(specialization);
            doctorSpecialization.setCreatedDate(new Date());

            if (doctorSpecializationDao.save(doctorSpecialization)) {
                success = true;
                message = "Specialization details saved successfully.";


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

        if (doctorSpecializationDao.delete(id)) {
            success = true;
            message = "Specialization deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
