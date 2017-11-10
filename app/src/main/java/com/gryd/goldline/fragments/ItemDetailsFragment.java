package com.gryd.goldline.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gryd.goldline.R;
import com.gryd.goldline.models.Item;
import com.gryd.goldline.models.ItemType;


public class ItemDetailsFragment extends DialogFragment {

    public static ItemDetailsFragment newInstance(Item item) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
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
        // Get the view
        final View view = getActivity().findViewById(R.id.main_content);
        // Build the layout
        // Title
        builder.setTitle(R.string.string_details);
        // Item details
        builder.setView(createItemDetailsView(inflater, null));
        // Close button
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Do Nothing and close
            }
        });
        return builder.create();
    }

    private View createItemDetailsView(@NonNull LayoutInflater inflater, ViewGroup container) {
        ItemType itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));
        View view;
        // TODO Change to more specific item views later on
        switch (itemType) {
            case tyre:
                view = inflater.inflate(R.layout.fragment_item_details, container, false);
                break;
            case battery:
                view = inflater.inflate(R.layout.fragment_item_details, container, false);
                break;
            case tube:
                view = inflater.inflate(R.layout.fragment_item_details, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }
        return view;
    }
}
