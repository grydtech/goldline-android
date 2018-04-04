package com.grydtech.goldline.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.grydtech.goldline.R;
import com.grydtech.goldline.data.DataSet;
import com.grydtech.goldline.data.Database;
import com.grydtech.goldline.models.Battery;
import com.grydtech.goldline.models.Item;
import com.grydtech.goldline.models.ItemType;
import com.grydtech.goldline.models.Tube;
import com.grydtech.goldline.models.Tyre;

import java.util.List;

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
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Activity activity = getActivity();
        final Context context = getContext();
        Bundle args = getArguments();
        if (activity == null || args == null || context == null)
            throw new IllegalArgumentException("Activity/Context is null");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        final View view = activity.findViewById(R.id.main_content);
        final View subView = createSubView(activity, inflater, (ViewGroup) view.getParent(), args);

        // Build the layout
        builder.setTitle(R.string.text_add_item);
        builder.setView(subView);
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {

            // Item Type
            ItemType itemType = ItemType.valueOf(getArguments().getInt("itemType", 0));

            // Variables
            AutoCompleteTextView brand = subView.findViewById(R.id.txt_brand);
            AutoCompleteTextView size = subView.findViewById(R.id.txt_size);
            AutoCompleteTextView make = subView.findViewById(R.id.txt_make);
            AutoCompleteTextView capacity = subView.findViewById(R.id.txt_capacity);
            AutoCompleteTextView warranty = subView.findViewById(R.id.txt_warranty);
            Spinner country = subView.findViewById(R.id.txt_country);

            // Generate Item Object
            Item item = null;
            switch (itemType) {
                case tyre:
                    Tyre tyre = new Tyre();
                    tyre.setBrand(brand.getText().toString());
                    tyre.setCountry(country.getSelectedItem().toString());
                    // Tyre Make
                    String tyreMake = make.getText().toString();
                    if (!tyreMake.isEmpty()) {
                        tyre.setMake(Integer.valueOf(tyreMake));
                    }
                    tyre.setSize(size.getText().toString());
                    tyre.setStocks(0);
                    item = tyre;
                    break;
                case battery:
                    Battery battery = new Battery();
                    battery.setBrand(brand.getText().toString());
                    // Battery Capacity
                    String batteryCapacity = capacity.getText().toString();
                    if (!batteryCapacity.isEmpty()) {
                        battery.setCapacity(Integer.valueOf(batteryCapacity));
                    }
                    // Battery Warranty
                    String batteryWarranty = warranty.getText().toString();
                    if (!batteryWarranty.isEmpty()) {
                        battery.setWarranty(Integer.valueOf(batteryWarranty));
                    }
                    battery.setStocks(0);
                    item = battery;
                    break;
                case tube:
                    Tube tube = new Tube();
                    tube.setBrand(brand.getText().toString());
                    tube.setSize(size.getText().toString());
                    tube.setStocks(0);
                    item = tube;
                    break;
            }

            Database.addItem(item)
                    .addOnSuccessListener(task ->
                            Snackbar.make(view, "Item Added Successfully", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show()
                    ).addOnFailureListener(task ->
                    Snackbar.make(view, "Item Could not be Added", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()
            );

        });
        builder.setNegativeButton(android.R.string.cancel, null);
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private View createSubView(@NonNull Context context, @NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        ItemType itemType = ItemType.valueOf(args.getInt("itemType", 0));
        View view;

        switch (itemType) {
            case tyre:
                view = inflater.inflate(R.layout.fragment_tyre_add, container, false);
                // Set spinner data
                Spinner country = view.findViewById(R.id.txt_country);
                List<String> countries = DataSet.getCountries();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, countries);
                country.setAdapter(adapter);
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

        // Set auto completion for brands
        AutoCompleteTextView brandTextEdit = view.findViewById(R.id.txt_brand);
        if (brandTextEdit != null) {
            brandTextEdit.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            brandTextEdit.setThreshold(1);
            List<String> brands = DataSet.getBrands(itemType);
            if (brands != null) {
                brandTextEdit.setAdapter(new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_dropdown_item, brands
                ));
            }
        }

        // Set auto completion for sizes
        AutoCompleteTextView sizeTextEdit = view.findViewById(R.id.txt_size);
        if (sizeTextEdit != null) {
            sizeTextEdit.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            sizeTextEdit.setThreshold(1);
            List<String> sizes = DataSet.getSizes(itemType);
            if (sizes != null) {
                sizeTextEdit.setAdapter(new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_dropdown_item, sizes
                ));
            }
        }

        return view;
    }
}
