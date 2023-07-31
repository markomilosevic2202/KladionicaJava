package com.marko.kladionicajava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KladionicaJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KladionicaJavaApplication.class, args);
	}

}
