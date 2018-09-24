package com.tawrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tawrun.Services","com.tawrun.Controller",
		"com.tawrun.Config","com.tawrun.model"})
@EnableJpaRepositories("com.tawrun.Repository")


public class TRwebsiteApplication {

	public static void main(String[] args)

	{
		SpringApplication.run( TRwebsiteApplication.class, args );
	}

}
