package com.diplomska.emed.martin.e_medicine;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.adapter.DrugNameAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.task.SearchTask;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Martin on 17-Jun-15.
 */
public class SearchResultsActivity extends AppCompatActivity implements OnTaskCompleted,SearchView.OnQueryTextListener {

    private RecyclerView result;
    private DrugNameAdapter adapter;
    private LinearLayoutManager manager;

    private ProgressDialog pgSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        //must check if null
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = (RecyclerView) findViewById(R.id.lstResult);
        result.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        result.setLayoutManager(manager);

        handleIntent(getIntent());
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
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    //This function will be called when the invocation of onNewIntent occurs
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchTask search = new SearchTask(this, this);
            search.execute(query);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    //Function for creating the About dialog window
    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchResultsActivity.this);
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
        pgSearch = new ProgressDialog(this);
        pgSearch.setMessage(getResources().getString(R.string.searching_db));
        pgSearch.show();
    }

    @Override
    public void onTaskCompleted(List<Drug> drugs) {
        adapter = new DrugNameAdapter(drugs);
        adapter.sort(new Comparator<Drug>() {
            @Override
            public int compare(Drug lhs, Drug rhs) {
                return lhs.getGeneric_name().compareTo(rhs.getGeneric_name());
            }
        });
        result.setAdapter(adapter);
        pgSearch.cancel();
    }

    @Override
    public void onTaskNotCompleted() {
        Toast.makeText(SearchResultsActivity.this, "Oops there is sth wrong!", Toast.LENGTH_SHORT).show();
        pgSearch.cancel();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        SearchTask search = new SearchTask(this, this);
        search.execute(newText);
        return true;
    }
}
