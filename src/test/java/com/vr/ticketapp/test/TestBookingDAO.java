package com.vr.ticketapp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.vr.ticketapp.dao.BookingDAO;
import com.vr.ticketapp.dao.SeatHoldDAO;
import com.vr.ticketapp.dao.VenueDAO;
import com.vr.ticketapp.dao.VenueInstanceDAO;
import com.vr.ticketapp.dao.VenueLevelDAO;
import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.SeatHoldEntity;
import com.vr.ticketapp.entity.VenueEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;
 
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBookingDAO
{
    @Autowired
    private VenueDAO venueDAO;  
    
    @Autowired
    private VenueInstanceDAO venueInstanceDAO;
    
    @Autowired
    private VenueLevelDAO venueLevelDao;   
    
    @Autowired
    private SeatHoldDAO seatHoldDAO;
     
    @Autowired
    private BookingDAO bookingDAO;     
     
    @Test
    @Transactional
    @Rollback(true)
    public void testAddBooking() throws ParseException
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity venueInstance = new VenueInstanceEntity(venue, s, e);
		venueInstanceDAO.addVenueInstance(venueInstance);
        VenueLevelEntity venueLevelEntity = new VenueLevelEntity(venueInstance, 1, "Balcony2", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity);
    	SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());    	
    	seatHoldDAO.addSeatHold(seatHoldEntity);
    	
    	BookingEntity booking = new BookingEntity(seatHoldEntity, venueLevelEntity, 5);                
        bookingDAO.addBooking(booking);
        
        Assert.assertNotNull(booking.getBookingId());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetBookingById() throws ParseException
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity venueInstance = new VenueInstanceEntity(venue, s, e);
		venueInstanceDAO.addVenueInstance(venueInstance);
        VenueLevelEntity venueLevelEntity = new VenueLevelEntity(venueInstance, 1, "Balcony2", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity);
    	SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());    	
    	seatHoldDAO.addSeatHold(seatHoldEntity);
    	
    	BookingEntity booking = new BookingEntity(seatHoldEntity, venueLevelEntity, 5);                
        bookingDAO.addBooking(booking);
    }   
    
    @Test
    @Transactional
    @Rollback(true)
    public void testAddAllBookings() throws ParseException
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity venueInstance = new VenueInstanceEntity(venue, s, e);
		venueInstanceDAO.addVenueInstance(venueInstance);
        VenueLevelEntity venueLevelEntity = new VenueLevelEntity(venueInstance, 1, "Balcony2", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity);
        VenueLevelEntity venueLevelEntity2 = new VenueLevelEntity(venueInstance, 2, "Balcony1", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity2);
    	SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());    	
    	seatHoldDAO.addSeatHold(seatHoldEntity);
    	
    	BookingEntity booking = new BookingEntity(seatHoldEntity, venueLevelEntity, 5);  
    	BookingEntity booking2 = new BookingEntity(seatHoldEntity, venueLevelEntity2, 10);
    	List<BookingEntity> bs = new ArrayList<BookingEntity>();
    	bs.add(booking);
    	bs.add(booking2);
        bookingDAO.addAllBookings(bs);        
        
        Assert.assertNotNull(booking.getBookingId());
        Assert.assertNotNull(booking2.getBookingId());
        Assert.assertEquals(seatHoldEntity.getSeatHoldId(), booking2.getSeatHoldEntity().getSeatHoldId());
    } 
     
}