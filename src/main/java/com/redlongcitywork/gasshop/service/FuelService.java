package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.Fuel;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface FuelService {

    List<Fuel> findAll();

    Fuel findById(Integer id);

    void saveFuel(Fuel fuel);

    void deleteFuel(Fuel fuel);

    void updateFuel(Fuel fuel);

    void deleteAll();

}
