package com.example.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Annonce;
import com.example.model.Sport;
import com.example.repository.SportRepository;


@RestController
@CrossOrigin(origins= {"*"}, allowedHeaders= {"*"}, methods	= {RequestMethod.DELETE,
RequestMethod.POST,RequestMethod.PUT,RequestMethod.GET,RequestMethod.HEAD,
RequestMethod.OPTIONS})
public class SportResource {

	
	  @Autowired private SportRepository sportRepository;
	  
	  @GetMapping(value = "/sports") public ResponseEntity<List<Sport>>
	  getSports() { List<Sport> sports = sportRepository.findAll();
	  
	  return ResponseEntity.ok().body(sports); }
	  
	  @GetMapping(value = "/sports/{id}") public ResponseEntity<Sport>
	  getSport(@PathVariable Long id) {
	  
	  Optional<Sport> result = sportRepository.findById(id);
	  if (result.isPresent())
		  
		  return ResponseEntity.ok().body(result.get());
	  else {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	  }
	  }
	  
	  // Localhost:8008/api/nom/prenom/
	  
	  @PostMapping(value = "/sports") public ResponseEntity<Sport>
	  createSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
	  
	  if (sport.getId() != null) { throw new RuntimeException(); }
	  
	  Sport result = sportRepository.save(sport);
	  
	  return ResponseEntity.created(new URI("/api/sports/" +
	  result.getId())).body(result); }
	  
	  @PutMapping(value = "/sports") public ResponseEntity<Sport>
	  updateSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
	  
	  if (sport.getId() == null) return createSport(sport);
	  
	  Sport result = sportRepository.save(sport);
	  
	  return ResponseEntity.ok().body(result);
	  
	  }
	  
	  @DeleteMapping(value = "/sports/{id}", produces =
	  MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Void>
	  deleteSport(@PathVariable Long id) {
	  
	  sportRepository.deleteById(id);
	 
	  return ResponseEntity.ok().build(); }
	 
	  @GetMapping(value = "/sports/Name/{Name}") 
	  public  ResponseEntity<List<Sport>> getSportByName( @PathVariable(value="Name") String name) { 
		  List<Sport> sports = sportRepository.findByName(name);
	  
	  return ResponseEntity.ok().body(sports); }
	  
	  @GetMapping(value = "/sports/Announces/Name/{Name}") 
	  public  ResponseEntity<List<Annonce>> getAnnouncesSportByName( @PathVariable(value="Name") String name) { 
		  List<Sport> sports = sportRepository.findByName(name);
	  List<Annonce> annonces=new ArrayList<Annonce>();
	  annonces=(List<Annonce>) sports.get(0).getAnnonces();
	  return ResponseEntity.ok().body(annonces); }
	  
	 
	@GetMapping(value = "/chercherSport/{Name}")
	  public  Page<Sport> ChercherSport( @PathVariable(value="Name") String name, 
			  @RequestParam(value="page", defaultValue = "0" ) int page ,
			  @RequestParam(value="size", defaultValue = "3" ) int size) { 
		return  sportRepository.chercher("%"+name+"%", PageRequest.of(page, size) );
	  
	   }
	
	  
	  
	 
}
