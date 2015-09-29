package com.vr.ticketapp.dao;
 
import java.util.List;
 





import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
 
@Repository
public class BookingDAOImpl implements BookingDAO {
     
    @PersistenceContext
    private EntityManager manager;
	
	@Override
	public void addAllBookings(List<BookingEntity> b) {
		for (BookingEntity bookingEntity : b) {
			addBooking(bookingEntity);
		}
	}	

	@Override
	public boolean addBooking(BookingEntity booking) {
        try{
            manager.persist(booking);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
	}

	@Override
	public BookingEntity getBookingById(Integer id) {
        return manager.find(BookingEntity.class, id);

	}

 
}