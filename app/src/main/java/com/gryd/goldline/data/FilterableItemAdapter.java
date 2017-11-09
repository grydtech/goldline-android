package com.gryd.goldline.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.gryd.goldline.R;
import com.gryd.goldline.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class FilterableItemAdapter<E extends Item> extends RecyclerView.Adapter<ItemHolder> implements Filterable {

    private final Context context;
    private final Filter filter;
    private List<E> items;
    private List<E> itemSource;
    private String search_text;

    FilterableItemAdapter(Context context, List<E> source_items) {
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
                    ArrayList<E> fRecords = new ArrayList<>();
                    for (E item : itemSource) {
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
                FilterableItemAdapter.this.items.clear();
                FilterableItemAdapter.this.items.addAll((List<E>) results.values);
                FilterableItemAdapter.this.notifyDataSetChanged();
            }
        };
    }

    void notifyItemSourceUpdated() {
        this.items.clear();
        this.items.addAll(this.itemSource);
        this.filter.filter(search_text, new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int i) {
                FilterableItemAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final E item = items.get(position);
        Log.d(holder.name.toString(), holder.stocks.toString());
        holder.name.setText(item.toString());
        holder.stocks.setText(String.valueOf(item.getStocks()));

        // Set plus click listener
        holder.setOnPlusClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = item.getStocks() + 1;
                item.setStocks(qty);
                notifyItemChanged(items.indexOf(item));
                Database.updateItem(item);
            }
        });

        // Set minus click listeners
        holder.setOnMinusClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = item.getStocks() - 1;
                if (qty >= 0) {
                    item.setStocks(qty);
                    notifyItemChanged(items.indexOf(item));
                    Database.updateItem(item);
                } else {
                    Toast.makeText(view.getContext(), R.string.alert_negative_stocks, Toast.LENGTH_SHORT).show();
                }
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
