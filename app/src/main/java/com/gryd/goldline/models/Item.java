package com.gryd.goldline.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public abstract class Item implements Serializable {
    private String brand;
    private int stocks;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public abstract String toString();

    @Exclude
    public abstract Map<String, Object> toMap();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return brand.equals(item.brand);
    }

    @Override
    public int hashCode() {
        return brand.hashCode();
    }
}
