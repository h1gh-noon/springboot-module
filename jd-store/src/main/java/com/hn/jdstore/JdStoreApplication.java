package com.hn.jdstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hn")
public class JdStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdStoreApplication.class, args);
    }

}
