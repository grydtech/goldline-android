package com.grydtech.goldline.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.grydtech.goldline.R;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 2/28/18
 */

public class ConfirmationFragment extends DialogFragment {

    public static ConfirmationFragment newInstance() {
        ConfirmationFragment fragment = new ConfirmationFragment();
        Bundle args = new Bundle();
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

        // Build the layout
        builder.setTitle(R.string.text_confirmation);
        builder.setView(R.layout.fragment_confirmation);
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {
        });
        return builder.create();
    }
}
