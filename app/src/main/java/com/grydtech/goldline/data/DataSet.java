package com.grydtech.goldline.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.grydtech.goldline.models.Battery;
import com.grydtech.goldline.models.Item;
import com.grydtech.goldline.models.ItemType;
import com.grydtech.goldline.models.Tube;
import com.grydtech.goldline.models.Tyre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/8/17
 */

public final class DataSet {

    // Default Countries
    private static final String[] defaultCountries = {
            "CHINA",
            "EUROPE",
            "GERMANY",
            "INDIA",
            "INDONESIA",
            "JAPAN",
            "KOREA",
            "MALAYSIA",
            "SRI LANKA",
            "TAIWAN",
            "THAILAND",
            "UK",
            "VIETNAM"
    };

    // Default Brands
    private static final String[] defaultTyreBrands = {
            "HANKOOK",
            "KUMHO",
            "SONAR",
            "EVENT",
            "GT",
            "DUNLOP",
            "BRIDGESTONE",
            "CEAT",
            "NANKANG",
            "GOODYEAR",
            "MICHELIN",
            "TOYO",
            "HIFLY",
            "CONTINENTAL",
            "ACHILLES"
    };
    private static final String[] defaultBatteryBrands = {
            "EXIDE"
    };
    private static final String[] defaultTubeBrands = {
            "CST"
    };

    // Default Sizes
    private static final String[] defaultTyreSizes = {
            "135/70/12",
            "145/70/12",
            "145/80/12",
            "155/65/12",
            "155/80/12",
            "155/65/13",
            "155/80/13",
            "165/80/13",
            "185/70/13",
            "165/70/14",
            "175/70/13",
            "175/70/14",
            "185/70/14",
            "185/65/14",
            "215/75/15",
            "235/75/15",
            "235/75/16",
            "195/65/15",
            "165/55/14",
            "185/65/15",
            "165/60/14",
            "175/65/15"
    };
    private static final String[] defaultTubeSizes = {
            "26x1 1/2",
            "26x1 3/8",
            "28x1 1/2"
    };
    private static final DataSet dataSet;
    private static final List<String> countries;
    // Brands
    private static final List<String> tyreBrands;
    private static final List<String> batteryBrands;
    private static final List<String> tubeBrands;
    // Sizes
    private static final List<String> tyreSizes;
    private static final List<String> tubeSizes;

    static {
        dataSet = new DataSet();
        countries = Arrays.asList(defaultCountries);
        Collections.sort(countries);
        // Brands
        batteryBrands = Arrays.asList(defaultBatteryBrands);
        Collections.sort(batteryBrands);
        tubeBrands = Arrays.asList(defaultTubeBrands);
        Collections.sort(tubeBrands);
        tyreBrands = Arrays.asList(defaultTyreBrands);
        Collections.sort(tyreBrands);
        // Sizes
        tubeSizes = Arrays.asList(defaultTubeSizes);
        Collections.sort(tubeSizes);
        tyreSizes = Arrays.asList(defaultTyreSizes);
        Collections.sort(tyreSizes);
    }

    private final ArrayList<Item> tyres = new ArrayList<>();
    private final ArrayList<Item> batteries = new ArrayList<>();
    private final ArrayList<Item> tubes = new ArrayList<>();
    private final ArrayList<FilterableItemAdapter> adapters = new ArrayList<>();

    private DataSet() {
        // Add listener to update data arrays on database changes
        Database.getItemsDatabase().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // Clear all data arrays first
                clearList(ItemType.tyre);
                clearList(ItemType.battery);
                clearList(ItemType.tube);

                // Populate data arrays with new data
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Each direct child specifies an item type
                    ItemType itemType = ItemType.valueOf(ds.getKey());
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
                        if (item != null) addItem(item);
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

    public static List<String> getCountries() {
        return countries;
    }

    public static List<String> getBrands(ItemType itemType) {
        switch (itemType) {
            case tyre:
                return tyreBrands;
            case battery:
                return batteryBrands;
            case tube:
                return tubeBrands;
            default:
                return null;
        }
    }

    public static List<String> getSizes(ItemType itemType) {
        switch (itemType) {

            case tyre:
                return tyreSizes;
            case tube:
                return tubeSizes;
            default:
                return null;
        }
    }

    public static DataSet getInstance() {
        return dataSet;
    }

    public FilterableItemAdapter getAdapter(Context context, ItemType itemType) {
        FilterableItemAdapter adapter;

        switch (itemType) {
            case tyre:
                adapter = new FilterableItemAdapter(context, tyres);
                break;
            case battery:
                adapter = new FilterableItemAdapter(context, batteries);
                break;
            case tube:
                adapter = new FilterableItemAdapter(context, tubes);
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

    private void addItem(Item item) {
        ItemType itemType = ItemType.valueOf(item.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
        switch (itemType) {
            case tyre:
                tyres.add(item);
                break;
            case battery:
                batteries.add(item);
                break;
            case tube:
                tubes.add(item);
                break;
        }
    }
}
