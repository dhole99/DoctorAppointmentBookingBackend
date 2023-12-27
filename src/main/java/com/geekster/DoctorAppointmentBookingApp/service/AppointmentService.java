package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Appointment;
import com.geekster.DoctorAppointmentBookingApp.model.Doctor;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IAppointmentRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IDoctorRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {
    @Autowired
    IAppointmentRepo iAppointmentRepo;
    @Autowired
    PatientTokanService patientTokanService;
    @Autowired
    IPatientRepo iPatientRepo;
    @Autowired
    IDoctorRepo iDoctorRepo;


    public String scheduleAppointment(AuthenticationInputDto authInfo, Appointment appointment) {
       if(patientTokanService.authenticate(authInfo)){
           //find the patient
           String email=authInfo.getEmail();
           Patient patient = iPatientRepo.findFirstByPatientEmail(email);
           appointment.setPatient(patient);

           //find the doctor

           Integer docId=appointment.getDoctor().getDocId();

           Doctor doc=iDoctorRepo.findById(docId).orElseThrow();
           appointment.setDoctor(doc);

           if(doc!=null){
               appointment.setAppCreationTime(LocalDateTime.now());
               iAppointmentRepo.save(appointment);
               return"Appointment booked time:" + appointment.getAppScheduleTime() + "with Dr." + doc.getDocName();
           }
           else{
               return "Doctor does not exist";
           }


       }
       else{
           return "Un authenticate access!!";
       }

    }

    public String cancelAppointment(AuthenticationInputDto autInfo, Integer appointmentId) {
        if(patientTokanService.authenticate(autInfo)){
            String email=autInfo.getEmail();
            Patient patient=iPatientRepo.findFirstByPatientEmail(email);
            Appointment existingAppointment=iAppointmentRepo.findById(appointmentId).orElseThrow();
            if(existingAppointment.getPatient().equals(patient)){
                iAppointmentRepo.findById(appointmentId);
                return "appontment with " + existingAppointment.getDoctor().getDocName() + "Has been Cancel";
            }
            else{
                return "UnAuthorized Cancel Appointment";
            }
        }
        else{
            return "Un authenticate access";
        }
    }
}
