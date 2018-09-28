package com.tawrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tawrun.Services","com.tawrun.Controller",
		"com.tawrun.Config","com.tawrun.model"})
@EnableJpaRepositories("com.tawrun.Repository")


public class TRwebsiteApplication {

	public static void main(String[] args)

	{
		SpringApplication.run( TRwebsiteApplication.class, args );
	}


//
//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//
//		return aa -> {
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort( beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}
}
