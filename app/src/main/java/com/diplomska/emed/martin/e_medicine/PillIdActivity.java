package com.diplomska.emed.martin.e_medicine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.diplomska.emed.martin.e_medicine.fragments.OptionsFragment;
import com.diplomska.emed.martin.e_medicine.fragments.ResultFragment;

/**
 * Created by Martin on 06-Jul-15.
 */
public class PillIdActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pill_id_activity);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OptionsFragment fragment = new OptionsFragment();
        getFragmentManager().beginTransaction().replace(R.id.tblSample, fragment, "options").addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().findFragmentByTag("result") != null) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(getFragmentManager().findFragmentByTag("result") != null){
            ResultFragment res=(ResultFragment)getFragmentManager().findFragmentByTag("result");
            getFragmentManager().beginTransaction().detach(res).attach(res).addToBackStack(null).commit();
        }
    }
}
