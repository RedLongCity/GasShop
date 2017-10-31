package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.GasPortionDao;
import com.redlongcitywork.gasshop.models.GasPortion;
import com.redlongcitywork.gasshop.models.GasPortion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity
 * 29/10/2017
 */
@Service("gasPorionService")
public class GasPortionServiceImpl implements GasPortionService{

    @Autowired
    private GasPortionDao dao;

    @Override
    public List<GasPortion> findAll() {
        return dao.findAll();
    }

    @Override
    public GasPortion findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public GasPortion saveGasPortion(GasPortion portion) {
       return dao.save(portion);
    }

    @Override
    public void deleteGasPortion(GasPortion portion) {
        dao.delete(portion);
    }

    @Override
    public void updateGasPortion(GasPortion portion) {
        GasPortion entity = dao.findById(portion.getId());
        if (entity != null) {
            entity.setAmount(portion.getAmount());
            entity.setStation(portion.getStation());
            entity.setFuel(portion.getFuel());
        }
    }

    @Override
    public void deleteAll() {
        List<GasPortion> list = dao.findAll();
        if (list != null) {
            for (GasPortion portion : list) {
                dao.delete(portion);
            }
        }
    }
    
}
