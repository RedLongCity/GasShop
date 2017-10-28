package com.redlongcitywork.gasshop.models;

/**
 *
 * @author redlongcity
 * 28/10/2017
 * model keeps item of order
 */
public class GasPortion {
    
    private Integer id;
    
    private Integer amount;

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

    @Override
    public String toString() {
        return "GasPortion{" + "id=" + id + ", amount=" + amount + '}';
    }
    
    
}
