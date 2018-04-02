package com.grydtech.goldline.models;

import android.util.SparseArray;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public enum ItemType {

    tyre(0), battery(1), tube(2);

    private static final SparseArray<ItemType> map = new SparseArray<>();

    static {
        for (ItemType itemType : ItemType.values()) {
            map.put(itemType.value, itemType);
        }
    }

    private final int value;

    ItemType(int value) {
        this.value = value;
    }

    public static ItemType valueOf(int itemType) {
        return map.get(itemType);
    }

    public int getValue() {
        return value;
    }
}
