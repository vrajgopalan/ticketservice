package com.vr.ticketapp.test;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.validator.util.privilegedactions.GetClassLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.vr.ticketapp.dao.BookingDAO;
import com.vr.ticketapp.dao.VenueDAO;
import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.VenueEntity;
 
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestVenueDAO
{
    private static Logger log = Logger.getLogger(TestVenueDAO.class);
    @Autowired
    private VenueDAO venueDAO;     
     
    @Test
    @Transactional
    @Rollback(true)
    public void testAddVenue()
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
		log.info("Venue Id = " + venue.getVenueId());
        Assert.assertNotNull(venue.getVenueId());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueById()
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
		
		VenueEntity v = venueDAO.getVenueById(venue.getVenueId());
		log.info("Venue Id = " + venue.getVenueId());
        Assert.assertEquals(v.getVenueId(),venue.getVenueId());
    }
     
}