package com.grydtech.goldline.models;

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
    private Integer make;

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

    public Integer getMake() {
        return make;
    }

    public void setMake(Integer make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.getDefault(),
                "%s %s (%s) %s",
                getSize(),
                getBrand(),
                getCountry(),
                getMake() != null ? getMake() : ""
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tyre tyre = (Tyre) o;


        return String.valueOf(this.make).equals(String.valueOf(tyre.make))
                && this.size.equals(tyre.size)
                && this.country.equals(tyre.country);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

    @Override
    public boolean isValid() {
        return !getBrand().isEmpty() && !getSize().isEmpty() && !getCountry().isEmpty();
    }
}
