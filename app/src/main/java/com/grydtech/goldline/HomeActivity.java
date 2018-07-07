package com.grydtech.goldline;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grydtech.goldline.fragments.AddItemFragment;
import com.grydtech.goldline.models.ItemType;

public class HomeActivity extends AppCompatActivity {

    private static final String password = "12344321";
    public static boolean isAuthorized = false;
    public CountDownTimer countDownTimer;
    public MenuItem statusText;

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
        this.getMenuInflater().inflate(R.menu.menu_home, menu);
        statusText = menu.findItem(R.id.txt_lock_status);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_switch_lock) {
            if (isAuthorized) {
                isAuthorized = false;
                countDownTimer.cancel();
                statusText.setTitle(R.string.str_locked);
                item.setIcon(R.drawable.ic_lock_closed_24dp);
            } else {
                countDownTimer = new CountDownTimer(120000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        statusText.setTitle(String.format("Unlocked for %s s", millisUntilFinished / 1000 + 1));
                    }

                    public void onFinish() {
                        isAuthorized = false;
                        countDownTimer.cancel();
                        statusText.setTitle(R.string.str_locked);
                        item.setIcon(R.drawable.ic_lock_closed_24dp);
                    }
                };

                final AlertDialog.Builder authDialogBuilder = new AlertDialog.Builder(this);
                authDialogBuilder.setTitle("Authentication");
                authDialogBuilder.setView(R.layout.fragment_auth);
                authDialogBuilder.setPositiveButton(android.R.string.ok, null);
                AlertDialog authDialog = authDialogBuilder.create();
                authDialog.setOnShowListener(dialog -> {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(v -> {
                        EditText passwordText = authDialog.findViewById(R.id.txt_password);
                        if (passwordText != null && passwordText.getText().toString().equals(password)) {
                            isAuthorized = true;
                            item.setIcon(R.drawable.ic_lock_open_24dp);
                            countDownTimer.start();
                            // Create timer to show the countdown
                            authDialog.dismiss();
                        } else {
                            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                authDialog.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
