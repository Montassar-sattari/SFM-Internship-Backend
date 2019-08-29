package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@ToString
@Table(name = "user")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = User.class)

public class AppUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private  Long id;
	
    @NotNull
    @Column(name="nom", nullable= false)
    private String nom;
    
    
    @NotNull
    @Column(name="prenom", nullable= false)
    private  String  prenom;
    
    @Column (unique = true)
    private String username;
    
    @Column(name="numerotel")
    private  String numeroTel;
    
    @NotNull
    @Column(name="email", nullable= false)
    private  String email;
    
    @NotNull
    @Column(name = "password" )
    private String password;
    
    
    @Column(name="Joined_On")
    private ZonedDateTime JoinedOn;
    @Column(name="photo")
    private String photo;
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   // @JsonBackReference
   // @JoinColumn(name = "appRole",referencedColumnName = "Role_id")
    private Collection<AppRole> appRoles = new ArrayList<AppRole>();
 
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "User_annonces",referencedColumnName = "annonce_id")
    private Collection<Annonce> annonces= new ArrayList<Annonce>();
    
    public void addAnnonce(Annonce annonce) {
    
    if (this.getAnnonces()==null) {
    	ArrayList<Annonce> annonces=new ArrayList<Annonce>();
    	annonces.add(annonce);
    	this.setAnnonces(annonces);
    }
    else {
    	this.getAnnonces().add(annonce);
    }
    }
    
}
