package com.gryd.goldline.models;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public abstract class Item {
    private String Brand;
    private int Stocks;

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
}
