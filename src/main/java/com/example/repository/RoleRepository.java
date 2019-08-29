package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.model.AppRole;
@RepositoryRestResource
public interface RoleRepository extends JpaRepository<AppRole, Long>{
		
	public AppRole findByName(String RoleName);
}
