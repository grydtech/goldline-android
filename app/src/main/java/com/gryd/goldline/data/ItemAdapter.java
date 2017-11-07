package com.gryd.goldline.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gryd.goldline.R;
import com.gryd.goldline.models.Item;

import java.util.List;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final Item item = items.get(position);
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
                // TODO Notify update to database
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
                    // TODO Notify update to database
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
}
