package com.vr.ticketapp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vr.ticketapp.dao.SeatHoldDAO;
import com.vr.ticketapp.dao.VenueDAO;
import com.vr.ticketapp.dao.VenueLevelDAO;
import com.vr.ticketapp.dao.VenueInstanceDAO;
import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.SeatHoldEntity;
import com.vr.ticketapp.entity.VenueEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;
 
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSeatHoldDAO
{
    private static Logger log = Logger.getLogger(TestSeatHoldDAO.class);
    
    @Autowired
    private VenueDAO venueDAO;  
    
    @Autowired
    private VenueLevelDAO venueLevelDao;
    
    @Autowired
    private VenueInstanceDAO venueInstanceDao;
    
    @Autowired
    private SeatHoldDAO seatHoldDao;
     
    @Test
    @Transactional
    @Rollback(true)
    public void testAddSeatHold() throws ParseException
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity venueInstance = new VenueInstanceEntity(venue, s, e);
		venueInstanceDao.addVenueInstance(venueInstance);
        VenueLevelEntity venueLevelEntity = new VenueLevelEntity(venueInstance, 1, "Balcony2", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity);
        SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());  
    	Set<BookingEntity> bookingEntities = new HashSet<BookingEntity>();
    	//See if this level has enough seats
    	BookingEntity b = new BookingEntity(seatHoldEntity, venueLevelEntity, 5 );
    	bookingEntities.add(b);
    	seatHoldEntity.setBookingEntities(bookingEntities);
    	seatHoldDao.addSeatHold(seatHoldEntity);
    	
    	SeatHoldEntity sh = seatHoldDao.getSeatHoldById(seatHoldEntity.getSeatHoldId());
        Assert.assertNotNull(sh.getSeatHoldId());
        Assert.assertNotNull(sh.getBookingEntities());

    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetSeatHoldById() throws ParseException
    {
    	SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());    	
    	seatHoldDao.addSeatHold(seatHoldEntity);
    	
    	SeatHoldEntity s = seatHoldDao.getSeatHoldById(seatHoldEntity.getSeatHoldId());
        Assert.assertEquals(s.getSeatHoldId(),seatHoldEntity.getSeatHoldId());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateSeatHold() throws ParseException
    {
    	SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());    	
    	seatHoldDao.addSeatHold(seatHoldEntity);
    	
    	SeatHoldEntity s = seatHoldDao.getSeatHoldById(seatHoldEntity.getSeatHoldId());
        Assert.assertEquals(s.getSeatHoldId(),seatHoldEntity.getSeatHoldId());
        Assert.assertEquals(s.isReserved(),false);

    	s.setReserved(true);
    	seatHoldDao.updateSeatHold(s);
    	
    	s = seatHoldDao.getSeatHoldById(seatHoldEntity.getSeatHoldId());
        Assert.assertEquals(s.getSeatHoldId(),seatHoldEntity.getSeatHoldId());
        Assert.assertEquals(true,s.isReserved());
    }    

     
}