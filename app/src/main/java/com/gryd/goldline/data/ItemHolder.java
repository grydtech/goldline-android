package com.gryd.goldline.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gryd.goldline.R;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/5/17
 */

class ItemHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView stocks;
    private Button btn_plus;
    private Button btn_minus;

    ItemHolder(final View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.txt_name);
        stocks = itemView.findViewById(R.id.txt_stocks);
        btn_plus = itemView.findViewById(R.id.btn_plus);
        btn_minus = itemView.findViewById(R.id.btn_minus);
    }

    void IncreaseStocks(int qty) {
        int prev_qty = Integer.parseInt(stocks.getText().toString());
        int new_qty = prev_qty + qty;
        stocks.setText(String.valueOf(new_qty));
    }

    void DecreaseStocks(int qty) {
        int prev_qty = Integer.parseInt(stocks.getText().toString());
        int new_qty = prev_qty - qty;
        if (new_qty >= 0) {
            stocks.setText(String.valueOf(new_qty));
        }
    }

    void setOnPlusClickListener(View.OnClickListener listener) {
        btn_plus.setOnClickListener(listener);
    }

    void setOnMinusClickListener(View.OnClickListener listener) {
        btn_minus.setOnClickListener(listener);
    }
}
