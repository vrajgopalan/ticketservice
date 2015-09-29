package com.vr.ticketapp.dao;
 
import java.util.List;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.VenueEntity;
 
public interface VenueDAO
{
    public boolean addVenue(VenueEntity venue);
    public VenueEntity getVenueById(Integer id);
}