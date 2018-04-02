package com.grydtech.goldline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grydtech.goldline.fragments.ItemFragment;
import com.grydtech.goldline.models.ItemType;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {

    SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ItemType itemType = ItemType.valueOf(position);
        return ItemFragment.newInstance(itemType);
    }

    @Override
    public int getCount() {
        return 3;
    }
}