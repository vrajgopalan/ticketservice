package com.vr.ticketapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vr.ticketapp.entity.SeatHoldEntity;

@Repository
@Transactional
public class SeatHoldDAOImpl implements SeatHoldDAO {

    @PersistenceContext
    private EntityManager manager;
 
	@Override
	public boolean addSeatHold(SeatHoldEntity seatHold) {
        try{
            manager.persist(seatHold);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
	}

	@Override
	public SeatHoldEntity getSeatHoldById(Integer id) {
		return manager.find(SeatHoldEntity.class, id);

	}
	
	@Override
	public boolean updateSeatHold(SeatHoldEntity seatHold) {
        try{
            manager.merge(seatHold);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
	}

}
