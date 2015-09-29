package com.vr.ticketapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vr.ticketapp.entity.VenueLevelEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;

@Repository
public class VenueInstanceDAOImpl implements VenueInstanceDAO {

    @PersistenceContext
    private EntityManager manager;   
    
	@Override
	public boolean addVenueInstance(VenueInstanceEntity v) {
		try{
            manager.persist(v);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
	}

	@Override
	public VenueInstanceEntity getVenueInstanceById(Integer id) {
		return manager.find(VenueInstanceEntity.class, id);

	}
}
