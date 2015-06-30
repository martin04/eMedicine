package com.diplomska.emed.martin.e_medicine;


import android.app.AlertDialog;
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

import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.DrugNameAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.task.LoadDBTask;

import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    // private DrugAdapter drug;
    private RecyclerView drugView;
    private DrugNameAdapter adapter;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drugView = (RecyclerView) findViewById(R.id.lstDrugs);
        drugView.setHasFixedSize(true);
        manager = new LinearLayoutManager(MainActivity.this);
        drugView.setLayoutManager(manager);

        //task for filling the database
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
        //filling the list view with the drugs
        adapter = new DrugNameAdapter(drugs);
        drugView.setAdapter(adapter);
    }

    @Override
    public void onTaskNotCompleted() {
        Toast.makeText(MainActivity.this, "Oops there is sth wrong!", Toast.LENGTH_SHORT).show();
    }
}
