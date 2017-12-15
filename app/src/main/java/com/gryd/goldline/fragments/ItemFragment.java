package com.gryd.goldline.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gryd.goldline.R;
import com.gryd.goldline.data.DataSet;
import com.gryd.goldline.data.FilterableItemAdapter;
import com.gryd.goldline.models.ItemType;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class ItemFragment extends Fragment {

    private FilterableItemAdapter filterableItemAdapter;
    private DataSet dataSet;

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
        // Item Type
        ItemType itemType = ItemType.valueOf(0);
        if (getArguments() != null) {
            itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));
        }
        // Inflate the view
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

        // Assign DataSet and FilterableItemAdapter
        dataSet = DataSet.getInstance();
        filterableItemAdapter = dataSet.getAdapter(getContext(), itemType);

        // Assign adapter to RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.view_items);
        recyclerView.setAdapter(filterableItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add listeners to SearchView
        final SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterableItemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        dataSet.removeAdapter(filterableItemAdapter);
        super.onDestroyView();
    }
}