package com.gryd.goldline.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Tyre extends Item {
    private String size;
    private String country;
    private int make;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMake() {
        return make;
    }

    public void setMake(int make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.getDefault(),
                "%s %s (%s) %d",
                getBrand(),
                getSize(),
                getCountry(),
                getMake()
        );
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
