package com.senla.internship.classifiedapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The {@code App} class is the main class that has the main method that starts the application
 * @author Vlas Nagibin
 * @version 1.0
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Classified API", version = "1,0"))
public class App {

	/**
	 * Returns the bean of the {@code ModelMapper} class for further using in the Service Layer
	 * @return the model mapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Runs the application
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
