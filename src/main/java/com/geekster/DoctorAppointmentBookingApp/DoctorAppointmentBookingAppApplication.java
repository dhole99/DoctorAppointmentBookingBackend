package com.geekster.DoctorAppointmentBookingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class DoctorAppointmentBookingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorAppointmentBookingAppApplication.class, args);
       Map<Character,Integer> map = call();
       map.entrySet().stream().forEach(System.out::println);
    }


    public static Map<Character,Integer> call() {
        final String str = "uhvcjhasdvcjhsvdjhasdvjkbsdjkvbasdvbasdhbvjhasbvjkasdbvasvbjhasdbjkvbasdnkvbasdfjkgvbuqwriouepxcmvcjb";

        char[] strChar = str.toCharArray();

        Map<Character, Integer> map = new HashMap<>();

        for (Character ch : strChar) {
            if (ch != null) {
                map.put(ch, 1);
            } else {
                map.put(ch, map.get(ch) + 1);
            }
        }

       return map;


    }

	public static void stop(){
		String nasme = "jdsdjbv";
	}

}
