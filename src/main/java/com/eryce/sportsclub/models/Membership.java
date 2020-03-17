package com.eryce.sportsclub.models;

import javax.persistence.*;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "month_id", nullable = false)
    private Period period;

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Integer getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
