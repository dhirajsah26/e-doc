package com.example.edoc.controller;


import com.example.edoc.DTO.DiagnosisDTO;
import com.example.edoc.DTO.PatientDTO;
import com.example.edoc.DTO.TreatmentDTO;
import com.example.edoc.constants.MessageType;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.*;
import com.example.edoc.models.Diagnosis;
import com.example.edoc.models.Patient;
import com.example.edoc.models.Settings;
import com.example.edoc.models.Treatment;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "patient")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientDao patientDao;
    @Autowired
    TreatmentDao treatmentDao;
    @Autowired
    Helper helper;
    @Autowired
    DiagnosisDao diagnosisDao;

    @Autowired SettingDao settingDao;

    String login = null;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {


        logger.info("inside laodIndexPage()");


        if (SessionHelper.isAdminLoggedIn(session)) {
            List<Patient> patient = patientDao.findAll();
            model.addAttribute("patient", patient);
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            List<PatientDTO> patient = patientDao.findPatientByID(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("patient", patient);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<Patient> patient = patientDao.findAll();
            model.addAttribute("patient", patient);
            login="doctor";
        }

        model.addAttribute("Heading",login );
        return "patient/index";
    }

    @RequestMapping(value = "{id}/details")
    public String employeeDetails(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {



        if (SessionHelper.isAdminLoggedIn(session)) {
            Patient patient = patientDao.findById(id);
            model.addAttribute("patient", patient);

            List<TreatmentDTO> treatment = treatmentDao.getTreatmentByPatient(id);
            model.addAttribute("treatmentByPatient", treatment);

            List<DiagnosisDTO> daignosisDTO = diagnosisDao.getDiagnosisByPatient(id);
            model.addAttribute("daignosisDTO", daignosisDTO);
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            Patient patient = patientDao.findById(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("patient", patient);

            List<TreatmentDTO> treatment = treatmentDao.getTreatmentByPatient(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("treatmentByPatient", treatment);

            List<DiagnosisDTO> daignosisDTO = diagnosisDao.getDiagnosisByPatient(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("daignosisDTO", daignosisDTO);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            Patient patient = patientDao.findById(id);
            model.addAttribute("patient", patient);

            List<TreatmentDTO> treatment = treatmentDao.getTreatmentByPatient(id);
            model.addAttribute("treatmentByPatient", treatment);

            List<DiagnosisDTO> daignosisDTO = diagnosisDao.getDiagnosisByPatient(id);
            model.addAttribute("daignosisDTO", daignosisDTO);
            login="doctor";
        }

        model.addAttribute("Heading",login );



        return "patient/details";
    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String loadViewEmployee(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
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

        return "patient/create";
    }

    @RequestMapping(value = "personaldetails/findbyidemployee", method = RequestMethod.POST)
    public ResponseEntity<Response> findAllById(@RequestParam Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        //checking admin login
       /* if (!SessionHelper.isAdminLoggedIn(session))
            return SessionHelper.getResponseEntity(true);
        //check admin role permission to this url
        if (!helper.checkUserPermission(PermissionKeys.EMPLOYEE_PERSONAL_DETAILS, session))
            return SessionHelper.getResponseEntity(false);
*/
        logger.info("inside laodIndexPage()");

        boolean success = false;
        String message = null;

        Patient patient = patientDao.findById(id);

        if (patient != null)
            success = true;

        Response response = new Response(patient, null, success);
        return new ResponseEntity<Response>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(value = "personaldetails/save", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpdatePersonalDetails(@ModelAttribute Patient patient,
                                                                @RequestParam(required = false) Long id,
                                                                @RequestParam MultipartFile file1,
                                                                @RequestParam String dateOfBirthStr,
                                                                HttpSession session,
																 RedirectAttributes redirectAttributes) {
        logger.info("inside laodIndexPage()");

        boolean success = false;
        String message = null;

        try {

            Date dateOfBirth = DateUtils.getFinalEngDateInJavaDateFormat(dateOfBirthStr, session, false);
            patient.setDateOfBirth(dateOfBirth);

            patient.setCreatedDate(new Date());


            if (!file1.isEmpty()) {
                String image = FileUploadHelper.upload(file1);
                if (image != null) {
                    patient.setImage(image);
                }
            }

            if (patient.getId() != null) {
                patient.setModifiedDate(new Date());
                String email= patientDao.findById(id).getEmail();
                String password =patientDao.findById(id).getPassword();
                String salt =patientDao.findById(id).getSalt();
                String username= patientDao.findById(id).getUsername();

                patient.setUsername(username);
                patient.setEmail(email);
                patient.setPassword(password);
                patient.setSalt(salt);

                if ((patientDao).update(patient)) {
                    success = true;
                    message = "Updated successfully.";
                } else
                    message = "Sorry but failed to update patient details.";
            } else {
                id = patientDao.save(patient);
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
    public ResponseEntity<Response> deletePatient(@RequestParam Long id, Model model) {

        boolean success = false;
        String message = null;

        if (patientDao.delete(id)) {
            success = true;
            message = "Patient deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response(null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "fetchbyid", method = RequestMethod.POST)
    public ResponseEntity<Response> updatePatientDetails(@RequestParam Long id, HttpSession session) {

        boolean success = false;
        String message = null;

        Patient patient = patientDao.findById(id);

        if (patient != null)
            success = true;

        Response response = new Response(patient, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "findall", method = RequestMethod.POST)
    public ResponseEntity<Response> findAll(HttpSession session) {

        logger.info("inside laodIndexPage()");

        boolean success = false;
        String message = null;

        List<PatientDTO> patientDTOS = null;

        try {
            patientDTOS = patientDao.findEmployeeSelectedDetails();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        Response response = new Response(patientDTOS, message, success);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String loadViewLogin(HttpSession session){



        logger.info("inside loadViewLogin()");
        logger.debug("getWelcome is executed!!!!!!!");

        return "patient/login";
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(RedirectAttributes rdr, HttpSession session, HttpServletRequest request,
                        @RequestParam(required = false) String username, @RequestParam(required = false) String password){

        logger.info("inside login()");

        Patient patient = patientDao.selectPatientByUsername(username);
        if(patient != null) {
            String salt = patient.getSalt();
            String pwd = patient.getPassword();

            Settings setting = settingDao.findDate();
            String final_pwd = DigestUtils.md5DigestAsHex((salt + password).getBytes());

            if(final_pwd.equals(pwd)) {
                session.setAttribute("patient_id",patient.getId());
                session.setAttribute("patient_logged_in", 1);
                session.setAttribute("patient_name", patient.getName());
                session.setAttribute("patient_username", patient.getUsername());
                session.setAttribute("patient_email", patient.getEmail());
                session.setAttribute("operation_date_setting", setting != null ? setting.getOperationDateSetting() : "np");
                logger.info("login success");
                return "redirect:/patient/";
            }else {
                logger.info("login fails");
                rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "Username or password was incorrect"));
            }

        }else {
            rdr.addFlashAttribute("message",Helper.getFlashMessage("error", "Username or password was incorrect"));
        }

        return "redirect:/patient/login";
    }



    @RequestMapping(value="/signup",method=RequestMethod.GET)
    public String loadViewSignUp(Model model,HttpSession session,RedirectAttributes ra){

        logger.info("login loadViewSignUp");


        model.addAttribute("title","Patient Sign Up");

        return "patient/signup";
    }

    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public String patientSignUp(@ModelAttribute Patient patient,RedirectAttributes rdr,HttpServletRequest request,Model model,
                              HttpSession session) {


        logger.info("login adminSignUp");


        String salt = UUID.randomUUID().toString().substring(0, 8);
        patient.setSalt(salt);

        patient.setPassword(DigestUtils.md5DigestAsHex((salt + patient.getPassword()).getBytes()));



        Long id = patientDao.save(patient);

        if(id > 0) {
            logger.info("signup success");

                Helper.setFlashMessage(MessageType.SUCCESS, "Signed up successfully.You may log in now.", rdr);
            return "redirect:/patient/login";
        }

        return "redirect:/patient/signup";
    }



}
