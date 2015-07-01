package com.diplomska.emed.martin.e_medicine;


import android.app.AlertDialog;
import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.adapter.DrugNameAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.task.LoadDBTask;

import java.util.List;


public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    private RecyclerView drugView;
    private DrugNameAdapter adapter;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        drugView = (RecyclerView) findViewById(R.id.lstDrugs);
        drugView.setHasFixedSize(true);
        manager = new LinearLayoutManager(MainActivity.this);
        drugView.setLayoutManager(manager);

        LoadDBTask load = new LoadDBTask(MainActivity.this, this);
        load.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);

        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_alarms) {
            return true;
        }

        if (id == R.id.action_about) {
            showAbout();
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
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you really want to exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
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
    public void onTaskCompleted(List<Drug> drugs) {
        adapter = new DrugNameAdapter(drugs);
        drugView.setAdapter(adapter);
    }

    @Override
    public void onTaskNotCompleted() {
        Toast.makeText(MainActivity.this, "Oops there is sth wrong!", Toast.LENGTH_SHORT).show();
    }
}
