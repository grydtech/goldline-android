package com.gryd.goldline.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gryd.goldline.R;

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
            case Tyre:
                view = inflater.inflate(R.layout.fragment_tyre, container, false);
                break;
            case Battery:
                view = inflater.inflate(R.layout.fragment_battery, container, false);
                break;
            case Tube:
                view = inflater.inflate(R.layout.fragment_tube, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }
        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.requestFocus();
        return view;
    }
}
