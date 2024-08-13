package org.example.finalfabricio300380186;

import org.example.finalfabricio300380186.entities.Seat;
import org.example.finalfabricio300380186.repositories.SeatsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class FinalFabricio300380186Application {

    public static void main(String[] args) {
        SpringApplication.run(FinalFabricio300380186Application.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(SeatsRepository seatsRepository) {
//        return args -> {
//         seatsRepository.save(new Seat(null, "Jasper Diaz", "3B",  new Date()));
//
//
//            seatsRepository.findAll().forEach(p -> {
//                System.out.println(p.getName());
//            });
//        };
//    }
}
