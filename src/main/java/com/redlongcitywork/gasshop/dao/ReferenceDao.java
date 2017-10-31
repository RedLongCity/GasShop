package com.redlongcitywork.gasshop.dao;

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

    @Override
    Reference save(Reference reference);

    @Override
    void delete(Reference reference);

    @Override
    void update(Reference reference);

}
