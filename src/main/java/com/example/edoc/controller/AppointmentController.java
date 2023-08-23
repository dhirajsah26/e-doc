package com.example.edoc.controller;

import com.example.edoc.DTO.AppointmentDTO;
import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.DTO.PatientDTO;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.AppointmentDao;
import com.example.edoc.dao.DoctorDao;
import com.example.edoc.dao.PatientDao;
import com.example.edoc.dao.SpecializationDao;
import com.example.edoc.models.Appointment;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.Patient;
import com.example.edoc.models.Specialization;
import com.example.edoc.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "appointment")
public class AppointmentController {
    private static final Logger logger =  LoggerFactory.getLogger(SpecializationController.class);
    @Autowired Helper helper;
    @Autowired
    DoctorDao doctorDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    SpecializationDao specializationDao;
    String  login = null;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {


        if (SessionHelper.isAdminLoggedIn(session)) {
            List<AppointmentDTO> appointmentDTOS = appointmentDao.getAllAppointment();
            model.addAttribute("appointmentDTOS",appointmentDTOS);
            login="admin";

        }

        //check admin role permission to this url
        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<AppointmentDTO> appointmentDTOS = appointmentDao.getAppointmentsByDoctorId(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("appointmentDTOS",appointmentDTOS);
            login="doctor";

        }

        if (SessionHelper.isPatientLoggedIn(session)) {
            List<AppointmentDTO> appointmentDTOS = appointmentDao.getAppointmentsByPatientId(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("appointmentDTOS",appointmentDTOS);
            model.addAttribute("name",SessionHelper.getLoggedInPatient(session).getName());

            login= "patient";

        }
        model.addAttribute("Heading",login );


        return "appointment/index";
    }

    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String getAppointmentSavePage(Model model,HttpSession session,RedirectAttributes redirectAttributes) {

        if (SessionHelper.isAdminLoggedIn(session) ) {
            login = "admin";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            login="doctor";
        }

        if (SessionHelper.isPatientLoggedIn(session) ) {
            List<PatientDTO> patientList=patientDao.findPatientByID(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("patientList",patientList);
            login= "patient";
        }

        List<Patient> patientList=patientDao.findAll();
        model.addAttribute("patientList",patientList);

        List<Doctor> doctorList = doctorDao.findAll();
        model.addAttribute("doctorList",doctorList);

        List<Specialization> specializationList = specializationDao.findAll();
        model.addAttribute("specializationList",specializationList);

        model.addAttribute("Heading",login );


        return "appointment/add";
    }



    @RequestMapping(value="/checkAppointment")
    public ResponseEntity<Response> getAppointmentByPatientId(@RequestParam Long doctorId,
                                                               @RequestParam String appointmentDate,@RequestParam String appointmentTime,
                                                               HttpSession session) throws ParseException {
       /* if (!SessionHelper.isAdminLoggedIn(session))
            return SessionHelper.getResponseEntity(true);
        //check admin role permission to this url
        if (!helper.checkUserPermission(PermissionKeys.ACCIDENT_DETAILS, session))
            return SessionHelper.getResponseEntity(false);
*/
        boolean success = false;
        String message = null;

        AppointmentDTO appointment1= appointmentDao.getAppointmentByDoctorId(doctorId);

        if (appointment1 != null){
            System.out.println("ima from check");
            Date checkDate = appointment1.getAppointment_date();

            Date formDate =Helper.parseDate(appointmentDate);
            String time= appointment1.getAppointment_time().toString();

            String tme = time;
            String[] tiimme = tme.split ( ":" );
            int hour1 = Integer.parseInt ( tiimme[0].trim() );
            int min1 = Integer.parseInt ( tiimme[1].trim() );

            String timme = appointmentTime;
            String[] tiime = timme.split ( ":" );
            int hour = Integer.parseInt ( tiime[0].trim() );
            int min = Integer.parseInt ( tiime[1].trim() );

            String formTime = String.valueOf(hour);
            String databaseTime=String.valueOf(hour1);

            if (checkDate.compareTo(formDate) == 0 && formTime.equals(databaseTime)){
                success = true;

            }

        }
        Response response = new Response(null,message,success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpdatePersonalDetails( Model model,@ModelAttribute Appointment appointment,
                                                                 @RequestParam Long patientId,
                                                                 @RequestParam Long doctorId,
                                                                 @RequestParam String appointmentDate,
                                                                 @RequestParam String appointmentTime,HttpSession session,
                                                                 RedirectAttributes redirectAttributes) {
       /* //checking admin login
        if (!SessionHelper.isAdminLoggedIn(session))
            return SessionHelper.getResponseEntity(true);
        //check admin role permission to this url
        if (!helper.checkUserPermission(PermissionKeys.EMPLOYEE_PERSONAL_DETAILS, session))
            return SessionHelper.getResponseEntity(false);
*/

        boolean success = false;
        String message = null;

        try {
            Doctor doctor= doctorDao.findById(doctorId);
            appointment.setDoctor(doctor);

            appointment.setAppointmentDate(Helper.parseDate(appointmentDate));
            Patient patient= patientDao.findById(patientId);

            appointment.setPatient(patient);
            appointment.setAppointmentTime(appointmentTime);
            appointment.setCreatedDate(new Date());


            if (appointmentDao.saveOrUpdate(appointment)) {
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

        if (appointmentDao.delete(id)) {
            success = true;
            message = "Doctor deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "getDoctorBySpecializationId")
    public ResponseEntity<Response> getDoctorBySpecialization(@RequestParam Long specialization_id, Model model) {

        boolean success = false;
        String message = null;

        List<DoctorDTO> doctorDTOList = doctorDao.findDoctorBySpecializationID(specialization_id);
        if (doctorDTOList!=null){
            success= true;
        }


        Response response = new Response (doctorDTOList, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }



}
