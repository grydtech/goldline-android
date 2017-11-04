package com.gryd.goldline.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gryd.goldline.R;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class AddItemFragment extends DialogFragment {

    public static AddItemFragment newInstance(ItemType itemType) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putInt("itemType", itemType.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Build the layout
        builder.setMessage(R.string.string_input)
                .setView(createSubView(inflater, null))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Snackbar.make(getActivity().findViewById(R.id.main_content), "Item Added Successfully", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Snackbar.make(getActivity().findViewById(R.id.main_content), "Canceled", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                });
        return builder.create();
    }

    private View createSubView(@NonNull LayoutInflater inflater, ViewGroup container) {
        ItemType itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));
        View view;
        switch (itemType) {
            case Tyre:
                view = inflater.inflate(R.layout.fragment_tyre_add, container, false);
                break;
            case Battery:
                view = inflater.inflate(R.layout.fragment_battery_add, container, false);
                break;
            case Tube:
                view = inflater.inflate(R.layout.fragment_tube_add, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }
        return view;
    }
}
