package com.zhlzzz.together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TogetherApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TogetherApplication.class);
	}
<<<<<<< HEAD
=======

>>>>>>> 5248b6dc5d0cf227a148a70e8f3cede2a7e06097

	public static void main(String[] args) {
		SpringApplication.run(TogetherApplication.class, args);
	}
}
