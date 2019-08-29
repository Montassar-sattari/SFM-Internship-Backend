package com.example.ressource;

import java.net.URI;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AppRole;
import com.example.repository.RoleRepository;

@RestController
@CrossOrigin(origins= {"*"}, allowedHeaders= {"*"}, methods	= {RequestMethod.DELETE,
RequestMethod.POST,RequestMethod.PUT,RequestMethod.GET,RequestMethod.HEAD,
RequestMethod.OPTIONS})
public class RoleResource {
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	  @GetMapping(value = "/roles") public ResponseEntity<List<AppRole>>
	  getRoles() { List<AppRole> appRoles = roleRepository.findAll();
	  
	  return ResponseEntity.ok().body(appRoles); }
	  
	  @GetMapping(value = "/roles/{id}") public ResponseEntity<AppRole>
	  getRole(@PathVariable Long id) {
	  
	  Optional<AppRole> result = roleRepository.findById(id);
	  if (result.isPresent())
		  
		  return ResponseEntity.ok().body(result.get());
	  else {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	  }
	  }
	  
	  // Localhost:8008/api/
	  
	  @PostMapping(value = "/roles") public ResponseEntity<AppRole>
	  createRole(@Valid @RequestBody AppRole appRole) throws URISyntaxException {
	  
	  if (appRole.getId() != null) { throw new RuntimeException(); }
	  
	  AppRole result = roleRepository.save(appRole);
	  
	  return ResponseEntity.created(new URI("/api/roles/" +
	  result.getId())).body(result); }
	  
	  @PutMapping(value = "/roles") public ResponseEntity<AppRole>
	  updateRole(@Valid @RequestBody AppRole appRole) throws URISyntaxException {
	  
	  if (appRole.getId() == null) return createRole(appRole);
	  
	  AppRole result = roleRepository.save(appRole);
	  
	  return ResponseEntity.ok().body(result);
	  
	  }
	  
	  @DeleteMapping(value = "/roles/{id}", produces =
	  MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Void>
	  deleteRole(@PathVariable Long id) {
	  
	  roleRepository.deleteById(id);
	  
	  return ResponseEntity.ok().build(); }
	

}
