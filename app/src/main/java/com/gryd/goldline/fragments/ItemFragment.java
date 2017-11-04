package com.gryd.goldline.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.gryd.goldline.ItemAdapter;
import com.gryd.goldline.R;
import com.gryd.goldline.models.Battery;
import com.gryd.goldline.models.Item;
import com.gryd.goldline.models.ItemType;
import com.gryd.goldline.models.Tube;
import com.gryd.goldline.models.Tyre;
import com.gryd.goldline.models.data.FirebaseDb;

import java.util.ArrayList;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(ItemType itemType) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt("itemType", itemType.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ItemType itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));
        View view;
        switch (itemType) {
            case tyre:
                view = inflater.inflate(R.layout.fragment_tyre, container, false);
                break;
            case battery:
                view = inflater.inflate(R.layout.fragment_battery, container, false);
                break;
            case tube:
                view = inflater.inflate(R.layout.fragment_tube, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }

        // Declare items and adapter
        final ArrayList<Item> items = new ArrayList<>();
        final ItemAdapter itemAdapter = new ItemAdapter(getActivity(), items);
        final String TAG = "FIREBASE";

        // Set adapter to ListView
        ListView listView = view.findViewById(R.id.list_items);
        listView.setAdapter(itemAdapter);

        // Read from the database
        FirebaseDb.getItemsDatabase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot : ds.getChildren()) {
                        Item item = null;
                        ItemType itemType =
                                ItemType.valueOf(dataSnapshot.child("type").getValue().toString());
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
                        }
                        items.add(item);
                    }
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Add SearchView Listeners
        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.requestFocus();
        return view;
    }
}