package com.eryce.sportsclub.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MembershipPrice {

    @Id
    private Integer id;

    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
