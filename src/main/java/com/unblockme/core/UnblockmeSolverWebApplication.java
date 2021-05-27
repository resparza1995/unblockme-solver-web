package com.unblockme.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UnblockmeSolverWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UnblockmeSolverWebApplication.class, args);
	}

}
