package org.diegosneves.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@ComponentScan(basePackages = {
        "org.diegosneves.application", // Seu pacote de aplicação
        "org.diegosneves.domain"      // Pacote do domínio
    })
public class ExactproCmmsBackendApplication {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(ExactproCmmsBackendApplication.class, args);
    }

}
