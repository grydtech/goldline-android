package com.gryd.goldline.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.gryd.goldline.R;
import com.gryd.goldline.data.Database;
import com.gryd.goldline.data.ItemAdapter;
import com.gryd.goldline.models.Battery;
import com.gryd.goldline.models.Item;
import com.gryd.goldline.models.ItemType;
import com.gryd.goldline.models.Tube;
import com.gryd.goldline.models.Tyre;

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

        // Declare items and adapters
        final ArrayList<Item> tyres = new ArrayList<>();
        final ArrayList<Item> batteries = new ArrayList<>();
        final ArrayList<Item> tubes = new ArrayList<>();
        final ItemAdapter tyreAdapter = new ItemAdapter(getContext(), tyres);
        final ItemAdapter batteryAdapter = new ItemAdapter(getContext(), batteries);
        final ItemAdapter tubeAdapter = new ItemAdapter(getContext(), tubes);

        // Inflate the view, declare items and set adapter
        View view;
        RecyclerView recyclerView;
        switch (itemType) {
            case tyre:
                view = inflater.inflate(R.layout.fragment_tyre, container, false);
                recyclerView = view.findViewById(R.id.view_items);
                recyclerView.setAdapter(tyreAdapter);
                break;
            case battery:
                view = inflater.inflate(R.layout.fragment_battery, container, false);
                recyclerView = view.findViewById(R.id.view_items);
                recyclerView.setAdapter(batteryAdapter);
                break;
            case tube:
                view = inflater.inflate(R.layout.fragment_tube, container, false);
                recyclerView = view.findViewById(R.id.view_items);
                recyclerView.setAdapter(tubeAdapter);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add listener to update data arrays on database changes
        Database.getItemsDatabase().addValueEventListener(new ValueEventListener() {
            final String TAG = "FIREBASE";

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Clear data arrays
                tyres.clear();
                batteries.clear();
                tubes.clear();
                // Populate data arrays with new values
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ItemType itemType = ItemType.valueOf(ds.getKey());
                    for (DataSnapshot dataSnapshot : ds.getChildren()) {
                        switch (itemType) {
                            case tyre:
                                Tyre tyre = dataSnapshot.getValue(Tyre.class);
                                tyres.add(tyre);
                                break;
                            case battery:
                                Battery battery = dataSnapshot.getValue(Battery.class);
                                batteries.add(battery);
                                break;
                            case tube:
                                Tube tube = dataSnapshot.getValue(Tube.class);
                                tubes.add(tube);
                                break;
                        }
                    }
                    switch (itemType) {
                        case tyre:
                            tyreAdapter.notifyDataSetChanged();
                            break;
                        case battery:
                            batteryAdapter.notifyDataSetChanged();
                            break;
                        case tube:
                            tubeAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Add SearchView Listeners
        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                itemAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                itemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }
}