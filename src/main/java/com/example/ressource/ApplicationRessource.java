package com.example.ressource;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Annonce;
import com.example.model.AppRole;
import com.example.model.AppUser;
import com.example.model.Location;
import com.example.model.Sport;
import com.example.repository.AnnonceRepository;
import com.example.repository.SportRepository;
import com.example.service.AccountServiceImpl;
import com.example.startUp.StartUp;
@RestController
@CrossOrigin(origins= {"*"}, allowedHeaders= {"*"}, methods	= {RequestMethod.DELETE,
RequestMethod.POST,RequestMethod.PUT,RequestMethod.GET,RequestMethod.HEAD,
RequestMethod.OPTIONS})
public class ApplicationRessource {
	
	 @Autowired	
	 private AccountServiceImpl accountService;
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired
	 private AnnonceRepository annonceRepository;
	 @Autowired
	 private SportRepository sportRepository;
	
	
	
	@GetMapping(value = "/intialization")
	public String intiateApp() {
		
	  ArrayList<Sport> sports = new ArrayList<Sport>();
	  sports=StartUp.readFromJsonFile();
	  sports.forEach(s->{
	  
	  sportRepository.save(s); });
	 

	  ZonedDateTime today; LocalDate date; date=LocalDate.now();
	  today=ZonedDateTime.now(); AppUser monta=new AppUser(null, "SATTARI",
	  "MONTASSAR", "Monta", "28739821", "satarimonta@gmail.com",
	  "monta007",today,null, null,null);
	  
	  //monta.setPassword(bCryptPasswordEncoder.encode(monta.getPassword()));
	  accountService.saveRole(new AppRole(null, "ADMIN"));
	  accountService.saveRole(new AppRole(null, "USER"));
	  
	  
	  Sport sport = sportRepository.findByName("Football").get(0);
	  
	  
	  Annonce annonce=new Annonce(); annonce.setDescription("A football game");
	  annonce.setType("annonce"); annonce.setCreated_On(today);
	  annonce.setDate(date.plusDays(5));
	  
	  Location location = new Location(); location.setCity("Nabeul");
	  location.setCountry("TN"); location.setLatitude(36.4562188);
	  location.setLongitude(10.7229968);
	  
	  
	  
	  annonce.addLocation(location); annonce.addJoueur(monta);
	  annonceRepository.save(annonce);
	  
	  
	  monta.addAnnonce(annonce);
	  
	  sport.addAnnonce(annonce);
	  
	  sportRepository.saveAndFlush(sport);
	  
	  accountService.saveUser(monta);
	  
	  accountService.saveUser(new AppUser(null, "SATTARI", "MOHAMED", "Med",
	  "20889665", "Med@gmail.com", "med007",today,null, null,null));
	 
	  accountService.AddRoleToUser("Monta", "ADMIN");
	  accountService.AddRoleToUser("Med", "USER");
	  
	  
		
		return "app initiated successfully!";
	}

	
  
 

}
