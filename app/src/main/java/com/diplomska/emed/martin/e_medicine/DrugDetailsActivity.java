package com.diplomska.emed.martin.e_medicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.adapter.DrugViewPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Martin on 29-Jun-15.
 */
public class DrugDetailsActivity extends AppCompatActivity {

    private Intent intent;

    private TextView drugName;
    private TableLayout tableContra;
    private TableLayout tableAdvice;
    private TextView contraDesc;
    private TextView adviceDesc;

    private TabLayout tabs;
    private ViewPager pager;
    private DrugViewPagerAdapter adapter;
    private ArrayList<String> names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);

        //must check if null
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        names = new ArrayList<>(Arrays.asList(getString(R.string.tab_contra), getString(R.string.tab_advice)
                , getString(R.string.tab_alt_names)));
        intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("name").split(",")[0]);

        adapter = new DrugViewPagerAdapter(getSupportFragmentManager(), names, 3, intent.getStringArrayExtra("contraindications"),
                intent.getStringArrayExtra("advices"),intent.getStringExtra("name").split(","));
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_contra)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_advice)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_alt_names)));
        tabs.setTabTextColors(ContextCompat.getColor(this, R.color.icons), ContextCompat.getColor(this, R.color.icons));
        tabs.setupWithViewPager(pager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_alarms:
                startActivity(new Intent(AlarmClock.ACTION_SET_ALARM));
                return true;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_refresh:
                //ovde uste ednas povikaj so imeto prateno vo intentot
                //za detalite
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_details, menu);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            menu.findItem(R.id.action_alarms).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }
}
