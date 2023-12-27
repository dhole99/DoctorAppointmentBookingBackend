package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.PatientAuthenticationToken;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.SignInInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IPTokanRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    IPatientRepo iPatientRepo;

    @Autowired
    PatientTokanService patientTokanService;

    public String patientSignUp(Patient patient) {
        // check if already exists-> Not Allowed : try signIp
        String newEmail = patient.getPatientEmail();
        Patient existingPatient = iPatientRepo.findFirstByPatientEmail(newEmail);
        if (existingPatient != null) {
            return "email already in use";
        }


        // password was encrypted before we stored in table
        String signupPassword = patient.getPatientPassword();
        try {
            String encryptedPassword = PasswordEncryptor.encrypt(signupPassword);

            patient.setPatientPassword(encryptedPassword);

            // patient table save patient

            iPatientRepo.save(patient);
            return "patient registered";

        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issue while saving password,try again later";
        }


    }

    public String patientSignIn(SignInInputDto signininputDto) {
        //check if the email is there in your table
        //sign in only possible if this person ever signed up
        String email = signininputDto.getEmail();
        Patient existingPatient = iPatientRepo.findFirstByPatientEmail(email);
        if (existingPatient == null) {
            return "Not a valid email,please frst sign up";
        }


            //password should be match

            String password = signininputDto.getPassword();

            try {
                String encryptedPassword = PasswordEncryptor.encrypt(password);

                if (existingPatient.getPatientPassword().equals(encryptedPassword)) {

                    //return a token for these sign in
                    PatientAuthenticationToken token = new PatientAuthenticationToken(existingPatient);
                    patientTokanService.createTokan(token);

                    EmailService.sendEmail(email,"opt after login",token.getTokenValue());

                    return "check mail for otp/token";


                }
                else {


                    //password was wrong
                    return "Invalid Credantials";
                }


            } catch (NoSuchAlgorithmException e) {
                return "Internal Server issue while saving password,try again later";
            }


    }

    public String patientSignOut(AuthenticationInputDto autInfo) {

        //the auth token should deleted



        if(patientTokanService.authenticate(autInfo)) {


            String tokenValue = autInfo.getTokenValue();
            patientTokanService.deletToken(tokenValue);
            return "Sign Out Succesfull";
        }
        else {
            return "Un Authentical Access";

        }

    }

    public List<Patient> getAllPatients() {
        return iPatientRepo.findAll();
    }
}


