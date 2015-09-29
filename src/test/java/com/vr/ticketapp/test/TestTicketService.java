package com.vr.ticketapp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.validator.util.privilegedactions.GetClassLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.vr.ticketapp.dao.BookingDAO;
import com.vr.ticketapp.dao.DepartmentDAO;
import com.vr.ticketapp.dao.EmployeeDAO;
import com.vr.ticketapp.dao.SeatHoldDAO;
import com.vr.ticketapp.dao.VenueDAO;
import com.vr.ticketapp.dao.VenueInstanceDAO;
import com.vr.ticketapp.dao.VenueLevelDAO;
import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.DepartmentEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
import com.vr.ticketapp.entity.SeatHoldEntity;
import com.vr.ticketapp.entity.VenueEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;
import com.vr.ticketapp.service.TicketService;
import com.vr.ticketapp.service.TicketServiceException;
import com.vr.ticketapp.service.TicketServiceImpl;
 
@RunWith(MockitoJUnitRunner.class)
public class TestTicketService
{
    private static Logger log = Logger.getLogger(TestTicketService.class);
    
    @InjectMocks
    private TicketService ticketService = new TicketServiceImpl();

    @Mock
    VenueDAO venueDAO;
    
    @Mock
    VenueInstanceDAO venueInstanceDAO;
    
    @Mock
    VenueLevelDAO venueLevelDao;
    
    @Mock
    SeatHoldDAO seatHoldDAO;
    
    @Mock
    BookingDAO bookingDAO;
    
    @Rule
    public final ExpectedException e = ExpectedException.none();
    
    @Before
    public void init() throws ParseException{
    	MockitoAnnotations.initMocks(ticketService);
    	//Prepare Data
    	VenueEntity venue = new VenueEntity("CH", "Concert Hall");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = sdf.parse("10/01/2015 10:00:00");
        Date e = sdf.parse("10/02/2015 18:00:00");
		VenueInstanceEntity venueInstance = new VenueInstanceEntity(venue, s, e);
        VenueLevelEntity venueLevelEntity = new VenueLevelEntity(venueInstance, 1, "Balcony2", 100);
        VenueLevelEntity venueLevelEntity2 = new VenueLevelEntity(venueInstance, 2, "Balcony1", 100);
        VenueLevelEntity venueLevelEntity3 = new VenueLevelEntity(venueInstance, 3, "Main", 100);
        VenueLevelEntity venueLevelEntity4 = new VenueLevelEntity(venueInstance, 4, "Orchestra", 50);
        SeatHoldEntity seatHoldEntity = new SeatHoldEntity("customer@example.com", new Date());
        seatHoldEntity.setSeatHoldId(1);
        Date sh = sdf.parse("27/09/2015 18:00:00");
        SeatHoldEntity seatHoldEntity2 = new SeatHoldEntity("customer2@example.com", sh);
        seatHoldEntity2.setSeatHoldId(2);
    	BookingEntity booking = new BookingEntity(seatHoldEntity, venueLevelEntity, 5); 
    	Set<BookingEntity> set = new HashSet<BookingEntity>(); 
    	venueLevelEntity.setBookingEntities(set);
    	venueLevelEntity.getBookingEntities().add(booking);
    	seatHoldEntity.setBookingEntities(set);
    	seatHoldEntity.getBookingEntities().add(booking);
    	List<VenueLevelEntity> venueLevelEntities = new ArrayList<VenueLevelEntity>();
    	venueLevelEntities.add(venueLevelEntity);
    	venueLevelEntities.add(venueLevelEntity2);
    	venueLevelEntities.add(venueLevelEntity3);
    	venueLevelEntities.add(venueLevelEntity4);
	
    	//Mock conditions
    	when(venueLevelDao.getAllVenueLevels()).thenReturn(venueLevelEntities);
    	when(venueLevelDao.getVenueLevelByLevelId(anyInt())).thenReturn(venueLevelEntities);
    	when(venueLevelDao.getVenueLevelsInRange(anyInt(), anyInt())).thenReturn(venueLevelEntities);
    	when(seatHoldDAO.addSeatHold(any(SeatHoldEntity.class))).thenReturn(true);
    	when(seatHoldDAO.getSeatHoldById(anyInt())).thenReturn(seatHoldEntity);
    }
    
    @Test
    public void testNumSeatsAvailable() throws ParseException
    {
    	
        Integer n = null;
    	int num = ticketService.numSeatsAvailable(Optional.fromNullable(n));
		log.info("Num = " + num);
        Assert.assertEquals(345, num);
    }
    
    @Test
    public void testFindAndHoldSeats()
    {
    	SeatHoldEntity s = ticketService.findAndHoldSeats(5, 
    			Optional.fromNullable(new Integer(1)), 
    			Optional.fromNullable(new Integer(3)),
    			"customerEmail@example.com");
    	Assert.assertNotNull(s);
    	Assert.assertNotNull(s.getBookingEntities());
    }
    
    @Test
    public void testReserveSeats()
    {
    	String id = ticketService.reserveSeats(1,
    			"customer@example.com");
    	Assert.assertNotNull(id);
    }
    
    @Test
    public void testReserveSeatsFail()
    {
    	e.expect(TicketServiceException.class);

    	String id = ticketService.reserveSeats(2,
    			"customer2@example.com");
    }

}