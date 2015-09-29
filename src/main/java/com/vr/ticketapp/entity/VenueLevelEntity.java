package com.vr.ticketapp.entity;
 
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
  
@Entity(name="VenueLevelEntity")
@Table (name="venue_levels")
public class VenueLevelEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue
    @Column(name="venue_level_id")
    private Integer venueLevelId;
    
    @NotNull
    @Column(name="venue_level")
    private Integer level;
              
    @NotNull
    @ManyToOne
    @JoinColumn(name = "venue_instance_id", referencedColumnName="venue_instance_id")    
    private VenueInstanceEntity venueInstanceEntity;
    
    @OneToMany(mappedBy="venueLevelEntity", fetch = FetchType.LAZY)
    private Set<BookingEntity> bookingEntities;
    
    @Column(name="description")
    private String description;
    
    @NotNull
    @Column(name="seats")
    private Integer seats;
    
	public VenueLevelEntity() {}
       
    public VenueLevelEntity(VenueInstanceEntity venueInstanceEntity, 
    		Integer level, String description, Integer seats) {
        this.level = level;
        this.venueInstanceEntity = venueInstanceEntity;
        this.description = description;
        this.seats = seats;
    }
    
    public Integer getVenueLevelId() {
		return venueLevelId;
	}

	public void setVenueLevelId(Integer venueLevelId) {
		this.venueLevelId = venueLevelId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public VenueInstanceEntity getVenueInstanceEntity() {
		return venueInstanceEntity;
	}

	public void setVenueInstanceEntity(VenueInstanceEntity venueInstanceEntity) {
		this.venueInstanceEntity = venueInstanceEntity;
	}

	public Set<BookingEntity> getBookingEntities() {
		return bookingEntities;
	}

	public void setBookingEntities(Set<BookingEntity> bookingEntities) {
		this.bookingEntities = bookingEntities;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Override
    public String toString() {
        return "VenueLevelEntity [id=" + venueLevelId + ", description=" 
        		+ description + ", level " + level +
        		", seats " + seats + "]";
    }

}