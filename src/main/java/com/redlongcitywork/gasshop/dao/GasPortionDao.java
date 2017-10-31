package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.GasPortion;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface GasPortionDao {

    List<GasPortion> findAll();

    GasPortion findById(Integer id);

    GasPortion save(GasPortion portion);

    void delete(GasPortion portion);

    void update(GasPortion portion);

}
