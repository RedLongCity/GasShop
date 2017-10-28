package com.redlongcitywork.gasshop.jdbcutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author redlongcity 
 * 28/10/2017
 */
public class TransactionImpl implements Transaction {

    private static final Logger LOG = Logger.getLogger(TransactionImpl.class.getName());

    private static TransactionImpl instance = null;

    private Connection connection = null;

    private TransactionImpl() {

    }

    public static TransactionImpl getInstance() {
        if (instance == null) {
            instance = new TransactionImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionProvider.getInstance().getConnection();
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return this.connection;
    }

    @Override
    public void begin() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void rollback() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        } finally {
            this.close();
        }
    }

    private void close() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                ConnectionProvider.getInstance().closeConnection();
                this.connection = null;
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

}
