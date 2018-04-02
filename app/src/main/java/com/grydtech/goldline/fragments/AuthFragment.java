package com.grydtech.goldline.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 2/28/18
 */

public class AuthFragment extends DialogFragment {

    public static AuthFragment newInstance() {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
