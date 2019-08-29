package com.example.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.model.AppUser;
@RepositoryRestResource
public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	
	/*
	 * @Query("select u from user u where u.email = x")
	 public Page<User> chercher(@Param("x")String mc, Pageable pageable);
	*/
	 public AppUser findByEmail(String email);
	 public AppUser findByUsername (String username); 
	// public List<AppUser> findByappRoleId(Long id);
	// public Boolean checkUserNameAvailabilty(String name);
	// public Boolean checkEmailAvailabilty(String Email);
}
