package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.GasStation;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface GasStationService {

    List<GasStation> findAll();

    GasStation findById(Integer id);

    void saveGasStation(GasStation station);

    void deleteGasStation(GasStation station);

    void updateGasStation(GasStation station);

    void deleteAll();
}
