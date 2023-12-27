package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.PatientAuthenticationToken;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IPTokanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientTokanService {

    @Autowired
    IPTokanRepo ipTokanRepo;

    public void createTokan(PatientAuthenticationToken token) {
        ipTokanRepo.save(token);
    }

    public void deletToken(String tokenValue) {
        PatientAuthenticationToken token = ipTokanRepo.findFirstByTokenValue(tokenValue);
        ipTokanRepo.delete(token);

    }

    public boolean authenticate(AuthenticationInputDto autInfo) {
        String email = autInfo.getEmail();
        String tokenValue = autInfo.getTokenValue();
       // return ipTokanRepo.findFirstByTokenValue(tokenValue).getPatient().getPatientEmail().equals(email);
       PatientAuthenticationToken token=ipTokanRepo.findFirstByTokenValue(tokenValue);
       if(token!=null){
           return token.getPatient().getPatientEmail().equals(email);
       }
       else{
           return false;
       }
    }
}
