package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.GasPortion;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface GasPortionDao extends AbstractDao<GasPortion> {

    @Override
    List<GasPortion> findAll();

    @Override
    GasPortion findById(Integer id);

    @Override
    GasPortion save(GasPortion portion);

    @Override
    void delete(GasPortion portion);

    @Override
    void update(GasPortion portion);

}
