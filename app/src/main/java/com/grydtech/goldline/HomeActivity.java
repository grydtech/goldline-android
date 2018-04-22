package com.grydtech.goldline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.grydtech.goldline.fragments.AddItemFragment;
import com.grydtech.goldline.models.ItemType;

public class HomeActivity extends AppCompatActivity {

    public static boolean isAuthorized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three primary sections of the activity.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(sectionsPagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fab.setOnClickListener(view -> {
            if (isAuthorized) {
                ItemType itemType = ItemType.valueOf(tabLayout.getSelectedTabPosition());
                DialogFragment fragment = AddItemFragment.newInstance(itemType);
                fragment.show(fragmentManager, getString(R.string.tag_item_fragment));
            } else {
                Toast.makeText(this, "Not authorized", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_switch_lock) {
            if (isAuthorized) {
                isAuthorized = false;
                item.setIcon(R.drawable.ic_lock_outline_black_24dp);
            } else {
                isAuthorized = true;
                item.setIcon(R.drawable.ic_lock_open_black_24dp);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
