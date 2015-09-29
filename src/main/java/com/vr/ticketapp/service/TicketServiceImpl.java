package com.vr.ticketapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.vr.ticketapp.dao.BookingDAO;
import com.vr.ticketapp.dao.SeatHoldDAO;
import com.vr.ticketapp.dao.VenueLevelDAO;
import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.SeatHoldEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;

@Service
@Qualifier("ticketService")
public class TicketServiceImpl implements TicketService {

    private static Logger log = Logger.getLogger(TicketServiceImpl.class);

	private static final long ONE_DAY = 86400000L;
	
	@Autowired
	private VenueLevelDAO venueLevelDAO;  

	@Autowired
	private BookingDAO bookingDAO;
	
	@Autowired
	private SeatHoldDAO seatHoldDAO;
	
	@Override
	@Transactional(propagation= Propagation.REQUIRED, readOnly=true)
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		//Get venue level or get all venue levels if venueLevel is not set
		Integer n = null;
		List<VenueLevelEntity> vs = getVenueLevelEntitiesForLevel
				(Optional.fromNullable(n), Optional.fromNullable(n), venueLevel);
		//Loop through venues and get sum of all seats available
		int seatCountAvailable = getSeatsAvailable(vs);
		log.debug("Num of seats at all venues " +seatCountAvailable);
		//Loop through booking from venue level to get num of seats 
		//reserved or held but not expired
		//Assume seat hold expires after a day
		int seatCountUnAvailable = getSeatsUnAvailable(getAllBookings(vs));
		log.debug("Num of seats booked/held at all venues " +seatCountUnAvailable);
		//return the difference
		return seatCountAvailable - seatCountUnAvailable;
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public SeatHoldEntity findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
		Optional<Integer> maxLevel, String customerEmail) {
		//Get venue levels from max to min level
		//This will be used to get available seats per level
		TreeMap<Integer, Integer> levelMap = new TreeMap<>();
		Map<Integer, VenueLevelEntity> levelVenueMap = new HashMap<Integer, VenueLevelEntity>();		
		Integer n = null;
		List<VenueLevelEntity> vs = getVenueLevelEntitiesForLevel
				(minLevel, maxLevel, Optional.fromNullable(n));
		log.debug("size of vs " + vs.size());
		//Get total seats
		int availableSeats = getSeatsAvailable(vs);
		log.debug("avail seats "+ availableSeats);
		//Get available seats per level
		for (VenueLevelEntity v : vs) {
			levelVenueMap.put(v.getLevel(), v);
			int freeSeats = v.getSeats() - getSeatsUnAvailable(v.getBookingEntities());
			levelMap.put(v.getLevel(), freeSeats);
		}

		//Check if enough seats are available
		//If available book from highest level
		SeatHoldEntity s = null;
		Set<BookingEntity> bookingEntities = null;
		if (availableSeats > numSeats){
			//create seat hold
			s = new SeatHoldEntity(customerEmail, new Date());
			//Create List of bookings
			bookingEntities = new HashSet<BookingEntity>();
			for (Integer level : levelMap.descendingKeySet()) {
				//See if this level has enough seats
				if (levelMap.get(level) >= numSeats){
					BookingEntity b = new BookingEntity(s, levelVenueMap.get(level), numSeats );
					bookingEntities.add(b);
				}else{
					//Else we need to check the next level
					numSeats = numSeats - levelMap.get(level);
					BookingEntity b = new BookingEntity(s, levelVenueMap.get(level), numSeats );
					bookingEntities.add(b);
				}
			}
			if (bookingEntities.size() > 0){
				s.setBookingEntities(bookingEntities);
			}
		}else{
			throw new TicketServiceException("Not enough seats available");
		}

		return hold(s);
	}

	private List<VenueLevelEntity> getVenueLevelEntitiesForLevel(Optional<Integer> minLevel,
			Optional<Integer> maxLevel, Optional<Integer> exactLevel) {
		List<VenueLevelEntity> vs = new ArrayList<VenueLevelEntity>();
		//Run a range query to get venue levels
		if (exactLevel.isPresent()){
			vs = getVenueLevels(exactLevel.get());
		}else if (minLevel.isPresent() && maxLevel.isPresent()){
			vs = getVenueLevelsInRange(minLevel.get(), maxLevel.get());
		}else if (minLevel.isPresent()){
			vs = getVenueLevelsGreaterThanMin(minLevel.get());		
		}else if (maxLevel.isPresent()){
			vs = getVenueLevelsLessThanMax(maxLevel.get());
		}else{
			vs = getAllVenueLevels();
		}
		System.out.println("venue level " + vs);

		return vs;
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public String reserveSeats(int seatHoldId, String customerEmail) {
		//Check seatHoldId and verify email
		//and whether seat hold has expired
		SeatHoldEntity s = verifySeatHold(seatHoldId, customerEmail);
		//If present update booking table and set reserved to true
		if (s != null){
			s.setReserved(true);
			updateSeatHold(s);
		}else{
			throw new TicketServiceException("Unable to reserve seats" );
		}
		return new StringBuilder(seatHoldId).toString();
	}

	private Set<BookingEntity> getAllBookings(List<VenueLevelEntity> vs) {
		Set<BookingEntity> bs = new HashSet<BookingEntity>();
		for (VenueLevelEntity v : vs) {
			if (v.getBookingEntities() != null){
				bs.addAll(v.getBookingEntities());
			}
		}
		return bs;
	}
	
	private int getSeatsAvailable (List<VenueLevelEntity> vs) {
		int seatCountAvailable = 0;
		for (VenueLevelEntity v : vs) {
			seatCountAvailable += v.getSeats();
		}
		System.out.println("Avail " + seatCountAvailable);
		return seatCountAvailable;
	}
	
	private List<VenueLevelEntity> getVenueLevelsLessThanMax(Integer max) {
		return venueLevelDAO.getVenueLevelsLessThanMax(max);

	}

	private List<VenueLevelEntity> getVenueLevelsGreaterThanMin(Integer min) {
		return venueLevelDAO.getVenueLevelsGreaterThanMin(min);
	}

	private List<VenueLevelEntity> getVenueLevelsInRange(Integer min,
			Integer max) {
		return venueLevelDAO.getVenueLevelsInRange(min, max);
	}

	private List<VenueLevelEntity> getAllVenueLevels() {
		return venueLevelDAO.getAllVenueLevels();	}

	private List<VenueLevelEntity> getVenueLevels(Integer id) {
		return venueLevelDAO.getVenueLevelByLevelId(id);
	}

	private int getSeatsUnAvailable(Set<BookingEntity> bs) {
		int seatCountUnAvailable = 0;
		if (bs == null) return seatCountUnAvailable;
		for (BookingEntity b : bs) {
			System.out.println(" ++ " + b.getSeatHoldEntity().isReserved() );
			System.out.println(" ++ " + b.getSeatHoldEntity().getReservationTime() );
			if(b.getSeatHoldEntity().isReserved()
				|| b.getSeatHoldEntity().getReservationTime().getTime() 
					+ ONE_DAY > System.currentTimeMillis() ){
				System.out.println("** " +b.getSeats());
				seatCountUnAvailable += b.getSeats();
			}
		}
		System.out.println("Unavail " + seatCountUnAvailable);
		return seatCountUnAvailable;
	}
	
	private SeatHoldEntity hold(SeatHoldEntity s) {
		seatHoldDAO.addSeatHold(s);
		return s;
	}

	private void updateSeatHold(SeatHoldEntity s) {
		seatHoldDAO.updateSeatHold(s);
	}

	private SeatHoldEntity verifySeatHold(int seatHoldId, String customerEmail) {
		SeatHoldEntity s = seatHoldDAO.getSeatHoldById(seatHoldId);
		if (s == null) return null;
		if (s.getEmail() != null && customerEmail != null 
				&& s.getEmail().equalsIgnoreCase(customerEmail)) {
			if (s.getReservationTime() != null 
					&& s.getReservationTime().getTime() + ONE_DAY > System.currentTimeMillis()) return s;
		}
		return null;
	}

}
