package com.vr.ticketapp.entity;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
  
@Entity(name="SeatHoldEntity")
@Table (name="seat_hold_info")
public class SeatHoldEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue
    @Column(name="seat_hold_id")
    private Integer seatHoldId;

    @NotNull
    @Column(name="email")
    private String email;

    @NotNull
    @Column(name="reservation_time")
    private Date reservationTime;
    
    @Column(name="reserved")
    private boolean reserved;
    
    @OneToMany(mappedBy="seatHoldEntity",cascade=CascadeType.ALL)
    private Set<BookingEntity> bookingEntities;
    
    public SeatHoldEntity() {}
       
    public SeatHoldEntity(String email, Date reservationTime) {
        this.email = email;
        this.reservationTime = reservationTime;
    }
       
    //Setters and Getters

	public Integer getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(Integer seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public Set<BookingEntity> getBookingEntities() {
		return bookingEntities;
	}

	public void setBookingEntities(Set<BookingEntity> bookingEntities) {
		this.bookingEntities = bookingEntities;
	}

	@Override
    public String toString() {
        return "EmployeeVO [id=" + seatHoldId + ", email=" + email
                + ", reservationTime=" + reservationTime + "]";
    }
}