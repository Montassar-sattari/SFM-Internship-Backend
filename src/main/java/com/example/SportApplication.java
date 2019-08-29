package com.example;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.model.Annonce;
import com.example.model.AppRole;
import com.example.model.AppUser;
import com.example.model.Location;
import com.example.model.Sport;
import com.example.repository.AnnonceRepository;
import com.example.repository.SportRepository;
import com.example.repository.UserRepository;
import com.example.ressource.SportResource;
import com.example.ressource.UserResource;
import com.example.service.AccountServiceImpl;
import com.example.service.AccountServiceInterface;
import com.example.startUp.StartUp;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan(basePackageClasses = {UserResource.class,SportResource.class})
@EnableSwagger2

public class SportApplication  {
	 @Autowired	
	 private AccountServiceImpl accountService;
	 @Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired
	 private AnnonceRepository annonceRepository;
	 @Autowired
	 private SportRepository sportRepository;
	public static void main(String[] args) {
		SpringApplication.run(SportApplication.class, args);
	}
	  
	    @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build();                                           
	    }
	    @Bean
	    public BCryptPasswordEncoder getBCE() {
	    	
	    	return new BCryptPasswordEncoder();
	    }
	    
	   
	    @Bean
	    @Primary
	    public AccountServiceImpl getAccountService() {
			return new AccountServiceImpl() ;
	    	
	    	
	    }

	

}
