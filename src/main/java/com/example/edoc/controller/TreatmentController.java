package com.example.edoc.controller;

import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.DTO.PatientDTO;
import com.example.edoc.DTO.TreatmentDTO;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.DoctorDao;
import com.example.edoc.dao.PatientDao;
import com.example.edoc.dao.TreatmentDao;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.Patient;
import com.example.edoc.models.Treatment;
import com.example.edoc.util.FileUploadHelper;
import com.example.edoc.util.Helper;
import com.example.edoc.util.Response;
import com.example.edoc.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value="treatment")
public class TreatmentController {

    @Autowired
    TreatmentDao treatmentDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    Helper helper;
    String login = null;

    @Autowired
    DoctorDao doctorDao;

    @RequestMapping(value="/" ,method= RequestMethod.GET)
    public String getIndexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {



        if (SessionHelper.isAdminLoggedIn(session)) {
            List<TreatmentDTO> treatmentDetail=treatmentDao.getTreatmentDetail();
            model.addAttribute("treatmentDetail",treatmentDetail);
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            List<TreatmentDTO> treatmentDetail=treatmentDao.getTreatmentByPatient(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("treatmentDetail",treatmentDetail);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<TreatmentDTO> treatmentDetail=treatmentDao.getTreatmentByDoctor(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("treatmentDetail",treatmentDetail);
            login="doctor";
        }

        model.addAttribute("Heading",login );






        return "treatment/index";
    }

    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String getTreatmentSavePage(Model model,HttpSession session,RedirectAttributes redirectAttributes) {
        if (SessionHelper.isAdminLoggedIn(session) || SessionHelper.isDoctorLoggedIn(session)) {


        }
        if (SessionHelper.isAdminLoggedIn(session)) {
            List<Patient> patientList=patientDao.findAll();
            model.addAttribute("patientList",patientList);

            List<Doctor> doctorList = doctorDao.findAll();
            model.addAttribute("doctorList",doctorList);

            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {

            List<PatientDTO> patientList = patientDao.findPatientByID(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("patientList",patientList);

            List<Doctor> doctorList = doctorDao.findAll();
            model.addAttribute("doctorList",doctorList);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<Patient> patientList=patientDao.findAll();
            model.addAttribute("patientList",patientList);

            List<DoctorDTO> doctorList = doctorDao.findDoctorByID(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("doctorList",doctorList);
            login="doctor";
        }

        model.addAttribute("Heading",login );


        return "treatment/add";
    }

    @RequestMapping(value="/add" , method=RequestMethod.POST)
    public String addTreatmentDetail(@RequestParam(required =  false) List<MultipartFile> prescription_image ,
                                    Model model,@ModelAttribute Treatment treatment,
                                    @RequestParam String prescribed_date,
                                    @RequestParam Long patient_id,
                                     @RequestParam Long doctor_id,
                                    @RequestParam(required =  false) Long id,
                                    @RequestParam( required = false) String prescriptionFeedback,
                                  HttpSession session,RedirectAttributes redirectAttributes
    ){

        if (id!=null) {
            Treatment treatment1=treatmentDao.getById(id);
            Patient patient=patientDao.findById(patient_id);
            treatment1.setPatient(patient);
            Doctor doctor = doctorDao.findById(doctor_id);
            treatment1.setDoctor(doctor);
            treatment1.setDateOfPrescription(Helper.parseDate(prescribed_date));
            treatment1.setPrescriptionFeedback(prescriptionFeedback);

            treatment1.setModifiedDate(new Date());

            String image="";
            List<String> images = new ArrayList<>();
            for (MultipartFile multipartFile : prescription_image) {
                image = FileUploadHelper.upload(multipartFile);
                if (image != null && !image.isEmpty())
                    images.add(image);
            }
            String imageConcat = StringUtils.arrayToCommaDelimitedString(images.toArray());
            treatment.setPrescriptionPhoto(imageConcat);
            if(treatmentDao.update(treatment1)){
                return "redirect:/treatment/";
            }
            else {
                return "treatment/add";
            }

        }else {
            Patient patient=patientDao.findById(patient_id);
            treatment.setPatient(patient);
            Doctor doctor = doctorDao.findById(doctor_id);
            treatment.setDoctor(doctor);
            treatment.setDateOfPrescription(Helper.parseDate(prescribed_date));

            String image="" ;
            List<String> images = new ArrayList<>();
            for (MultipartFile multipartFile : prescription_image) {
                image = FileUploadHelper.upload(multipartFile);
                if (image != null && !image.isEmpty())
                    images.add(image);
            }
            String imageConcat = StringUtils.arrayToCommaDelimitedString(images.toArray());
            treatment.setPrescriptionPhoto(imageConcat);

            treatment.setCreatedDate(new Date());
            if(treatmentDao.save(treatment)){
                return "redirect:/treatment/";
            } else {
                return "treatment/add";
            }
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<Response> deleteTreatment(@RequestParam Long id, Model model) {

        boolean success = false;
        String message = null;

        if (treatmentDao.delete(id)) {
            success = true;
            message = "Machine deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/getTreatmentById")
    public ResponseEntity<Response> getTreatmentById(Long id,HttpSession session){
        if (!SessionHelper.isAdminLoggedIn(session))
            return SessionHelper.getResponseEntity(true);
        //check admin role permission to this url
        if (!helper.checkUserPermission(PermissionKeys.ACCIDENT_DETAILS, session))
            return SessionHelper.getResponseEntity(false);

        boolean success = false;
        String message = null;

        TreatmentDTO treatment=treatmentDao.getTreatmentDetailById(id);
        if (treatment!=null)
            success = true;
        Response response = new Response(treatment,message,success);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @RequestMapping(value="/{id}/treatmentDetail")
    public String viewTreatmentDetail(Model model, @PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {

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

        TreatmentDTO treatmentDetails=treatmentDao.getTreatmentDetailById(id);
        model.addAttribute("treatmentDetails",treatmentDetails);
        return "treatment/detail";
    }

    @RequestMapping(value="/{id}/edit")
    public String editTreatmentDetail(Model model, @PathVariable Long id, HttpSession session,RedirectAttributes redirectAttributes) {
        if (SessionHelper.isAdminLoggedIn(session)) {
            List<Patient> patientList=patientDao.findAll();
            model.addAttribute("patientList",patientList);

            List<Doctor> doctorList = doctorDao.findAll();
            model.addAttribute("doctorList",doctorList);
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            List<PatientDTO> patientList = patientDao.findPatientByID(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("patientList",patientList);

            List<Doctor> doctorList = doctorDao.findAll();
            model.addAttribute("doctorList",doctorList);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<Patient> patientList=patientDao.findAll();
            model.addAttribute("patientList",patientList);

            List<DoctorDTO> doctorList = doctorDao.findDoctorByID(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("doctorList",doctorList);
            login="doctor";
        }

        model.addAttribute("Heading",login );

        TreatmentDTO treatmentDetails=treatmentDao.getTreatmentDetailById(id);
        model.addAttribute("treatmentDetails",treatmentDetails);


        return "treatment/edit";
    }

}
