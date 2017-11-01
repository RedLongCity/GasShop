package com.redlongcitywork.gasshop.jdbc.utils;

import java.sql.Connection;

/**
 *
 * @author redlongcity
 * 28/10/2017
 */
public interface Transaction {
    
    public Connection getConnection();
    
    public void begin();
    
    public void commit();
    
    public void rollback();
    
}
