package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages= {"com.controller","com.dao","com.model","com.service","com.dto","com.excepcion"})
@EntityScan(basePackages= {"com.model"})
@EnableJpaRepositories(basePackages= {"com.dao"})
public class EjercicioneorisApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercicioneorisApplication.class, args);
	}

}
