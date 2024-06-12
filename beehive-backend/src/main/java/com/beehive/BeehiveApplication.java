package com.beehive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class BeehiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeehiveApplication.class, args);
    }
}
