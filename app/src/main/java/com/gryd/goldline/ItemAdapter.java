package com.gryd.goldline;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gryd.goldline.models.Item;

import java.util.List;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    private List<Item> items;
    private Context context;

    public ItemAdapter(Context context, List<Item> items) {
        super(context, R.layout.item_view, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row;
        ItemHolder holder;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(R.layout.item_view, parent, false);

        holder = new ItemHolder();
        holder.item = items.get(position);
        holder.plus_button = row.findViewById(R.id.button_plus);
        holder.plus_button.setTag(holder.item);
        holder.minus_button = row.findViewById(R.id.button_minus);
        holder.minus_button.setTag(holder.item);

        holder.brand = row.findViewById(R.id.text_item_name);
        holder.stocks = row.findViewById(R.id.text_item_stocks);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(ItemHolder holder) {
        holder.brand.setText(holder.item.getBrand());
        holder.stocks.setText(String.valueOf(holder.item.getStocks()));
    }

    public static class ItemHolder {
        Item item;
        TextView brand;
        TextView stocks;
        Button plus_button;
        Button minus_button;
    }
}
