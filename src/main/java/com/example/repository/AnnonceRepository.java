package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.model.Annonce;
@RepositoryRestResource
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {


	
}
