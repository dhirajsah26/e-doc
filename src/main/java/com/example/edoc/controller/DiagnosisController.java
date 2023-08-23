package com.example.edoc.controller;

import com.example.edoc.DTO.DiagnosisDTO;
import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.DTO.PatientDTO;
import com.example.edoc.constants.PermissionKeys;
import com.example.edoc.dao.DiagnosisDao;
import com.example.edoc.dao.DoctorDao;
import com.example.edoc.dao.PatientDao;
import com.example.edoc.models.Diagnosis;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.Patient;
import com.example.edoc.util.*;
import org.hibernate.Session;
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
@RequestMapping(value="diagnosis")
public class DiagnosisController {

    @Autowired
    DiagnosisDao diagnosisDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    Helper helper;
    @Autowired
    DoctorDao doctorDao;
    String  login = null;


    @RequestMapping(value="/" ,method= RequestMethod.GET)
    public String getIndexPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (SessionHelper.isAdminLoggedIn(session)) {
            List<DiagnosisDTO> diagnosisDetail=diagnosisDao.getDiagnosisDetail();
            model.addAttribute("diagnosisDetail",diagnosisDetail);
            login="admin";
        }
        //check admin role permission to this url
        if (SessionHelper.isPatientLoggedIn(session)) {
            List<DiagnosisDTO> diagnosisDetail=diagnosisDao.getDiagnosisByPatient(SessionHelper.getLoggedInPatientId(session).longValue());
            model.addAttribute("diagnosisDetail",diagnosisDetail);
            login="patient";
        }

        if (SessionHelper.isDoctorLoggedIn(session)) {
            List<DiagnosisDTO> diagnosisDetail=diagnosisDao.getDiagnosisByDoctor(SessionHelper.getLoggedInDoctorId(session).longValue());
            model.addAttribute("diagnosisDetail",diagnosisDetail);
            login="doctor";
        }



        model.addAttribute("Heading",login );
        return "diagnosis/index";
    }

    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String getDiagnosisSavePage(Model model,HttpSession session,RedirectAttributes redirectAttributes) {
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
        return "diagnosis/add";
    }

    @RequestMapping(value="/add" , method=RequestMethod.POST)
    public String addDiagnosisDetail(@RequestParam(required =  false) List<MultipartFile> diagnosis_photo ,
                                    Model model,@ModelAttribute Diagnosis diagnosis,
                                    @RequestParam Long patient_id ,
                                     @RequestParam Long doctor_id ,
                                     @RequestParam String blood_pressure ,
                                     @RequestParam String blood_glucose ,
                                     @RequestParam String body_weight ,
                                     @RequestParam String body_mass_index ,
                                    @RequestParam(required =  false) Long id ,
                                    HttpSession session,RedirectAttributes redirectAttributes
    ){

        if (id!=null) {
             diagnosis=diagnosisDao.getById(id);
            Patient patient=patientDao.findById(patient_id);
            diagnosis.setPatient(patient);
            Doctor doctor = doctorDao.findById(doctor_id);
            diagnosis.setDoctor(doctor);
            diagnosis.setModifiedDate(new Date());
            diagnosis.setBloodGlucose(blood_glucose);
            diagnosis.setBloodPressure(blood_pressure);
            diagnosis.setBodyMassIndex(body_mass_index);
            diagnosis.setBodyWeight(body_weight);

            String image="";
            List<String> images = new ArrayList<>();
            for (MultipartFile multipartFile : diagnosis_photo) {
                image = FileUploadHelper.upload(multipartFile);
                if (image != null && !image.isEmpty())
                    images.add(image);
            }
            String imageConcat = StringUtils.arrayToCommaDelimitedString(images.toArray());
            diagnosis.setDiagnosisPhoto(imageConcat);
            if(diagnosisDao.update(diagnosis)){
                return "redirect:/diagnosis/";
            }
            else {
                return "diagnosis/add";
            }

        }else {
            Patient patient=patientDao.findById(patient_id);
            diagnosis.setPatient(patient);

            Doctor doctor = doctorDao.findById(doctor_id);
            diagnosis.setDoctor(doctor);

            String image="" ;
            List<String> images = new ArrayList<>();
            for (MultipartFile multipartFile : diagnosis_photo) {
                image = FileUploadHelper.upload(multipartFile);
                if (image != null && !image.isEmpty())
                    images.add(image);
            }
            String imageConcat = StringUtils.arrayToCommaDelimitedString(images.toArray());
            diagnosis.setDiagnosisPhoto(imageConcat);

            diagnosis.setCreatedDate(new Date());
            diagnosis.setBloodGlucose(blood_glucose);
            diagnosis.setBloodPressure(blood_pressure);
            diagnosis.setBodyMassIndex(body_mass_index);
            diagnosis.setBodyWeight(body_weight);
            if(diagnosisDao.save(diagnosis)){
                return "redirect:/diagnosis/";
            } else {
                return "diagnosis/add";
            }
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<Response> deleteDiagnosis(@RequestParam Long id, Model model) {

        boolean success = false;
        String message = null;

        if (diagnosisDao.delete(id)) {
            success = true;
            message = "Machine deleted successfully.";
        } else {
            message = "Sorry but something went wrong.";
        }

        Response response = new Response (null, message, success);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/getDiagnosisById")
    public ResponseEntity<Response> getDiagnosisByPatientId(Long id,HttpSession session){

        boolean success = false;
        String message = null;

        DiagnosisDTO diagnosisDTO=diagnosisDao.getDiagnosisDetailById(id);
        if (diagnosisDTO!=null)
            success = true;
        Response response = new Response(diagnosisDTO,message,success);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @RequestMapping(value="/{id}/diagnosisDetail")
    public String viewDiagnosisDetail(Model model, @PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
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
        DiagnosisDTO diagnosis=diagnosisDao.getDiagnosisDetailById(id);

        System.out.println(diagnosis.toString());

        model.addAttribute("diagnosis",diagnosis);
        return "diagnosis/detail";
    }

    @RequestMapping(value="/{id}/edit")
    public String editDiagnosisDetail(Model model, @PathVariable Long id, HttpSession session,RedirectAttributes redirectAttributes) {
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

        DiagnosisDTO diagnosisDTO=diagnosisDao.getDiagnosisDetailById(id);
        model.addAttribute("diagnosisDTO",diagnosisDTO);


        return "diagnosis/edit";
    }
}
