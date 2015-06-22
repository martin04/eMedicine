package com.diplomska.emed.martin.e_medicine;


import android.app.AlertDialog;
import android.app.SearchManager;

import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.task.LoadDBTask;


import java.util.Comparator;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView lv;
    private String[] drugs;
    private DrugAdapter drug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //task for filling the database
        LoadDBTask load = new LoadDBTask(MainActivity.this);
        load.execute();
        try{Thread.sleep(2000);}catch(InterruptedException ex){ex.printStackTrace();}

        //filling the list view with the drugs
        drug = new DrugAdapter(this);
        lv = (ListView) findViewById(R.id.lista);
        drug.open();
        List<Drug> pom = drug.getAllItems();
        drug.close();
        drugs = new String[pom.size()];
        for (int i = 0; i < pom.size(); i++) {
            drugs[i] = pom.get(i).getGeneric_name();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(), android.R.layout.simple_list_item_1, drugs);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        lv.setAdapter(adapter);
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

    //funkcija za kreiranje na dijalog prozorecot za About
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
}
