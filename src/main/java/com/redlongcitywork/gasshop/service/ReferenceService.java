package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.Reference;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface ReferenceService {

    List<Reference> findAll();

    List<Reference> findByFuel(Fuel fuel);

    Reference findById(Integer id);

    void saveReference(Reference reference);

    void deleteReference(Reference reference);

    void updateReference(Reference reference);

    Float getCost(Fuel fuel, GasStation station);

    Float getAverageCost(Fuel fuel);

    void deleteAll();

}
