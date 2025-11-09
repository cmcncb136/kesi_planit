package com.kesi.planit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlanItApplication {
    //git push test
    public static void main(String[] args) {
        SpringApplication.run(PlanItApplication.class, args);
    }

}
