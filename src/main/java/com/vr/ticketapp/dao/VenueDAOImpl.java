package com.vr.ticketapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.vr.ticketapp.entity.VenueEntity;

@Repository
public class VenueDAOImpl implements VenueDAO {

    @PersistenceContext
    private EntityManager manager;
    
	@Override
	public boolean addVenue(VenueEntity venue) {
		try{
            manager.persist(venue);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
	}

	@Override
	public VenueEntity getVenueById(Integer id) {
		return manager.find(VenueEntity.class, id);
	}

}
