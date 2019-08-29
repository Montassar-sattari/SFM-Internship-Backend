package com.example.model;



import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

	@Entity
	@Table(name = "annonce")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@NoArgsConstructor
	@ToString
	//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = User.class)
public class Annonce implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "annonce_id")
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "type")
	private String type;
	@Column(name="isfull")
	private boolean isfull;
	@Column(name="created_On")
	private ZonedDateTime Created_On;
	
	@Column(name="date")
	private LocalDate date;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(name = "coords") 
	private Collection<Location> locations= new ArrayList<Location>();

	@Column(name="Joueurs")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<AppUser> Joueurs = new ArrayList<AppUser>();
	
	
	
	 public void addLocation(Location location) {
		    
		    if (this.getLocations()==null) {
		    	ArrayList<Location> locations=new ArrayList<Location>();
		    	locations.add(location);
		    	this.setLocations(locations);
		    }
		    else {
		    	this.getLocations().add(location);
		    }
		    }
	 
	 
	 public void addJoueur(AppUser joueur) {
		    
		    if (this.getJoueurs()==null) {
		    	ArrayList<AppUser> Joueurs=new ArrayList<AppUser>();
		    	Joueurs.add(joueur);
		    	this.setJoueurs(Joueurs);
		    }
		    else {
		    	this.getJoueurs().add(joueur);
		    }
		    }


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Collection<Location> getLocations() {
		return locations;
	}


	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}

@JsonIgnore
	public Collection<AppUser> getJoueurs() {
		return Joueurs;
	}

	public void setJoueurs(Collection<AppUser> joueurs) {
		Joueurs = joueurs;
	}


	public boolean isIsfull() {
		return isfull;
	}


	public void setIsfull(boolean isfull) {
		this.isfull = isfull;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public ZonedDateTime getCreated_On() {
		return Created_On;
	}


	public void setCreated_On(ZonedDateTime created_On) {
		Created_On = created_On;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}
	/*
	 * @JoinColumn(name = "Sport", referencedColumnName = "sport_id") private Sport
	 * sport;
	 */
	
	
	/*
	 * @JoinColumn(name="annonceur", referencedColumnName = "user_id") private
	 * AppUser annonceur;
	 */
	
	/*
	 * @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name="annonceur", referencedColumnName = "user_id")
	 * //@JsonBackReference private AppUser annonceur;
	 */
	

	 
}
