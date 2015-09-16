package com.diplomska.emed.martin.e_medicine;


import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.adapter.DrugNameAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.task.DailymedTask;
import com.diplomska.emed.martin.e_medicine.task.LoadDBTask;
import com.diplomska.emed.martin.e_medicine.task.SearchTask;

import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnTaskCompleted, SearchView.OnQueryTextListener {

    private RecyclerView drugView;
    private DrugNameAdapter adapter;
    private LinearLayoutManager manager;
    private ProgressDialog pdLoading;
    private TextView txtNoResults;

    private boolean dataFilled;
    private ConnectivityManager cm;
    private NetworkInfo ni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataFilled = false;

        drugView = (RecyclerView) findViewById(R.id.lstDrugs);
        drugView.setHasFixedSize(true);
        manager = new LinearLayoutManager(MainActivity.this);
        drugView.setLayoutManager(manager);

        txtNoResults = (TextView) findViewById(R.id.txtNoResult);

        /*LoadDBTask load = new LoadDBTask(MainActivity.this, this);
        load.execute();*/
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
        if (!isConnected) {
            dataFilled = false;
            connectionDialog();
        } else {
            DailymedTask load = new DailymedTask(this, this);
            load.execute("http://dailymed.nlm.nih.gov/dailymed/services/v2/drugnames.json?pagesize=20&page=1");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);

        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_alarms:
                startActivity(new Intent(AlarmClock.ACTION_SET_ALARM));
                return true;
            case R.id.action_about:
                showAbout();
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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

    //Function for creating the About dialog window
    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.about_title);
        builder.setMessage(R.string.about_message);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onTaskStarted() {
        pdLoading = new ProgressDialog(this);
        pdLoading.setMessage(getResources().getString(R.string.loading_db));
        pdLoading.show();
    }

    @Override
    public void onTaskCompleted(List<Drug> drugs) {
        if (drugs.size() != 0) {
            adapter = new DrugNameAdapter(drugs);
            adapter.sort(new Comparator<Drug>() {
                @Override
                public int compare(Drug lhs, Drug rhs) {
                    return lhs.getGeneric_name().compareTo(rhs.getGeneric_name());
                }
            });
            drugView.setAdapter(adapter);
            dataFilled = true;
        } else {
            drugView.setVisibility(View.GONE);
            txtNoResults.setVisibility(View.VISIBLE);
        }
        pdLoading.cancel();
    }

    @Override
    public void onTaskNotCompleted() {
        Toast.makeText(MainActivity.this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        pdLoading.cancel();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchTask search = new SearchTask(this, this);
        search.execute(query.trim());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        /*SearchTask search = new SearchTask(this, this);
        search.execute(newText);*/
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!dataFilled) {
            DailymedTask load = new DailymedTask(this, this);
            load.execute("http://dailymed.nlm.nih.gov/dailymed/services/v2/drugnames.json?pagesize=20&page=1");
        }
    }

    private void connectionDialog() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle(R.string.connection_title)
                .setMessage(R.string.connection_message)
                .setNegativeButton(R.string.connection_cancel_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton(R.string.connection_ok_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        startActivity(new Intent(Settings.ACTION_SETTINGS));

                    }
                })
                .create()
                .show();
    }
}
