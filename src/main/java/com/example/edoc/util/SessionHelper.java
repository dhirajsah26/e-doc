package com.example.edoc.util;

import com.example.edoc.models.Admin;
import com.example.edoc.models.Doctor;
import com.example.edoc.models.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

/*
 * @author : Dhiraj  shah
 * @date : Sep 14, 2019
 */
public class SessionHelper {

    public static boolean isAdminLoggedIn(HttpSession session) {
        /* if (true) return true;*/
        if (session.getAttribute("admin_username") != null
                && session.getAttribute("admin_logged_in").toString().equals("1")
                && !session.getAttribute("admin_username").toString().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isPatientLoggedIn(HttpSession session) {
        /* if (true) return true;*/
        if (session.getAttribute("patient_username") != null
                && session.getAttribute("patient_logged_in").toString().equals("1")
                && !session.getAttribute("patient_username").toString().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isDoctorLoggedIn(HttpSession session) {
        /* if (true) return true;*/
        if (session.getAttribute("doctor_username") != null
                && session.getAttribute("doctor_logged_in").toString().equals("1")
                && !session.getAttribute("doctor_username").toString().isEmpty()) {
            return true;
        }
        return false;
    }




    public static Integer getLoggedInAdminId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("admin_id").toString());
    }
    public static Integer getLoggedInPatientId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("patient_id").toString());
    }
    public static Integer getLoggedInDoctorId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("doctor_id").toString());
    }


    public static Admin getLoggedInAdmin(HttpSession session) {
        System.out.println("Hello World");
        System.out.println(session.getAttribute("admin_id"));
        Admin admin = new Admin();
        admin.setId(Integer.parseInt(session.getAttribute("admin_id").toString()));
        admin.setName(session.getAttribute("admin_name").toString());
        admin.setUsername(session.getAttribute("admin_username").toString());
        return admin;
    }

    public static Patient getLoggedInPatient(HttpSession session) {
        System.out.println("Hello World");
        System.out.println(session.getAttribute("patient_id"));
        Patient patient = new Patient();
        patient.setId((long) Integer.parseInt(session.getAttribute("patient_id").toString()));
        patient.setName(session.getAttribute("patient_name").toString());
        patient.setUsername(session.getAttribute("patient_username").toString());
        return patient;
    }


    public static Doctor getLoggedInDoctor(HttpSession session) {
        System.out.println("Hello World");
        System.out.println(session.getAttribute("doctor_id"));
        Doctor doctor = new Doctor();
        doctor.setId((long) Integer.parseInt(session.getAttribute("doctor_id").toString()));
        doctor.setName(session.getAttribute("doctor_name").toString());
        doctor.setUsername(session.getAttribute("doctor_username").toString());
        return doctor;
    }

    public static ResponseEntity getResponseEntity(boolean isLoginFailure) {
        return new ResponseEntity<Response>(new Response(null, isLoginFailure ? Strings.SESSION_EXPIRED : Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION, true), HttpStatus.OK);
    }

    public static String getOperationDateSetting(HttpSession session) {
        return session.getAttribute("operation_date_setting").toString();
    }

}
