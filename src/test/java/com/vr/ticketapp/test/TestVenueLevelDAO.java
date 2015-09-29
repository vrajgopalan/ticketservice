package com.vr.ticketapp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
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
public class TestVenueLevelDAO
{
    private static Logger log = Logger.getLogger(TestVenueLevelDAO.class);
    
    @PersistenceContext
    private EntityManager manager;  
    
    @Autowired
    private VenueDAO venueDAO;  
    
    @Autowired
    private VenueInstanceDAO venueInstanceDAO;
    
    @Autowired
    private VenueLevelDAO venueLevelDao;     
    
    @Autowired
    private BookingDAO bookingDAO;    
    
    @Autowired
    private SeatHoldDAO seatHoldDAO;
    
    @Test
    @Transactional
    @Rollback(true)
    public void testAddVenueLevels() throws ParseException
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
        Assert.assertNotNull(venueLevelEntity.getVenueLevelId());           
    }
       
    @Test
    @Transactional
    @Rollback(true)
    public void testGetAllVenueLevels() throws ParseException
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
        VenueLevelEntity venueLevelEntity3 = new VenueLevelEntity(venueInstance, 3, "Main", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity3);
        VenueLevelEntity venueLevelEntity4 = new VenueLevelEntity(venueInstance, 4, "Orchestra", 50);
        venueLevelDao.addVenueLevel(venueLevelEntity4);
        List<VenueLevelEntity> levels = venueLevelDao.getAllVenueLevels();
        Assert.assertEquals(4, levels.size());           
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueLevelById() throws ParseException
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
        //
		VenueLevelEntity vs = venueLevelDao.getVenueLevelById(venueLevelEntity.getVenueLevelId());
		log.info("Venue Level Id = " + vs.getVenueLevelId());
        Assert.assertEquals(vs.getVenueLevelId(),venueLevelEntity.getVenueLevelId());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueLevelByLevelId() throws ParseException
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
        //

		List<VenueLevelEntity> vs = venueLevelDao.getVenueLevelByLevelId(1);
		Assert.assertEquals(1, vs.size());
		log.info("Venue Level  = " + vs);
        System.out.println("Venue Level before = " + venueLevelEntity);
		System.out.println("Venue Level after = " + vs.get(0));
        Assert.assertEquals(vs.get(0).getVenueLevelId(),venueLevelEntity.getVenueLevelId());
    
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueLevelsLessThanMax() throws ParseException
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
        VenueLevelEntity venueLevelEntity3 = new VenueLevelEntity(venueInstance, 3, "Main", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity3);
        VenueLevelEntity venueLevelEntity4 = new VenueLevelEntity(venueInstance, 4, "Orchestra", 50);
        venueLevelDao.addVenueLevel(venueLevelEntity4);
        List<VenueLevelEntity> levels = venueLevelDao.getVenueLevelsLessThanMax(3);
        Assert.assertEquals(3, levels.size());           
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueLevelsGreaterThanMin() throws ParseException
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
        VenueLevelEntity venueLevelEntity3 = new VenueLevelEntity(venueInstance, 3, "Main", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity3);
        VenueLevelEntity venueLevelEntity4 = new VenueLevelEntity(venueInstance, 4, "Orchestra", 50);
        venueLevelDao.addVenueLevel(venueLevelEntity4);
        List<VenueLevelEntity> levels = venueLevelDao.getVenueLevelsGreaterThanMin(2);
        Assert.assertEquals(3, levels.size());           
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testGetVenueLevelsInRange() throws ParseException
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
        VenueLevelEntity venueLevelEntity3 = new VenueLevelEntity(venueInstance, 3, "Main", 100);
        venueLevelDao.addVenueLevel(venueLevelEntity3);
        VenueLevelEntity venueLevelEntity4 = new VenueLevelEntity(venueInstance, 4, "Orchestra", 50);
        venueLevelDao.addVenueLevel(venueLevelEntity4);
        List<VenueLevelEntity> levels = venueLevelDao.getVenueLevelsInRange(2,3);
        Assert.assertEquals(2, levels.size());           
    }
    
    @Test    
    @Transactional
    @Rollback(true)
    public void testGetAllVenueLevelsWithBookings() throws ParseException
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
        
        BookingEntity b = bookingDAO.getBookingById(booking.getBookingId());
        List<VenueLevelEntity> v = venueLevelDao.getAllVenueLevels();

        int num = 0;
        
        num += v.get(0).getBookingEntities().size();

        Assert.assertEquals(1, num);        
    } 
}