package com.thegreenwashapi.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"com.thegreenwashapi"})
@EntityScan("com.thegreenwashapi.service")
@EnableMongoRepositories("com.thegreenwashapi.repository")
public class GreenWashApplication {
	public static void main(String[] args) {
		SpringApplication.run(GreenWashApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/client").allowedOrigins("*");
				registry.addMapping("/admin").allowedOrigins("*");
				registry.addMapping("/admin").allowedOrigins("*");
				registry.addMapping("/web").allowedOrigins("*");
			}
		};
	}
}
