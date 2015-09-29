package com.vr.ticketapp.entity;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
  
@Entity(name="VenueInstanceEntity")
@Table (name="venue_instances")
public class VenueInstanceEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue
    @Column(name="venue_instance_id")
    private Integer venueInstanceId;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName="venue_id")
    private VenueEntity venueEntity;
    
    @OneToMany(mappedBy="venueInstanceEntity",cascade=CascadeType.ALL)
    private List<VenueLevelEntity> venueLevelEntities;
      
    @Column(name="start_time")
    private Date starttime;
    
    @Column(name="end_time")    
    private Date endtime;  

	public VenueInstanceEntity() {}
       
    public VenueInstanceEntity(VenueEntity venue, Date starttime, Date endtime) {
        this.venueEntity = venue;
        this.starttime = starttime;
        this.endtime = endtime;
    }
     
	public Integer getVenueInstanceId() {
		return venueInstanceId;
	}

	public void setVenueInstanceId(Integer venueInstanceId) {
		this.venueInstanceId = venueInstanceId;
	}

	public List<VenueLevelEntity> getVenueLevelEntities() {
		return venueLevelEntities;
	}

	public void setVenueLevelEntities(List<VenueLevelEntity> venueLevelEntities) {
		this.venueLevelEntities = venueLevelEntities;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public VenueEntity getVenueEntity() {
		return venueEntity;
	}

	public void setVenueEntity(VenueEntity venueEntity) {
		this.venueEntity = venueEntity;
	}
	
    @Override
    public String toString() {
        return "VenueLevelInstanceEntity [id=" + venueInstanceId + ", starttime=" + starttime + 
        		", endtime=" + endtime + "]";
    }

}