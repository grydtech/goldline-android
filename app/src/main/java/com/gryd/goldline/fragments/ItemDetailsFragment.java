package com.gryd.goldline.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gryd.goldline.R;
import com.gryd.goldline.data.Database;
import com.gryd.goldline.models.Battery;
import com.gryd.goldline.models.Item;
import com.gryd.goldline.models.ItemType;
import com.gryd.goldline.models.Tube;
import com.gryd.goldline.models.Tyre;


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
        Activity activity = getActivity();
        Bundle args = getArguments();
        if (activity == null || args == null)
            throw new IllegalArgumentException("Activity is null");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final Item item = (Item) args.getSerializable("item");
        final View view = activity.findViewById(R.id.main_content);
        final View subView = createItemDetailsView(inflater, (ViewGroup) view.getParent(), item);

        // Build the layout
        builder.setTitle(R.string.text_item_details);
        builder.setView(subView);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(R.string.text_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Database.removeItem(item);
                Toast.makeText(getContext(), R.string.alert_item_deleted, Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    private View createItemDetailsView(@NonNull LayoutInflater inflater, ViewGroup container, Item item) {
        ItemType itemType = ItemType.valueOf(item.getClass().getSimpleName().toLowerCase());
        TextView brand, stocks, size, make, country, capacity, warranty;
        View view;
        // Assign value to view object
        switch (itemType) {
            case tyre:
                view = inflater.inflate(R.layout.fragment_tyre_details, container, false);
                break;
            case battery:
                view = inflater.inflate(R.layout.fragment_battery_details, container, false);
                break;
            case tube:
                view = inflater.inflate(R.layout.fragment_tube_details, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }

        // Declare TextViews
        brand = view.findViewById(R.id.txt_brand);
        stocks = view.findViewById(R.id.txt_stocks);
        size = view.findViewById(R.id.txt_size);
        make = view.findViewById(R.id.txt_make);
        country = view.findViewById(R.id.txt_country);
        capacity = view.findViewById(R.id.txt_capacity);
        warranty = view.findViewById(R.id.txt_warranty);

        // Update TextViews
        brand.setText(item.getBrand());
        stocks.setText(String.valueOf(item.getStocks()));
        switch (itemType) {
            case tyre:
                size.setText(((Tyre) item).getSize());
                make.setText(String.valueOf(((Tyre) item).getMake()));
                country.setText(((Tyre) item).getCountry());
                break;
            case battery:
                capacity.setText(String.valueOf(((Battery) item).getCapacity()));
                warranty.setText(String.valueOf(((Battery) item).getWarranty()));
                break;
            case tube:
                size.setText(((Tube) item).getSize());
                break;
            default:
                throw new IllegalArgumentException("Invalid Item Type");
        }
        return view;
    }
}
