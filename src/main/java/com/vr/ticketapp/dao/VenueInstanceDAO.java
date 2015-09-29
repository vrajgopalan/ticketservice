package com.vr.ticketapp.dao;
 
import java.util.List;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;
import com.vr.ticketapp.entity.VenueInstanceEntity;
 
public interface VenueInstanceDAO
{
    public boolean addVenueInstance(VenueInstanceEntity v);
    public VenueInstanceEntity getVenueInstanceById(Integer id);
}