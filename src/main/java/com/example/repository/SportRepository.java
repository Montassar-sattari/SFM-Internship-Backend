package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.model.Sport;
@RepositoryRestResource
public interface SportRepository  extends Repository<Sport, Long>, JpaRepository<Sport, Long>, JpaSpecificationExecutor<Sport>{
	
	
	
	  @Query("select u from Sport u where u.name like :x") public
	  Page<Sport> chercher(@Param("x")String mc, Pageable pageable);
	 
	public List<Sport> findByName( String name);
}
