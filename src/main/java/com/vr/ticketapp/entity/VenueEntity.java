package com.vr.ticketapp.entity;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
  
@Entity(name="VenueEntity")
@Table (name="venue")
public class VenueEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue
    @Column(name="venue_id")
    private Integer venueId;
    
    @NotNull
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
      
    @OneToMany(mappedBy="venueEntity",cascade = CascadeType.ALL)
    private List<VenueInstanceEntity> venueInstances = new ArrayList<VenueInstanceEntity>();
       
    public VenueEntity(String name, String description) {
    	this.name = name;
        this.description = description;
    }
    //Setters and Getters   

	public Integer getVenueId() {
		return venueId;
	}

	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VenueInstanceEntity> getVenueInstances() {
		return venueInstances;
	}

	public void setVenueInstances(List<VenueInstanceEntity> venueInstances) {
		this.venueInstances = venueInstances;
	}

	@Override
    public String toString() {
        return "Venue [id=" + venueId + ", description=" + description+ "]";
    }

}