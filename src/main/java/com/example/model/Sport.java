package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Data @NoArgsConstructor
@Table(name = "sport")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = User.class)
public class Sport implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "sport_id")
	private Long id;
    
    @Column(name = "name" , nullable= false) 
    @NotNull
    private String name;
    
    @Column(name = "numberofplayers")
  
    private Long numberOfPlayers;
    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "thumb")
    private String thumb;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sport_annonces",referencedColumnName = "annonce_id")
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
