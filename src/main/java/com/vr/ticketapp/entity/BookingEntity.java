package com.vr.ticketapp.entity;
 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
  
@Entity(name="BookingEntity")
@Table (name="bookings")
public class BookingEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue
    @Column(name="booking_id")
    private Integer bookingId;
    
    @ManyToOne
    @JoinColumn(name = "seat_hold_id", nullable=false)    
    private SeatHoldEntity seatHoldEntity;
    

    @ManyToOne
    @JoinColumn(name = "venue_level_id", nullable=false)        
    private VenueLevelEntity venueLevelEntity;
    
    @NotNull
    @Column(name="seats")
    private Integer seats;  
      
    public BookingEntity() {}
       
    public BookingEntity(SeatHoldEntity seatHoldEntity, VenueLevelEntity venueLevelEntity, Integer seats) {
        this.seatHoldEntity = seatHoldEntity;
        this.venueLevelEntity = venueLevelEntity;
        this.seats = seats;
    }

    //Setters and Getters    
	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public SeatHoldEntity getSeatHoldEntity() {
		return seatHoldEntity;
	}

	public void setSeatHoldEntity(SeatHoldEntity seatHoldEntity) {
		this.seatHoldEntity = seatHoldEntity;
	}

	public VenueLevelEntity getVenueLevelEntity() {
		return venueLevelEntity;
	}

	public void setVenueLevelEntity(VenueLevelEntity venueLevelEntity) {
		this.venueLevelEntity = venueLevelEntity;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

    @Override
    public String toString() {
        return "Booking [id=" + bookingId + ", seatHoldId=" + seatHoldEntity
                + ", seats=" + "]";
    }

}