package com.trainingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrainingsystemApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TrainingsystemApplication.class, args);
    }
    
}
