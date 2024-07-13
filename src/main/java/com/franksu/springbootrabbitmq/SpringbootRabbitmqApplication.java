package com.franksu.springbootrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootRabbitmqApplication {

    public static void main(String[] args) {
        // System.setProperty("spring.amqp.deserialization.trust.all", "true");
        SpringApplication.run(SpringbootRabbitmqApplication.class, args);
    }

}
