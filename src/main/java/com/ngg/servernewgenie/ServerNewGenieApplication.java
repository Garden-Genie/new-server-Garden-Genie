package com.ngg.servernewgenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ngg.servernewgenie")
public class ServerNewGenieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerNewGenieApplication.class, args);
	}

}
