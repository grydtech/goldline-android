package com.gryd.goldline.fragments;

import android.util.SparseArray;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public enum ItemType {

    Tyre(0), Battery(1), Tube(2);

    private static SparseArray<ItemType> map = new SparseArray<>();

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
