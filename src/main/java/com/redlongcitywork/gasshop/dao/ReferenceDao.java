package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.Reference;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface ReferenceDao extends AbstractDao<Reference> {

    @Override
    List<Reference> findAll();

    @Override
    Reference findById(Integer id);
    
    List<Reference> findByFuel(Fuel fuel);

    @Override
    Reference save(Reference reference);

    @Override
    void delete(Reference reference);

    @Override
    void update(Reference reference);
    
    Float getCost(Fuel fuel, GasStation station);
    
    Float getAverageCost(Fuel fuel);

}
