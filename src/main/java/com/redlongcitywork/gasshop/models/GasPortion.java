package com.redlongcitywork.gasshop.models;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author redlongcity
 * 28/10/2017
 * model keeps item of order
 */
public class GasPortion {
    
    private Integer id;
    
    private Integer amount;
    
    private GasStation station;
    
    private Fuel fuel;
    
    private Set<Order> orders = new HashSet<Order>();

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public GasStation getStation() {
        return station;
    }

    public void setStation(GasStation station) {
        this.station = station;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "GasPortion{" + "id=" + id + ", amount=" + amount + ", station=" + station + ", fuel=" + fuel + ", orders=" + orders + '}';
    }
    
}
