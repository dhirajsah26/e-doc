package com.example.edoc.controller;

import com.example.edoc.DTO.DoctorHospitalDTO;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.DoctorDao;
import com.example.edoc.dao.DoctorHospitalDao;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.HospitalAffiliation;
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
@RequestMapping(value = "doctor/hospital")
public class DoctorHospitalController {
    private static final Logger logger =  LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    Helper helper;
    @Autowired
    DoctorHospitalDao doctorHospitalDao;
    @Autowired
    DoctorDao doctorDao;

    String login =null;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        //checking admin login
        if (SessionHelper.isAdminLoggedIn(session)) {
            List<Doctor> doctorList = doctorDao.findAll();
            model.addAttribute("doctorList",doctorList);

            List<DoctorHospitalDTO> doctorHospitalDTOList= doctorHospitalDao.findHospitalAndDoctorName();
            model.addAttribute("doctorHospitalDTOList",doctorHospitalDTOList);

            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<DoctorHospitalDTO> doctorHospitalDTOList= doctorHospitalDao.findHospitalByDoctorID(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("doctorHospitalDTOList",doctorHospitalDTOList);
            login="doctor";
        }
        model.addAttribute("Heading",login );



        return "doctor-hospital/index";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpdatePersonalDetails(@ModelAttribute HospitalAffiliation hospitalAffiliation,
                                                                HttpSession session,
                                                                RedirectAttributes redirectAttributes,
                                                                @RequestParam(required = false) Long doctor_id,
                                                                @RequestParam String startDate,
                                                                @RequestParam String endDate) {

        boolean success = false;
        String message = null;

        try {

            if (SessionHelper.isDoctorLoggedIn(session)){
                Doctor doctor= doctorDao.findById(SessionHelper.getLoggedInDoctorId(session).longValue());
                hospitalAffiliation.setDoctor(doctor);
            }
            else {
                Doctor doctor= doctorDao.findById(doctor_id);
                hospitalAffiliation.setDoctor(doctor);
            }


            hospitalAffiliation.setStartDate(Helper.parseDate(startDate));
            hospitalAffiliation.setEndDate(Helper.parseDate(endDate));
            hospitalAffiliation.setCreatedDate(new Date());


            if (doctorHospitalDao.save(hospitalAffiliation)) {
                success = true;
                message = "Doctor details saved successfully.";


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

        if (doctorHospitalDao.delete(id)) {
            success = true;
            message = "Doctor deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
