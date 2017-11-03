package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.GasStationDao;
import com.redlongcitywork.gasshop.models.GasStation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Service("gasStationService")
public class GasStationServiceImpl implements GasStationService {

    @Autowired
    private GasStationDao dao;

    @Override
    public List<GasStation> findAll() {
        return dao.findAll();
    }

    @Override
    public GasStation findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void saveGasStation(GasStation station) {
        dao.save(station);
    }

    @Override
    public void deleteGasStation(GasStation station) {
        dao.delete(station);
    }

    @Override
    public void updateGasStation(GasStation station) {
        GasStation entity = dao.findById(station.getId());
        if (entity != null) {
            entity.setName(station.getName());
        }
    }

    @Override
    public void deleteAll() {
        List<GasStation> list = dao.findAll();
        if (list != null) {
            for (GasStation station : list) {
                dao.delete(station);
            }
        }
    }

}
