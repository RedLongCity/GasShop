package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.Reference;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface ReferenceDao {

    List<Reference> findAll();

    Reference findById(Integer id);

    void save(Reference reference);

    void delete(Reference reference);

    void update(Reference reference);

}
