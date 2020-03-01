package com.tonyzhang.touchfish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TouchFishApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouchFishApplication.class, args);
    }

}
