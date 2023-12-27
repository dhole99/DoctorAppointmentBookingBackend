package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Doctor;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    IDoctorRepo iDoctorRepo;
    @Autowired
    PatientTokanService patientTokanService;


    public List<Doctor> getAllDoctor(AuthenticationInputDto autInfo) {
        if(patientTokanService.authenticate(autInfo)) {
            return iDoctorRepo.findAll();
        }
        else {
            return null;
        }
    }

    public String addDoctor(Doctor newDoctor) {

        Integer docId= newDoctor.getDocId();
        if(docId != null && iDoctorRepo.existsById(docId)){
            return "doctor already exists !!";
        }
        newDoctor.setAppointment(null);//linking anyway doesnot happen from non fk side
        iDoctorRepo.save(newDoctor);
        return "doctor added";
    }

    public Doctor getDoctorById(Integer id) {
        return iDoctorRepo.findById(id).orElseThrow();
    }
}
