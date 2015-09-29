package com.vr.ticketapp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.validator.util.privilegedactions.GetClassLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
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
import com.vr.ticketapp.service.TicketService;
 
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ITestTicketService
{
    private static Logger log = Logger.getLogger(ITestTicketService.class);
    
    @Autowired
    private TicketService ticketService;

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
    
    @Before
    public void init() throws ParseException{
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity venueInstance = new VenueInstanceEntity(venue, s, e);
		venueInstanceDAO.addVenueInstance(venueInstance);
        VenueLevelEntity venueLevelEntity = new VenueLevelEntity(venueInstance, 1, "Balcony2", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity);
        VenueLevelEntity venueLevelEntity2 = new VenueLevelEntity(venueInstance, 2, "Balcony1", 50);
        venueLevelDao.addVenueLevel(venueLevelEntity2);        
    	SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());    	
    	seatHoldDAO.addSeatHold(seatHoldEntity);    	
    	BookingEntity booking = new BookingEntity(seatHoldEntity, venueLevelEntity, 45);                
        bookingDAO.addBooking(booking);

    }
    @Test
    public void testNumSeatsAvailableMultipleLevels()
    {
    	Integer n = null;
    	int num = ticketService.numSeatsAvailable(Optional.fromNullable(n));
    	log.info("Num of seats available = " + num );
    	Assert.assertEquals(105, num);
    }
    
    @Test
    public void testNumSeatsAvailableSingleLevel()
    {
    	Integer n = 1;
    	int num = ticketService.numSeatsAvailable(Optional.fromNullable(n));
    	log.info("Num of seats available = " + num );
    	Assert.assertEquals(55, num);
    }
    
    
    @Test
    public void testFindAndHoldSeats()
    {
        Assert.fail("Not implemented");
    }
    
    @Test
    public void testReserveSeatsFail()
    {
    	Assert.fail("Not implemented");
    }

}