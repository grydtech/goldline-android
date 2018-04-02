package com.grydtech.goldline.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.grydtech.goldline.R;
import com.grydtech.goldline.fragments.ItemDetailsFragment;
import com.grydtech.goldline.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class FilterableItemAdapter extends RecyclerView.Adapter<ItemHolder> implements Filterable {

    private final Context context;
    private final Filter filter;
    private final List<Item> items;
    private final List<Item> itemSource;
    private String search_text;

    FilterableItemAdapter(Context context, List<Item> source_items) {
        this.context = context;
        this.itemSource = source_items;
        this.items = new ArrayList<>(source_items);
        filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    search_text = "";
                    results.values = itemSource;
                    results.count = itemSource.size();
                } else {
                    ArrayList<Item> fRecords = new ArrayList<>();
                    for (Item item : itemSource) {
                        String item_name = item.toString().toLowerCase().trim();
                        search_text = constraint.toString().toLowerCase().trim();
                        if (item_name.contains(search_text)) {
                            fRecords.add(item);
                        }
                    }
                    results.values = fRecords;
                    results.count = fRecords.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Now we have to inform the adapter about the new list filtered
                items.clear();
                List values = (List) results.values;
                for (Object value : values) {
                    if (value instanceof Item) {
                        Item item = (Item) value;
                        items.add(item);
                    }
                }
                notifyDataSetChanged();
            }
        };
    }

    void notifyItemSourceUpdated() {
        this.items.clear();
        this.items.addAll(this.itemSource);
        this.filter.filter(search_text, i -> notifyDataSetChanged());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        final ItemHolder holder = new ItemHolder(view);

        final FragmentManager fragmentManager = ((FragmentActivity) FilterableItemAdapter.this.context).getSupportFragmentManager();
        // Add item click listener
        view.setOnClickListener(v -> {
            ItemDetailsFragment fragment = ItemDetailsFragment.newInstance(
                    items.get(holder.getAdapterPosition())
            );
            fragment.show(fragmentManager, "Item Details");
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final Item item = items.get(position);
        Log.d(holder.name.toString(), holder.stocks.toString());
        holder.name.setText(item.toString());
        holder.stocks.setText(String.valueOf(item.getStocks()));

        // Set plus click listener
        holder.setOnPlusClickListener(view -> {
            int qty = item.getStocks() + 1;
            item.setStocks(qty);
            FilterableItemAdapter.this.notifyItemChanged(items.indexOf(item));
            Database.updateItem(item).addOnCompleteListener(task ->
                    Toast.makeText(view.getContext(), R.string.item_update_success, Toast.LENGTH_SHORT).show()
            );
        });

        // Set minus click listeners
        holder.setOnMinusClickListener(view -> {
            int qty = item.getStocks() - 1;
            if (qty >= 0) {
                item.setStocks(qty);
                FilterableItemAdapter.this.notifyItemChanged(items.indexOf(item));
                Database.updateItem(item).addOnCompleteListener(task ->
                        Toast.makeText(view.getContext(), R.string.item_update_success, Toast.LENGTH_SHORT).show()
                );
            } else {
                Toast.makeText(view.getContext(), R.string.alert_negative_stocks, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
