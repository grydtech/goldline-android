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

import com.gryd.goldline.R;
import com.gryd.goldline.models.Item;


public class ItemDetailsFragment extends DialogFragment {

    public static ItemFragment newInstance(Item item) {
        ItemFragment fragment = new ItemFragment();
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
        builder.setTitle(R.string.string_add);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                // Variables
//                EditText brand = subView.findViewById(R.id.text_brand);
//                EditText dimension = subView.findViewById(R.id.text_dimension);
//                EditText country = subView.findViewById(R.id.text_country);
//                EditText make = subView.findViewById(R.id.text_make);
//                EditText capacity = subView.findViewById(R.id.text_capacity);
//                EditText warranty = subView.findViewById(R.id.text_warranty);
//
//                // Generate Item Object
//                Item item = (Item) getArguments().getSerializable("item");
//                ItemType itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));
//                Item item = null;
//                switch (itemType) {
//                    case tyre:
//                        Tyre tyre = new Tyre();
//                        tyre.setBrand(brand.getText().toString());
//                        tyre.setCountry(country.getText().toString());
//                        tyre.setMake(Integer.parseInt(make.getText().toString()));
//                        tyre.setSize(dimension.getText().toString());
//                        tyre.setStocks(0);
//                        item = tyre;
//                        break;
//                    case battery:
//                        Battery battery = new Battery();
//                        battery.setBrand(brand.getText().toString());
//                        battery.setCapacity(Integer.parseInt(capacity.getText().toString()));
//                        battery.setWarranty(Integer.parseInt(warranty.getText().toString()));
//                        battery.setStocks(0);
//                        item = battery;
//                        break;
//                    case tube:
//                        Tube tube = new Tube();
//                        tube.setBrand(brand.getText().toString());
//                        tube.setSize(dimension.getText().toString());
//                        tube.setStocks(0);
//                        item = tube;
//                        break;
//                }
//                Database.addItem(item);
//                Snackbar.make(view, "Item Added Successfully", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Snackbar.make(view, "Closed", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        return builder.create();
    }
}
