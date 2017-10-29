package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.ReferenceDao;
import com.redlongcitywork.gasshop.models.Reference;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity
 * 29/10/2017
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
    public void deleteAll() {
        List<Reference> list = dao.findAll();
        if (list != null) {
            for (Reference reference : list) {
                dao.delete(reference);
            }
        }
    }
}
