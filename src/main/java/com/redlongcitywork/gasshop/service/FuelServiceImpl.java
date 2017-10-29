package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.FuelDao;
import com.redlongcitywork.gasshop.models.Fuel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Service("fuelService")
public class FuelServiceImpl implements FuelService {

    @Autowired
    private FuelDao dao;

    @Override
    public List<Fuel> findAll() {
        return dao.findAll();
    }

    @Override
    public Fuel findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void saveFuel(Fuel fuel) {
        dao.save(fuel);
    }

    @Override
    public void deleteFuel(Fuel fuel) {
        dao.delete(fuel);
    }

    @Override
    public void updateFuel(Fuel fuel) {
        Fuel entity = dao.findById(fuel.getId());
        if (entity != null) {
            entity.setName(fuel.getName());
        }
    }

    @Override
    public void deleteAll() {
        List<Fuel> list = dao.findAll();
        if (list != null) {
            for (Fuel fuel : list) {
                dao.delete(fuel);
            }
        }
    }

}
