package com.vr.ticketapp.dao;
 
import java.util.List;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
 
public interface BookingDAO
{
    public boolean addBooking(BookingEntity booking);
    public BookingEntity getBookingById(Integer id);
	void addAllBookings(List<BookingEntity> b);
}