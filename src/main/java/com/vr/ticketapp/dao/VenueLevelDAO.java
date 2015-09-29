package com.vr.ticketapp.dao;
 
import java.util.List;

import com.vr.ticketapp.entity.BookingEntity;
import com.vr.ticketapp.entity.EmployeeEntity;
import com.vr.ticketapp.entity.VenueEntity;
import com.vr.ticketapp.entity.VenueLevelEntity;
 
public interface VenueLevelDAO
{
    public boolean addVenueLevel(VenueLevelEntity venueLevel);
    public VenueLevelEntity getVenueLevelById(Integer id);
    public List<VenueLevelEntity> getAllVenueLevels();
	public List<VenueLevelEntity> getVenueLevelByLevelId(Integer id);
	public List<VenueLevelEntity> getVenueLevelsLessThanMax(Integer max);
	public List<VenueLevelEntity> getVenueLevelsGreaterThanMin(Integer min);
	public List<VenueLevelEntity> getVenueLevelsInRange(Integer min, Integer max);
}