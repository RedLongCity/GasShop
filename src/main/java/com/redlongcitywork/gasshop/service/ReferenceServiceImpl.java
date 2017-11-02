package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.ReferenceDao;
import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.Reference;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Service("referenceService")
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    private ReferenceDao dao;

    @Override
    public List<Reference> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Reference> findByFuel(Fuel fuel) {
        return dao.findByFuel(fuel);
    }

    @Override
    public Reference findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void saveReference(Reference reference) {
        dao.save(reference);
    }

    @Override
    public void deleteReference(Reference reference) {
        dao.delete(reference);
    }

    @Override
    public void updateReference(Reference reference) {
        Reference entity = dao.findById(reference.getId());
        if (entity != null) {
            entity.setCost(reference.getCost());
            entity.setStation(reference.getStation());
            entity.setFuel(reference.getFuel());
        }
    }

    @Override
    public Float getCost(Fuel fuel, GasStation station) {
        return dao.getCost(fuel, station);
    }

    @Override
    public Float getAverageCost(Fuel fuel) {
        return dao.getAverageCost(fuel);
    }

    @Override
    public void deleteAll() {
        List<Reference> list = dao.findAll();
        if (list != null) {
            for (Reference reference : list) {
                dao.delete(reference);
            }
        }
    }
}
