package com.gryd.goldline.fragments;

import android.app.Activity;
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
import android.widget.EditText;

import com.gryd.goldline.R;
import com.gryd.goldline.data.Database;
import com.gryd.goldline.models.Battery;
import com.gryd.goldline.models.Item;
import com.gryd.goldline.models.ItemType;
import com.gryd.goldline.models.Tube;
import com.gryd.goldline.models.Tyre;

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
        Activity activity = getActivity();
        Bundle args = getArguments();
        if (activity == null || args == null)
            throw new IllegalArgumentException("Activity is null");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        final View view = activity.findViewById(R.id.main_content);
        final View subView = createSubView(inflater, (ViewGroup) view.getParent(), args);

        // Build the layout
        builder.setTitle(R.string.text_add_item);
        builder.setView(subView);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Variables
                EditText brand = subView.findViewById(R.id.text_brand);
                EditText dimension = subView.findViewById(R.id.text_dimension);
                EditText country = subView.findViewById(R.id.text_country);
                EditText make = subView.findViewById(R.id.text_make);
                EditText capacity = subView.findViewById(R.id.text_capacity);
                EditText warranty = subView.findViewById(R.id.text_warranty);

                // Generate Item Object
                ItemType itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));
                Item item = null;
                switch (itemType) {
                    case tyre:
                        Tyre tyre = new Tyre();
                        tyre.setBrand(brand.getText().toString());
                        tyre.setCountry(country.getText().toString());
                        tyre.setMake(Integer.parseInt(make.getText().toString()));
                        tyre.setSize(dimension.getText().toString());
                        tyre.setStocks(0);
                        item = tyre;
                        break;
                    case battery:
                        Battery battery = new Battery();
                        battery.setBrand(brand.getText().toString());
                        battery.setCapacity(Integer.parseInt(capacity.getText().toString()));
                        battery.setWarranty(Integer.parseInt(warranty.getText().toString()));
                        battery.setStocks(0);
                        item = battery;
                        break;
                    case tube:
                        Tube tube = new Tube();
                        tube.setBrand(brand.getText().toString());
                        tube.setSize(dimension.getText().toString());
                        tube.setStocks(0);
                        item = tube;
                        break;
                }
                Database.addItem(item);
                Snackbar.make(view, "Item Added Successfully", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }

    private View createSubView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        ItemType itemType = ItemType.valueOf(args.getInt("itemType", 0));
        View view;
        switch (itemType) {
            case tyre:
                view = inflater.inflate(R.layout.fragment_tyre_add, container, false);
                break;
            case battery:
                view = inflater.inflate(R.layout.fragment_battery_add, container, false);
                break;
            case tube:
                view = inflater.inflate(R.layout.fragment_tube_add, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }
        return view;
    }
}
