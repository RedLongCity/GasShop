package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.GasStation;
import java.util.List;

/**
 *
 * @author redlongcity 28/10/2017
 */
public interface GasStationDao extends AbstractDao<GasStation> {

    @Override
    List<GasStation> findAll();

    @Override
    GasStation findById(Integer id);

    @Override
    GasStation save(GasStation station);

    @Override
    void delete(GasStation station);

    @Override
    void update(GasStation station);

}
