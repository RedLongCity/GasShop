package com.redlongcitywork.gasshop.models;

import java.sql.Date;

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

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", date=" + date + ", status=" + status + '}';
    }
    
    
    
}
