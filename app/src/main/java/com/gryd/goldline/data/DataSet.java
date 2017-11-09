package com.gryd.goldline.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.gryd.goldline.models.Battery;
import com.gryd.goldline.models.Item;
import com.gryd.goldline.models.ItemType;
import com.gryd.goldline.models.Tube;
import com.gryd.goldline.models.Tyre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/8/17
 */

public final class DataSet {

    // Declare items and adapters
    private static final DataSet dataSet = new DataSet();
    private final ArrayList<Tyre> tyres = new ArrayList<>();
    private final ArrayList<Battery> batteries = new ArrayList<>();
    private final ArrayList<Tube> tubes = new ArrayList<>();
    private final ArrayList<FilterableItemAdapter> adapters = new ArrayList<>();

    private DataSet() {
        // Add listener to update data arrays on database changes
        Database.getItemsDatabase().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Populate data arrays with new values
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ItemType itemType = ItemType.valueOf(ds.getKey());
                    // Clear array of items
                    clearList(itemType);
                    for (DataSnapshot dataSnapshot : ds.getChildren()) {
                        Item item;
                        switch (itemType) {
                            case tyre:
                                item = dataSnapshot.getValue(Tyre.class);
                                break;
                            case battery:
                                item = dataSnapshot.getValue(Battery.class);
                                break;
                            case tube:
                                item = dataSnapshot.getValue(Tube.class);
                                break;
                            default:
                                throw new IllegalArgumentException("Not a valid Item type");
                        }
                        addItem(item);
                    }
                }
                // Notify adapters
                for (FilterableItemAdapter adapter : adapters) {
                    adapter.notifyItemSourceUpdated();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(this.getClass().getSimpleName(), "Failed to read value.", error.toException());
            }
        });
    }

    public static DataSet getInstance() {
        return dataSet;
    }

    public FilterableItemAdapter<? extends Item> getAdapter(Context context, ItemType itemType) {
        FilterableItemAdapter<? extends Item> adapter;

        switch (itemType) {
            case tyre:
                adapter = new FilterableItemAdapter<>(context, tyres);
                break;
            case battery:
                adapter = new FilterableItemAdapter<>(context, batteries);
                break;
            case tube:
                adapter = new FilterableItemAdapter<>(context, tubes);
                break;
            default:
                throw new IllegalArgumentException("Not a valid Item Type");
        }
        this.adapters.add(adapter);
        return adapter;
    }

    public void removeAdapter(FilterableItemAdapter adapter) {
        this.adapters.remove(adapter);
    }

    private void clearList(ItemType itemType) {
        switch (itemType) {
            case tyre:
                tyres.clear();
                break;
            case battery:
                batteries.clear();
                break;
            case tube:
                tubes.clear();
                break;
            default:
                throw new IllegalArgumentException("Not a valid enum value for ItemType");
        }
    }

    private List<? extends Item> getList(ItemType itemType) {
        switch (itemType) {
            case tyre:
                return tyres;
            case battery:
                return batteries;
            case tube:
                return tubes;
            default:
                throw new IllegalArgumentException("Not a valid enum value for ItemType");
        }
    }

    private void addItem(Item item) {
        ItemType itemType = ItemType.valueOf(item.getClass().getSimpleName().toLowerCase());
        switch (itemType) {
            case tyre:
                tyres.add((Tyre) item);
                break;
            case battery:
                batteries.add((Battery) item);
                break;
            case tube:
                tubes.add((Tube) item);
                break;
        }
    }
}
