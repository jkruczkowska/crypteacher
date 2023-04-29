package com.example.crypteacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrypteacherApplication {

    private static final Logger logger = LoggerFactory.getLogger(CrypteacherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrypteacherApplication.class, args);
        logger.info("Starting the App");
    }
}
