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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vr.ticketapp.dao.BookingDAO;
import com.vr.ticketapp.dao.DepartmentDAO;
import com.vr.ticketapp.dao.EmployeeDAO;
import com.vr.ticketapp.dao.VenueDAO;
import com.vr.ticketapp.dao.VenueLevelDAO;
import com.vr.ticketapp.dao.VenueInstanceDAO;
import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.DepartmentEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
import com.vr.ticketapp.entity.VenueEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;
 
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestVenueInstanceDAO
{
    private static Logger log = Logger.getLogger(TestVenueInstanceDAO.class);
    @Autowired
    private VenueDAO venueDAO;  
    
    @Autowired
    private VenueInstanceDAO venueInstanceDao;
    
    @Test
    @Transactional
    @Rollback(true)
    public void testAddVenueLevelInstance() throws ParseException
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity v = new VenueInstanceEntity(venue, s, e);
		venueInstanceDao.addVenueInstance(v);		        
        Assert.assertNotNull(v.getVenueInstanceId());        
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueInstanceById() throws ParseException
    {
        VenueEntity venue = new VenueEntity("CH", "Concert Hall");
		venueDAO.addVenue(venue);
		
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity v = new VenueInstanceEntity(venue, s, e);
		venueInstanceDao.addVenueInstance(v);	
		
		VenueInstanceEntity v2 = venueInstanceDao.getVenueInstanceById(v.getVenueInstanceId());
        Assert.assertEquals(v2.getVenueInstanceId(), v.getVenueInstanceId());        
    }
     
}