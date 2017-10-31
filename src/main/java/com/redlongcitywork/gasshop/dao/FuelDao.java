package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.Fuel;
import java.util.List;

/**
 *
 * @author redlongcity 28/10/2017
 */
public interface FuelDao extends AbstractDao<Fuel> {

    @Override
    List<Fuel> findAll();

    @Override
    Fuel findById(Integer id);

    @Override
    Fuel save(Fuel fuel);

    @Override
    void delete(Fuel fuel);

    @Override
    void update(Fuel fuel);
}
