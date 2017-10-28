package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.Fuel;
import java.util.List;

/**
 *
 * @author redlongcity
 * 28/10/2017
 */
public interface FuelDao {
    
    List<Fuel> findAll();
    
    Fuel findById(Integer id);
    
    void save(Fuel fuel);
    
    void delete(Fuel fuel);
    
    void update(Fuel fuel);
}
