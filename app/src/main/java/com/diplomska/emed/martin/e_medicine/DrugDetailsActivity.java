package com.diplomska.emed.martin.e_medicine;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Martin on 29-Jun-15.
 */
public class DrugDetailsActivity extends ActionBarActivity {

    private Intent intent;
    private String[] contraindications;
    private String[] advices;

    private TextView drugName;
    private TableLayout tableContra;
    private TableLayout tableAdvice;
    private TextView contraDesc;
    private TextView adviceDesc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);

        getSupportActionBar().setTitle(R.string.drug_details);

        intent = getIntent();

        drugName = (TextView) findViewById(R.id.txtName);
        drugName.setText(intent.getStringExtra("name"));
        fillTblContra(intent.getStringArrayExtra("contraindications"));
        fillTblAdvices(intent.getStringArrayExtra("advices"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_details, menu);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(DrugDetailsActivity.this);
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

    //Function for creating the table rows for contraindications
    private void fillTblContra(String[] contra) {
        tableContra = (TableLayout) findViewById(R.id.tblContra);

        for (int i = 0; i < contra.length; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            contraDesc = new TextView(this);
            contraDesc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            contraDesc.setTextSize(getResources().getDimension(R.dimen.cont_adv_size));
            contraDesc.setText(Html.fromHtml("&#183;") + " " + contra[i]);
            row.addView(contraDesc);
            tableContra.addView(row, i);
        }
    }

    //Function for creating the table rows for advices
    private void fillTblAdvices(String[] adv) {
        tableAdvice = (TableLayout) findViewById(R.id.tblAdvice);

        for (int i = 0; i < adv.length; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            adviceDesc = new TextView(this);
            adviceDesc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            adviceDesc.setTextSize(getResources().getDimension(R.dimen.cont_adv_size));
            adviceDesc.setText(Html.fromHtml("&#183;") + " " + adv[i]);
            row.addView(adviceDesc);
            tableAdvice.addView(row, i);
        }
    }
}
