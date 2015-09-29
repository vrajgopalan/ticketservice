package com.vr.ticketapp.dao;
 
import java.util.List;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
import com.vr.ticketapp.entity.SeatHoldEntity;
import com.vr.ticketapp.entity.VenueEntity;
 
public interface SeatHoldDAO
{
    public boolean addSeatHold(SeatHoldEntity seatHold);
    public SeatHoldEntity getSeatHoldById(Integer id);
	boolean updateSeatHold(SeatHoldEntity seatHold);
}