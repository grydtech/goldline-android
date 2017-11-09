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

public class Battery extends Item {
    private int capacity;
    private int warranty;


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.getDefault(),
                "%s %d (%dY)",
                getBrand(),
                getCapacity(),
                getWarranty()
        );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Battery battery = (Battery) o;

        return capacity == battery.capacity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + capacity;
        return result;
    }
}
