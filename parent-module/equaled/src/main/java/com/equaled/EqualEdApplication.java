package com.equaled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.equaled")
public class EqualEdApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(EqualEdApplication.class);

	public static void main(String[] args) {

		System.out.println("Starting EqualEd server");
		SpringApplication.run(EqualEdApplication.class, args);
	}
}
