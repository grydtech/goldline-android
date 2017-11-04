package com.gryd.goldline.fragments;

import android.support.v4.app.Fragment;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class FragmentFactory {

    public static Fragment getItemViewInstance(int position) {
        switch (position) {
            case 0:
                return new TyreFragment();
            case 1:
                return new BatteryFragment();
            case 2:
                return new TubeFragment();
            default:
                throw new IllegalArgumentException("Invalid Position");
        }
    }

    public static Fragment getItemAddInstance(int position) {
        switch (position) {
            case 0:
                return new TyreAddFragment();
            case 1:
                return new BatteryAddFragment();
            case 2:
                return new TubeAddFragment();
            default:
                throw new IllegalArgumentException("Invalid Position");
        }
    }
}
