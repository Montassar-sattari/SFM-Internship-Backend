package com.example.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Annonce;
import com.example.model.AppRole;
import com.example.model.AppUser;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.AccountServiceImpl;



@RestController
@CrossOrigin(origins= {"*"}, allowedHeaders= {"*"}, methods	= {RequestMethod.DELETE,
RequestMethod.POST,RequestMethod.PUT,RequestMethod.GET,RequestMethod.HEAD,
RequestMethod.OPTIONS})
public class UserResource {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired	
	 private AccountServiceImpl accountService;
	@GetMapping(value = "/users") public ResponseEntity<List<AppUser>>
	  getUsers() { List<AppUser> appUsers = userRepository.findAll();
	  
	  return ResponseEntity.ok().body(appUsers); }
	
// get user by ID
	@GetMapping(value = "/users/{id}") public ResponseEntity<AppUser>
	  getUser(@PathVariable Long id) {
	  
	  Optional<AppUser> result = userRepository.findById(id);
	  if (result.isPresent())
		  
		  return ResponseEntity.ok().body(result.get());
	  else {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		  }
	   }
	
// add user
	 @PostMapping(value = "/users") public ResponseEntity<AppUser>
	  createUser(@Valid @RequestBody AppUser appUser) throws URISyntaxException {
	  
	  if (appUser.getId() != null) { throw new RuntimeException(); }
	  
	  AppUser result = userRepository.save(appUser);
	  
	  return ResponseEntity.created(new URI("/api/users/" +
	  result.getId())).body(result); }
	 
// Update user
	 @PutMapping(value = "/users") public ResponseEntity<AppUser>
	  updateUser(@Valid @RequestBody AppUser appUser) throws URISyntaxException {
	  appUser.setJoinedOn(userRepository.getOne(appUser.getId()).getJoinedOn());
	  
	  if (appUser.getId() == null) return createUser(appUser);
	  
	  if (!userRepository.getOne(appUser.getId()).getAppRoles().isEmpty()) {
		 
		appUser.setAppRoles(userRepository.getOne(appUser.getId()).getAppRoles());  
	  }
	  if (!userRepository.getOne(appUser.getId()).getAnnonces().isEmpty()) {
			for (Annonce a : userRepository.getOne(appUser.getId()).getAnnonces()) {
				appUser.addAnnonce(a);
			}
		
		  }
	  AppUser result = accountService.saveUser(appUser);
	  
	  return ResponseEntity.ok().body(result);
	  
	  }
	 
	 @PostMapping(value = "/update/users") public ResponseEntity<AppUser>
	  updateUserInfo(@Valid @RequestBody AppUser appUser) throws URISyntaxException {
		  appUser.setJoinedOn(userRepository.getOne(appUser.getId()).getJoinedOn());
		  //appUser.setPassword(userRepository.getOne(appUser.getId()).getPassword());
		  		  
		  if (!userRepository.getOne(appUser.getId()).getAppRoles().isEmpty()) {
			 
			appUser.setAppRoles(userRepository.getOne(appUser.getId()).getAppRoles());  
		  }
		  if (!userRepository.getOne(appUser.getId()).getAnnonces().isEmpty()) {
				for (Annonce a : userRepository.getOne(appUser.getId()).getAnnonces()) {
					appUser.addAnnonce(a);
				}
			
			  }
		  AppUser result = userRepository.saveAndFlush(appUser);
		  
		  return ResponseEntity.ok().body(result);
		  
		  }
	 
	 
	 
	 @GetMapping(value = "/update/users/{photo}/{username}") public ResponseEntity<AppUser>
	  updateUserPhoto(@PathVariable String photo,@PathVariable String username) throws URISyntaxException {
		 
		 System.out.println("username"+username);
		 AppUser appUser=userRepository.findByUsername(username);
		 //System.out.println("this is user"+appUser);
		 AppUser NewUser=new AppUser();
		 if(appUser!= null)
		 {
		  NewUser.setJoinedOn(userRepository.getOne(appUser.getId()).getJoinedOn());
		  NewUser.setPassword(userRepository.getOne(appUser.getId()).getPassword());
		  NewUser.setPhoto(photo);	 
		  NewUser.setEmail(appUser.getEmail());
		  NewUser.setPrenom(appUser.getPrenom());
		  NewUser.setNom(appUser.getNom());
		  NewUser.setNumeroTel(appUser.getNumeroTel());
		  NewUser.setUsername(appUser.getUsername());
		  NewUser.setId(appUser.getId());
		 
		  
		 }
		 
		 // if (appUser.getId() == null) return createUser(appUser);
		  
		  if (!userRepository.getOne(appUser.getId()).getAppRoles().isEmpty()) {
			 
			NewUser.setAppRoles(userRepository.getOne(appUser.getId()).getAppRoles());  
		  }
		  if (!userRepository.getOne(appUser.getId()).getAnnonces().isEmpty()) {
				for (Annonce a : userRepository.getOne(appUser.getId()).getAnnonces()) {
					NewUser.addAnnonce(a);
				}
			
			  }
		  AppUser result = userRepository.save(NewUser);
		  
		  return ResponseEntity.ok().body(result);
		  
		  }
// delete user
	 
	 @DeleteMapping(value = "/users/{id}", produces =
	  MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Void>
	  deleteUser(@PathVariable Long id) {
	  
	  userRepository.deleteById(id);
	 
	  return ResponseEntity.ok().build(); }
	 
// Find user By Email
	
	  @GetMapping(value="/userByEmail/{email}")
	  public AppUser getUser(@PathVariable String email){ return
	  userRepository.findByEmail(email); }
		 
	// Find user By Email
		
		  @GetMapping(value="/userByUsername/{username}")
		  public AppUser getUserByUsername(@PathVariable String username){ return
		  userRepository.findByUsername(username); }
	  //here
	  @GetMapping(value = "/users/test/{name}/{roleID}") 
	  public List<AppUser> test(@PathVariable String name, @PathVariable Long roleID) {
		  AppUser user1=new AppUser();
		  user1.setEmail("sattarimonta@gmail.com");
		  user1.setNom("sattari");
		  user1.setPrenom(name);	  
		  user1.setNumeroTel("28739821");
		  user1.setPassword("monta007");
		  Optional<AppRole> appRole =roleRepository.findById(roleID);
		  if (appRole.isPresent())
		  {
		  user1.getAppRoles().add(appRole.get());
		  }
		  
		  this.userRepository.save(user1);
		  return this.userRepository.findAll();
		  
	  }
	  
	  // here
	/*
	 * @GetMapping(value = "/users/role/{roleID}") public List<AppUser>
	 * getUsersByRole(@PathVariable Long roleID) { List<AppUser> appUsers=new
	 * ArrayList<AppUser>(); userRepository.findByappRoleId(roleID).
	 * forEach(appUsers :: add); return appUsers;
	 * 
	 * 
	 * }
	 */
	  
	  
	// check UserName Availabilty
		 
		 @PostMapping(value = "/users/checkUsernameAvailability/{username}") 
		 	public Boolean checkUserNameAvailabilty(@PathVariable String username) {
		 // Boolean  result=true;
		 Collection< AppUser> UserList=new ArrayList<AppUser>();
		 UserList =userRepository.findAll();
		for (AppUser user: UserList) {
			if (username.toString().equalsIgnoreCase(user.getUsername())) {
				return false;
			}	
			}
		  return true; 
		  
		 } 
		 
		  
		// check Email Availabilty
			 
		 @GetMapping(value = "/users/checkEmailAvailability/{email}") 
		 	public Boolean checkEmailAvailabilty(@PathVariable String email) {
		  Boolean  result=true;
		 Collection< AppUser> UserList=new ArrayList<AppUser>();
		 UserList =userRepository.findAll();
		for (AppUser user: UserList) {
			if (user.getEmail().equalsIgnoreCase(email)) result=false;	
			}
		 
		  return result; }
		 @GetMapping(value = "/matchPWD/{pwd}/user/{id}") 
		 	public boolean getUserPWD(@PathVariable String pwd, @PathVariable Long id) {
		  boolean res=false;
		  
			 AppUser user=userRepository.getOne(id);
			 if (user!=null && bCryptPasswordEncoder.matches(pwd, user.getPassword())) {
				 res=true;
				 
			 }

			return res;

		 
		   }
}
