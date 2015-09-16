package com.diplomska.emed.martin.e_medicine;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.adapter.DrugViewPagerAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.InteractionsHandler;
import com.diplomska.emed.martin.e_medicine.interfaces.RxNormHandler;
import com.diplomska.emed.martin.e_medicine.task.InteractionsApiTask;
import com.diplomska.emed.martin.e_medicine.task.RxNormTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Martin on 29-Jun-15.
 */
public class DrugDetailsActivity extends AppCompatActivity implements RxNormHandler, InteractionsHandler {

    private Intent intent;

    private TabLayout tabs;
    private ViewPager pager;
    private DrugViewPagerAdapter adapter;
    private ArrayList<String> names;
    private ProgressDialog pDialog;
    private String mainName;

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
        mainName = intent.getStringExtra("name").split(",")[0].trim();
        getSupportActionBar().setTitle(mainName);


        pager = (ViewPager) findViewById(R.id.pager);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_contra)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_advice)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_alt_names)));
        tabs.setTabTextColors(ContextCompat.getColor(this, R.color.icons), ContextCompat.getColor(this, R.color.icons));


        new RxNormTask(this).execute(mainName);

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
                new RxNormTask(this).execute(mainName);
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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            menu.findItem(R.id.action_alarms).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public void onRxNormStarted() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading_db));
        pDialog.show();
    }

    @Override
    public void onRxNormResult(List<String> rxcui) {
        String queryParam = "";
        if (rxcui.size() == 1) {
            new InteractionsApiTask(this).execute(rxcui.get(0));
        } else {
            for (int i = 0; i < rxcui.size(); i++) {
                queryParam += rxcui.get(i) + "+";
            }
            new InteractionsApiTask(this).execute(queryParam);
        }
    }

    @Override
    public void onRxNormError() {
        Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_LONG).show();
        pDialog.dismiss();
    }

    @Override
    public void onInteractionsResult(HashMap<String, String> contraDrugs) {
        adapter = new DrugViewPagerAdapter(getSupportFragmentManager(), names, 3, contraDrugs, intent.getStringArrayExtra("contraindications"),
                intent.getStringArrayExtra("advices"), intent.getStringExtra("name").split(","));
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        pDialog.dismiss();
    }

    @Override
    public void onInteractionsError() {
        Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_LONG).show();
        pDialog.dismiss();
    }
}
