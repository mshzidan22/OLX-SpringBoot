package com.olx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OlxApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxApplication.class, args);
	}

	@Bean
	public ModelMapper m(){
		return new ModelMapper();
	}

	@Bean
	public ObjectMapper om(){
		return new ObjectMapper();
	}




}


