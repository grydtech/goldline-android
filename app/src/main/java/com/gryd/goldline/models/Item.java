package com.gryd.goldline.models;

import com.google.firebase.database.Exclude;

import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public abstract class Item {
    private String Brand;
    private int Stocks;

    public abstract ItemType getType();

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getStocks() {
        return Stocks;
    }

    public void setStocks(int stocks) {
        Stocks = stocks;
    }

    @Override
    public String toString() {
        return Brand;
    }

    @Exclude
    public abstract Map<String, Object> toMap();
}
