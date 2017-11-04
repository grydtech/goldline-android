package com.gryd.goldline.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Battery extends Item {
    private int Capacity;
    private int Warranty;


    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getWarranty() {
        return Warranty;
    }

    public void setWarranty(int warranty) {
        Warranty = warranty;
    }

    @Override
    public ItemType getType() {
        return ItemType.battery;
    }

    @Override
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("brand", getBrand());
        result.put("capacity", getCapacity());
        result.put("warranty", getWarranty());
        result.put("stocks", getStocks());

        return result;
    }
}
