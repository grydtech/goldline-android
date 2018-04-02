package com.grydtech.goldline.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grydtech.goldline.R;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/5/17
 */

class ItemHolder extends RecyclerView.ViewHolder {

    final TextView name;
    final TextView stocks;
    private final Button btn_plus;
    private final Button btn_minus;

    ItemHolder(final View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.txt_name);
        stocks = itemView.findViewById(R.id.txt_stocks);
        btn_plus = itemView.findViewById(R.id.btn_plus);
        btn_minus = itemView.findViewById(R.id.btn_minus);
    }

    void setOnPlusClickListener(View.OnClickListener listener) {
        btn_plus.setOnClickListener(listener);
    }

    void setOnMinusClickListener(View.OnClickListener listener) {
        btn_minus.setOnClickListener(listener);
    }
}
