package com.redlongcitywork.gasshop.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author redlongcity
 * 28/10/2017
 * model keeps info about order
 */
public class Order {
    
    private Integer id;
    
    private Date date;
    
    private String status;
    
    private Set<GasPortion> gasPortionSet = new HashSet<GasPortion>(); 
    
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<GasPortion> getGasPortionSet() {
        return gasPortionSet;
    }

    public void setGasPortionSet(Set<GasPortion> gasPortionSet) {
        this.gasPortionSet = gasPortionSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", date=" + date + ", status=" + status + ", gasPortionSet=" + gasPortionSet + ", user=" + user + '}';
    }
    
}
