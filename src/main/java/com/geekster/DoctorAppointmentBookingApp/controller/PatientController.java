package com.geekster.DoctorAppointmentBookingApp.controller;

import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.ScheduleAppointmentDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.SignInInputDto;
import com.geekster.DoctorAppointmentBookingApp.service.AppointmentService;
import com.geekster.DoctorAppointmentBookingApp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    AppointmentService appointmentService;
    // Sign up
    @PostMapping("patient/signUp")
    public String patientSignUp(@RequestBody Patient patient){
        return patientService.patientSignUp(patient);
    }


    // Sign in


    @PostMapping("patient/signIn")
    public String patientSignIn(@RequestBody SignInInputDto signininputDto){
        return patientService.patientSignIn(signininputDto);

    }


    // Sign out
    @DeleteMapping("patient/signOut")
    public String patientSignOut(@RequestBody AuthenticationInputDto autInfo){

         return patientService.patientSignOut(autInfo);
    }

    @PostMapping("patient/appontment/schedule")
    public String scheduleAppointment(@RequestBody ScheduleAppointmentDto scheduleAppointmentDto){


        return appointmentService.scheduleAppointment(scheduleAppointmentDto.getAuthInfo(),scheduleAppointmentDto.getAppointment());
    }
    @DeleteMapping("patient/appointment/{appointmentId}/cancel")
    public String cancelAppointment(@RequestBody AuthenticationInputDto autInfo,@PathVariable Integer appointmentId){


        return appointmentService.cancelAppointment(autInfo,appointmentId);
    }
}
