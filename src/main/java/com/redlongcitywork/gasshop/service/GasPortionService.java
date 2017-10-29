package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.GasPortion;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface GasPortionService {

    List<GasPortion> findAll();

    GasPortion findById(Integer id);

    void saveGasPortion(GasPortion portion);

    void deleteGasPortion(GasPortion portion);

    void updateGasPortion(GasPortion portion);

    void deleteAll();

}
