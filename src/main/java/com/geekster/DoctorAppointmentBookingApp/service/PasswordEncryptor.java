package com.geekster.DoctorAppointmentBookingApp.service;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    public static String encrypt(String UnHassedPassword) throws NoSuchAlgorithmException {
        MessageDigest md5= MessageDigest.getInstance("MD5");
        md5.update(UnHassedPassword.getBytes());
        byte[]digested= md5.digest();
        return DatatypeConverter.printHexBinary(digested);
    }
}
