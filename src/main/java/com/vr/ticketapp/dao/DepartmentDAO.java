package com.vr.ticketapp.dao;
 
import java.util.List;

import com.vr.ticketapp.entity.DepartmentEntity;
 
public interface DepartmentDAO
{
    public List<DepartmentEntity> getAllDepartments();
    public DepartmentEntity getDepartmentById(Integer id);
    public boolean addDepartment(DepartmentEntity dept);
    public boolean removeDepartment(DepartmentEntity dept);
    public boolean removeAllDepartments();
}
