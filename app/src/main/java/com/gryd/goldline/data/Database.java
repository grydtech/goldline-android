package com.gryd.goldline.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gryd.goldline.models.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Database {
    // Write a message to the database
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference itemsDatabase = database.getReference("item");

    public static DatabaseReference getItemsDatabase() {
        return itemsDatabase;
    }

    public static void addItem(Item item) {
        if (item == null) {
            return;
        }
        String itemType = item.getClass().getSimpleName().toLowerCase();
        String key = String.valueOf(item.hashCode());
        itemsDatabase.child(itemType).child(key).setValue(item);
    }

    public static void updateItem(Item item) {
        String itemType = item.getClass().getSimpleName().toLowerCase();
        String key = String.valueOf(item.hashCode());
        Map<String, Object> itemValues = item.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.format("/%s/%s", itemType, key), itemValues);
        itemsDatabase.updateChildren(childUpdates);
    }

    public static void removeItem(Item item) {
        String itemType = item.getClass().getSimpleName().toLowerCase();
        String key = String.valueOf(item.hashCode());
        itemsDatabase.child(itemType).child(key).removeValue();
    }
}
