package com.vr.ticketapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.SeatHoldEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;

@Repository
public class VenueLevelDAOImpl implements VenueLevelDAO {

	@PersistenceContext
	private EntityManager manager;   

	private static final String BASE_SQL = "select v.venue_level_id, v.seats as vseats,"
			+ " b.booking_id, b.seats as bseats, v.venue_level,"
			+ " s.seat_hold_id, s.reservation_time, s.reserved"
			+ " from venue_levels v left outer join bookings b on"
			+ " v.venue_level_id = b.venue_level_id"
			+ " left outer join seat_hold_info s on"
			+ " b.seat_hold_id = s.seat_hold_id";

	@Override
	public boolean addVenueLevel(VenueLevelEntity venueLevel) {
		try{
			manager.persist(venueLevel);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public VenueLevelEntity getVenueLevelById(Integer id) {
		VenueLevelEntity v = manager.find(VenueLevelEntity.class, id);
		return v;
	}

	@Override
	public List<VenueLevelEntity> getAllVenueLevels() {
		List<Object[]> results = manager.createNativeQuery(BASE_SQL
 ).getResultList();
		List<VenueLevelEntity> levels = mapResults(results);
		return levels;
	}

	@Override
	public List<VenueLevelEntity> getVenueLevelByLevelId(Integer id) {

		List<Object[]> results = manager.createNativeQuery(
				BASE_SQL + " where v.venue_level =:id" )
						.setParameter("id", id)
						.getResultList();
		List<VenueLevelEntity> levels = mapResults(results);
		return levels;

	}

	@Override
	public List<VenueLevelEntity> getVenueLevelsLessThanMax(Integer max) {

		List<Object[]> results = manager.createNativeQuery(
				BASE_SQL + " where v.venue_level <= :max" )
						.setParameter("max", max)
						.getResultList();
		List<VenueLevelEntity> levels = mapResults(results);
		return levels;

	}

	@Override
	public List<VenueLevelEntity> getVenueLevelsGreaterThanMin(Integer min) {

		List<Object[]> results = manager.createNativeQuery(
				BASE_SQL + " where v.venue_level >= :min" )
						.setParameter("min", min)
						.getResultList();
		List<VenueLevelEntity> levels = mapResults(results);
		return levels;
	}

	@Override
	public List<VenueLevelEntity> getVenueLevelsInRange(Integer min, Integer max) {

		List<Object[]> results = manager.createNativeQuery(
				BASE_SQL + " where v.venue_level >= :min and v.venue_level <=:max" )
						.setParameter("min", min)
						.setParameter("max", max)				
						.getResultList();
		List<VenueLevelEntity> levels = mapResults(results);
		return levels;
	}

	private List<VenueLevelEntity> mapResults(List<Object[]> results) {
		Map<Integer, VenueLevelEntity> m = new HashMap<>();
		if (results.size() > 0){
			int sid = 0, samid = 0;
			for (Object[] row : results) {
				//All these are not null columns. Still checking
				if (row[0] != null && row[1] != null && row[4] != null){
					int venueLevelId = (Integer) row[0];
					int level = (Integer) row[4];
					int vseats = (Integer) row[1];	
					if (!m.containsKey(level)) {
						//If not in map add a new venue level entity
						VenueLevelEntity v = new VenueLevelEntity();
						v.setVenueLevelId(venueLevelId);
						v.setLevel(level);
						v.setSeats(vseats);
						m.put(level, v);
					}
					//Create Booking entities
					//Not null fields
					if (row[2] != null && row[3] != null){
						BookingEntity b = new BookingEntity();
						int bookingId= (Integer) row[2];
						b.setBookingId(bookingId);
						int bookingSeats= (Integer) row[3];
						b.setSeats(bookingSeats);
						//Create and add seat hold info
						if (row[5] != null && row [6] != null && row[7] != null){
							SeatHoldEntity s = new SeatHoldEntity();
							Integer seatHoldId = (Integer) row[5];
							Date reservationTime = (Date) row[6];
							Boolean reserved = (Boolean) row[7];
							s.setSeatHoldId(seatHoldId);
							s.setReserved(reserved);
							s.setReservationTime(reservationTime);
							b.setSeatHoldEntity(s);
						}
						//Create Booking entities
						if (m.get(level).getBookingEntities() == null){
							Set<BookingEntity> bs = new HashSet<BookingEntity>();
							bs.add(b);
							m.get(level).setBookingEntities(bs);
						}else{
							m.get(level).getBookingEntities().add(b);
						}
					}

				}        		


			}
		}
		return new ArrayList<VenueLevelEntity>(m.values());
	}
}
