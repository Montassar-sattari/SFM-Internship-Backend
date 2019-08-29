package com.example.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Annonce;
import com.example.model.AppUser;
import com.example.model.Location;
import com.example.model.Sport;
import com.example.repository.AnnonceRepository;
import com.example.repository.SportRepository;
import com.example.repository.UserRepository;


@RestController
@CrossOrigin(origins= {"*"}, allowedHeaders= {"*"}, methods	= {RequestMethod.DELETE,
RequestMethod.POST,RequestMethod.PUT,RequestMethod.GET,RequestMethod.HEAD,
RequestMethod.OPTIONS})
public class AnnonceRessource {
	
	 @Autowired 
	 private AnnonceRepository annonceRepository;
	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private SportRepository sportRepository;
	 @GetMapping("/Announces")
	 public ResponseEntity< List<Annonce>> getAnnonces() {
	 { List<Annonce> annonces = annonceRepository.findAll();
		  
		  return ResponseEntity.ok().body(annonces); }
		 
	 }
	 
	 
	 @PostMapping(value = "/Announces") public ResponseEntity<Annonce>
	  createAnnonce(@Valid @RequestBody Annonce annonce) throws URISyntaxException {
	  
	  if (annonce.getId() != null) { throw new RuntimeException(); }
	  
	  Annonce result = annonceRepository.save(annonce);
	  
	  return ResponseEntity.created(new URI("/annonces" +
	  result.getId())).body(result); }
	
	 @GetMapping(value="/Announces/{id}/addPlayer/{playerName}")
	 public ResponseEntity<Annonce> addPayer(@PathVariable(name = "playerName") String playerName, @PathVariable(name = "id") Long id) throws URISyntaxException  {
		 
		 Annonce annonce=annonceRepository.getOne(id);
		
		 AppUser player=userRepository.findByUsername(playerName);
		 
		 if (player.getId() == null|| annonce.getId()==null) { throw new RuntimeException(); }
		 
		 if(annonce.getJoueurs().contains(player)) {return ResponseEntity.ok().body(annonce);}
		
		 annonce.addJoueur(player);
		 
		Annonce result= annonceRepository.saveAndFlush(annonce); 
		return ResponseEntity.ok().body(result); 
		 
	 }
	 
	 
	 
	 @PostMapping(value="/Announce/create/{userName}/{sportName}/{currentLat}/{currentLon}")
	 public ResponseEntity<Annonce> addAnnounce(@PathVariable String userName,
			 @PathVariable String sportName, @PathVariable String currentLat,
			 @PathVariable String currentLon,@Valid @RequestBody Annonce annonce ) {
		 System.out.println(annonce);
		 ZonedDateTime today;
		 today=ZonedDateTime.now();
		 double lat=Double.parseDouble(currentLat);
		 double lon = Double.parseDouble(currentLon);
		 Sport sport=sportRepository.findByName(sportName).get(0);
		 
		 AppUser appUser = userRepository.findByUsername(userName);
		 Location location=new Location();
		 location.setLatitude(lat);
		 location.setLongitude(lon);
		
		 
		 annonce.setCreated_On(today); 
		 
		 annonce.addLocation(location);
		 
		 System.out.println(annonce);
		Annonce result= annonceRepository.save(annonce);
		   
		   appUser.addAnnonce(result);
		   sport.addAnnonce(result);
		 userRepository.saveAndFlush(appUser);
		 sportRepository.saveAndFlush(sport);
		 return ResponseEntity.ok().body(result);
		 
		 
		 
	 }
	 
	 
	 //
	 
	 @GetMapping(value="/Announce/{id}/user/{username}")
	 public ResponseEntity<Boolean> IsParticipated(@PathVariable(name = "username") String playerName, @PathVariable(name = "id") Long id) throws URISyntaxException  {
		 
		 Annonce annonce=annonceRepository.getOne(id);
		
		 AppUser user=userRepository.findByUsername(playerName);
		 
		 if (user!= null && annonce!=null) { 
		 
		 if(annonce.getJoueurs().contains(user)) {return ResponseEntity.ok().body(true);}  }
		return ResponseEntity.ok().body(false); 
		 
	 }
	 
	 //
	 
	 @GetMapping(value="/Announce/nombreJoueurs/{id}")
	 public Integer getNombreHoueurs( @PathVariable(name = "id") Long id) throws URISyntaxException  {
		 
		 Annonce annonce=annonceRepository.getOne(id);
		int nbre=0;
		
		 
		 if (annonce!= null) { 
		 nbre=annonce.getJoueurs().size();}
		 return nbre;
		 
		 
		 
	 }
	 
	 @GetMapping(value="/Announce/nombreJoueursTotal/{id}")
	 public long getNombreHoueursTotal( @PathVariable(name = "id") Long id) throws URISyntaxException  {
		 
		 Annonce annonce=annonceRepository.getOne(id);
		long nbre=0;
		Collection<Sport> sports=new ArrayList<Sport>();
		sports=sportRepository.findAll();
		 if (annonce!= null) { 
			 
		for (Sport s: sports) {
		if(s.getAnnonces().contains(annonce)) {
			nbre=s.getNumberOfPlayers();
		}	
		}
		}
		 return nbre;
		 
		 
		 
	 }

}
