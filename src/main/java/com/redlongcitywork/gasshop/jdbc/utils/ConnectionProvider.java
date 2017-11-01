package com.redlongcitywork.gasshop.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author redlongity 
 * 28/10/2017
 */
@Configuration
@ComponentScan("com.redlongcitywork.gasshop")
@PropertySource("classpath:application.properties")
public class ConnectionProvider {

    private static final Logger LOG = Logger.getLogger(ConnectionProvider.class.getName());
    
    private static ConnectionProvider instance = null;

    @Resource
    private Environment env;

    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";

    private static Connection connection = null;
    
    public static ConnectionProvider getInstance(){
        if(instance == null){
            instance = new ConnectionProvider();
        }
        return instance;
    }

    private ConnectionProvider() {
        try {
            Class.forName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
            connection = DriverManager.getConnection(
                    env.getRequiredProperty(PROP_DATABASE_URL),
                    env.getRequiredProperty(PROP_DATABASE_USERNAME),
                    env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        } catch (ClassNotFoundException e) {
            LOG.log(Level.WARNING, e.getException().toString());
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            this.closeConnection();

            connection = DriverManager.getConnection(
                    env.getRequiredProperty(PROP_DATABASE_URL),
                    env.getRequiredProperty(PROP_DATABASE_USERNAME),
                    env.getRequiredProperty(PROP_DATABASE_PASSWORD));
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
    }

    public void rollback() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
    }
}
