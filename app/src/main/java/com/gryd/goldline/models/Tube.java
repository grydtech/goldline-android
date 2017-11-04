package com.gryd.goldline.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Tube extends Item {
    private String Size;

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    @Override
    public ItemType getType() {
        return ItemType.tube;
    }

    @Override
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("brand", getBrand());
        result.put("size", getSize());
        result.put("stocks", getStocks());

        return result;
    }
}
