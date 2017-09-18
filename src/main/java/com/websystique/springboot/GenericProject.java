package com.websystique.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.websystique.springboot.configuration.JpaConfigurationTest;
import com.websystique.springboot.controller.AppController;
import com.websystique.springboot.controller.RestApiController;


@Import(JpaConfigurationTest.class)
@SpringBootApplication(scanBasePackages={"com.websystique.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class GenericProject {

	public static void main(String[] args) {
		SpringApplication.run(GenericProject.class, args);
	}
}
