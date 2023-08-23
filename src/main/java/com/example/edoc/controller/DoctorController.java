package com.example.edoc.controller;

import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.DTO.DoctorHospitalDTO;
import com.example.edoc.DTO.DoctorQualificationDTO;
import com.example.edoc.DTO.DoctorSpecializationDTO;
import com.example.edoc.constants.MessageType;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.*;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.DoctorSpecialization;
import com.example.edoc.models.Settings;
import com.example.edoc.models.Specialization;
import com.example.edoc.util.*;
import com.example.edoc.util.dateutil.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "doctor")
public class DoctorController {

    private static final Logger logger =  LoggerFactory.getLogger(DoctorController.class);

    @Autowired Helper helper;
    @Autowired DoctorDao doctorDao;
    @Autowired
    DoctorQualificationDao doctorQualificationDao;
    @Autowired
    DoctorHospitalDao doctorHospitalDao;
    @Autowired
    DoctorSpecializationDao doctorSpecializationDao;
    @Autowired SpecializationDao specializationDao;
   

    @Autowired
    SettingDao settingDao;

    String login = null;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {


        if (SessionHelper.isAdminLoggedIn(session)) {
            List<DoctorDTO> doctorList= doctorDao.findAllDoctorWithSpecialization();
            model.addAttribute("doctorList",doctorList);
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            List<DoctorDTO> doctorList= doctorDao.findAllDoctorWithSpecialization();
            model.addAttribute("doctorList",doctorList);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<DoctorDTO> doctor =doctorDao.findAllDoctorWithSpecialization(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("doctorList",doctor);
            login="doctor";
        }

        model.addAttribute("Heading",login );



        return "doctor/index";
    }

    @RequestMapping(value="{id}/details")
    public String doctorDetails(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        //checking admin login
       /* if (!SessionHelper.isAdminLoggedIn(session)) {
            Helper.setSessionExpiredFlashMessage(redirectAttributes);
            return "redirect:/admin/login";
        }*/

        //redirect if null or is already deleted
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

        Doctor doctor = doctorDao.findById(id);
        model.addAttribute("doctor",doctor);

        List<DoctorSpecializationDTO> doctorSpecialization = doctorSpecializationDao.findByDoctorIDAndSpecialization(id);
        model.addAttribute("doctorSpecialization",doctorSpecialization);


        List<DoctorHospitalDTO>  doctorHospitalDTOList=doctorHospitalDao.findHospitalByDoctorID(id);
        model.addAttribute("doctorHospitalDTOList",doctorHospitalDTOList);

        List<DoctorQualificationDTO> doctorQualificationDTOList =doctorQualificationDao.findQualificationByDoctorID(id);
        model.addAttribute("doctorQualificationDTOList",doctorQualificationDTOList);



        return "doctor/details";
    }



    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String loadViewDoctor(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
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

        return "doctor/create";
    }

    @RequestMapping(value = "doctor/findById", method = RequestMethod.POST)
    public ResponseEntity<Response> findAllById(@RequestParam Long id, HttpSession session, RedirectAttributes redirectAttributes) {


        logger.info("inside laodIndexPage()");

        boolean success = false;
        String message = null;

        Doctor doctor = doctorDao.findById(id);

        if (doctor != null)
            success = true;

        Response response = new Response(doctor, null, success);
        return new ResponseEntity<Response>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpdatePersonalDetails(@ModelAttribute Doctor doctor,
                                                                @RequestParam(required = false) Long id,
                                                                @RequestParam String practisingFromDate,
                                                                HttpSession session,
																 RedirectAttributes redirectAttributes) {


        boolean success = false;
        String message = null;

        try {
            Date practicingFromDate = DateUtils.getFinalEngDateInJavaDateFormat(practisingFromDate, session, false);
            doctor.setPracticingFromDate(practicingFromDate);

            doctor.setCreatedDate(new Date());
            doctor.setCreatedBy(SessionHelper.getLoggedInDoctor(session));

            if (doctor.getId() != null) {
                doctor.setModifiedDate(new Date());
                doctor.setModifiedBy(SessionHelper.getLoggedInDoctor(session));
                String email= doctorDao.findById(id).getEmail();
                String password =doctorDao.findById(id).getPassword();
                String salt =doctorDao.findById(id).getSalt();
                String username= doctorDao.findById(id).getUsername();

                doctor.setUsername(username);
                doctor.setEmail(email);
                doctor.setPassword(password);
                doctor.setSalt(salt);
                if ((doctorDao).update(doctor)) {
                    success = true;
                    message = "Updated successfully.";
                } else
                    message = "Sorry but failed to update patient details.";
            } else {
                id = doctorDao.save(doctor);
                if (id != null) {
                    success = true;
                    message = "patient details saved successfully.";


                } else
                    message = "Sorry but failed to save.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        Response response = new Response(id, message, success);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<Response> deleteDoctor(@RequestParam Long id, Model model) {

        boolean success = false;
        String message = null;

        if (doctorDao.delete(id)) {
            success = true;
            message = "Patient deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "fetchbyid", method = RequestMethod.POST)
    public ResponseEntity<Response> updateDoctorDetails(@RequestParam Long id, HttpSession session) {

        boolean success = false;
        String message = null;

        Doctor doctor = doctorDao.findById(id);

        if (doctor != null)
            success = true;

        Response response = new Response(doctor, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String loadViewLogin(HttpSession session){


        System.out.println("HELLO WORLD:::::: " + logger.isDebugEnabled());;

        logger.info("inside loadViewLogin()");
        logger.debug("getWelcome is executed!!!!!!!");

        return "doctor/login";
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(RedirectAttributes rdr, HttpSession session, HttpServletRequest request,
                        @RequestParam(required = false) String username, @RequestParam(required = false) String password){

        logger.info("inside login()");

        Doctor doctor = doctorDao.selectAdminByUsername(username);
        if(doctor != null) {
            String salt = doctor.getSalt();
            String pwd = doctor.getPassword();

            Settings setting = settingDao.findDate();
            String final_pwd = DigestUtils.md5DigestAsHex((salt + password).getBytes());

            if(final_pwd.equals(pwd)) {
                session.setAttribute("doctor_id",doctor.getId());
                session.setAttribute("doctor_logged_in", 1);
                session.setAttribute("doctor_name", doctor.getName());
                session.setAttribute("doctor_username", doctor.getUsername());
                session.setAttribute("doctor_email", doctor.getEmail());
                session.setAttribute("operation_date_setting", setting != null ? setting.getOperationDateSetting() : "np");
                logger.info("login success");
                return "redirect:/doctor/";
            }else {
                logger.info("login fails");
                rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "Username or password was incorrect"));
            }

        }else {
            rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "Username or password was incorrect"));
        }

        return "redirect:/doctor/login";
    }



    @RequestMapping(value="/signup",method=RequestMethod.GET)
    public String loadViewSignUp(Model model,HttpSession session,RedirectAttributes ra){

        logger.info("login loadViewSignUp");
        List<Specialization> specializationList = specializationDao.findAll();
        model.addAttribute("specializationList",specializationList);

        model.addAttribute("title","Doctor Sign Up");

        return "doctor/signup";
    }

    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public String adminSignUp(@ModelAttribute Doctor doctor,RedirectAttributes rdr,HttpServletRequest request,Model model,
                              HttpSession session,@RequestParam(value = "specialization_id") Long spId) {

        logger.info("login adminSignUp");

        String salt = UUID.randomUUID().toString().substring(0, 8);
        doctor.setSalt(salt);

        doctor.setPassword(DigestUtils.md5DigestAsHex((salt + doctor.getPassword()).getBytes()));

        Long id = doctorDao.save(doctor);

        if(id > 0) {
            logger.info("signup success");
            Doctor doctor1 = doctorDao.findById(id);

            Specialization specialization = specializationDao.findById(spId);
            DoctorSpecialization doctorSpecialization = new DoctorSpecialization();
            doctorSpecialization.setSpecialization(specialization);
            doctorSpecialization.setDoctor(doctor1);

            if (doctorSpecializationDao.save(doctorSpecialization))

            Helper.setFlashMessage(MessageType.SUCCESS, "Signed up successfully.You may log in now.", rdr);
            return "redirect:/doctor/login";
        }

        return "redirect:/doctor/signup";
    }


}
