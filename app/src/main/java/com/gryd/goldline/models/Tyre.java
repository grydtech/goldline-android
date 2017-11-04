package com.gryd.goldline.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Tyre extends Item {
    private String Size;
    private String Country;
    private int Make;

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getMake() {
        return Make;
    }

    public void setMake(int make) {
        Make = make;
    }

    @Override
    public ItemType getType() {
        return ItemType.tyre;
    }

    @Override
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("brand", getBrand());
        result.put("size", getSize());
        result.put("country", getCountry());
        result.put("make", getMake());
        result.put("stocks", getStocks());

        return result;
    }
}
