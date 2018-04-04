package com.grydtech.goldline.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grydtech.goldline.models.Item;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Database {
    // Write a message to the database
    private static final FirebaseDatabase database;
    private static final DatabaseReference itemsDatabase;

    static {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        itemsDatabase = database.getReference("item");

    }

    static DatabaseReference getItemsDatabase() {
        return itemsDatabase;
    }

    public static Task<Void> addItem(Item item) {
        if (item == null) {
            return null;
        }
        String itemType = item.getClass().getSimpleName().toLowerCase(Locale.getDefault());
        String key = String.valueOf(item.hashCode());
        return itemsDatabase.child(itemType).child(key).setValue(item);
    }

    public static Task<Void> updateItem(Item item) {
        String itemType = item.getClass().getSimpleName().toLowerCase(Locale.getDefault());
        String key = String.valueOf(item.hashCode());
        Map<String, Object> itemValues = item.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.format("/%s/%s", itemType, key), itemValues);
        return itemsDatabase.updateChildren(childUpdates);
    }

    public static Task<Void> removeItem(Item item) {
        String itemType = item.getClass().getSimpleName().toLowerCase(Locale.getDefault());
        String key = String.valueOf(item.hashCode());
        return itemsDatabase.child(itemType).child(key).removeValue();
    }
}
