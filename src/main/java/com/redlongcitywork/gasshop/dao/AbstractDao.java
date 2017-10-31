package com.redlongcitywork.gasshop.dao;

import java.util.List;

/**
 *
 * @author redlongcity
 */
public interface AbstractDao<T> {

    List<T> findAll();

    T findById(Integer id);

    T save(T obj);

    void delete(T obj);

    void update(T obj);

}
