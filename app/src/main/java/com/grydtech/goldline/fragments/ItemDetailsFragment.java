package com.grydtech.goldline.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.grydtech.goldline.R;
import com.grydtech.goldline.data.Database;
import com.grydtech.goldline.models.Battery;
import com.grydtech.goldline.models.Item;
import com.grydtech.goldline.models.ItemType;
import com.grydtech.goldline.models.Tube;
import com.grydtech.goldline.models.Tyre;

import java.util.Locale;


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

        // Validate activity and args
        if (activity == null || args == null)
            throw new IllegalArgumentException("Activity is null");

        // Create builder for item details dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        // Get item, and validate it
        final Item item = (Item) args.getSerializable("item");
        if (item == null)
            throw new IllegalArgumentException("Item is null");

        // Get reference to main content
        final View view = activity.findViewById(R.id.main_content);

        // Build the layout for item details dialog
        builder.setTitle(R.string.text_item_details);
        View subView = createItemDetailsView(inflater, (ViewGroup) view.getParent(), item);
        builder.setView(subView);

        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(R.string.text_delete, (dialog, which) -> {

            // Build the layout for item delete confirmation dialog
            final AlertDialog.Builder confirmationDialogBuilder = new AlertDialog.Builder(activity);
            confirmationDialogBuilder.setTitle(R.string.text_confirmation);
            confirmationDialogBuilder.setMessage(R.string.text_item_delete_confirmation);
            confirmationDialogBuilder.setPositiveButton(android.R.string.yes, (dialog1, which1) -> {
                Database.removeItem(item).addOnCompleteListener(task ->
                        Toast.makeText(view.getContext(), R.string.text_item_delete_success, Toast.LENGTH_SHORT).show()
                ).addOnFailureListener(task ->
                        Toast.makeText(view.getContext(), R.string.text_item_delete_failure, Toast.LENGTH_SHORT).show()
                );
                // Close the parent dialog along with confirmation dialog
                dialog.dismiss();
            });
            confirmationDialogBuilder.setNegativeButton(android.R.string.no, null);
            confirmationDialogBuilder.create().show();
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private View createItemDetailsView(@NonNull LayoutInflater inflater, ViewGroup container, Item item) {
        ItemType itemType = ItemType.valueOf(item.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
        TextView brand, stocks, size, make, country, capacity, warranty;
        Button btn_plus, btn_minus;
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
        btn_plus = view.findViewById(R.id.btn_plus);
        btn_minus = view.findViewById(R.id.btn_minus);

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

        // Set plus click listener
        btn_plus.setOnClickListener(v -> {
            int qty = item.getStocks() + 1;
            item.setStocks(qty);
            stocks.setText(String.valueOf(item.getStocks()));
            Database.updateItem(item).addOnCompleteListener(task ->
                    Toast.makeText(view.getContext(), R.string.item_update_success, Toast.LENGTH_SHORT).show()
            );
        });

        // Set minus click listeners
        btn_minus.setOnClickListener(v -> {
            int qty = item.getStocks() - 1;
            if (qty >= 0) {
                item.setStocks(qty);
                stocks.setText(String.valueOf(item.getStocks()));
                Database.updateItem(item).addOnCompleteListener(task ->
                        Toast.makeText(view.getContext(), R.string.item_update_success, Toast.LENGTH_SHORT).show()
                );
            } else {
                Toast.makeText(view.getContext(), R.string.alert_negative_stocks, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
