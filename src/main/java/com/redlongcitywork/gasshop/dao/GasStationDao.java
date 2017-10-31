package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.GasStation;
import java.util.List;

/**
 *
 * @author redlongcity 
 * 28/10/2017
 */
public interface GasStationDao {

    List<GasStation> findAll();

    GasStation findById(Integer id);

    GasStation save(GasStation station);

    void delete(GasStation station);

    void update(GasStation station);

}
