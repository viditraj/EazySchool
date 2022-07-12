package com.education.School;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories("com.education.School.repository") // tell JPA about repository folder
@EntityScan("com.education.School.model") //tell JPA about models folder
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Enabling Auditing by telling JPA about our bean class AuditAwareImpl with bean name
public class SchoolApplication {

	public static void main(String[] args)  {
		log.info("Application Starting ....");
		SpringApplication.run(SchoolApplication.class, args);
	}

}
