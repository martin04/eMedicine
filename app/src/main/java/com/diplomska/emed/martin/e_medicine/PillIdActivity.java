package com.diplomska.emed.martin.e_medicine;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.diplomska.emed.martin.e_medicine.fragments.OptionsFragment;

/**
 * Created by Martin on 06-Jul-15.
 */
public class PillIdActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pill_id_activity);

        OptionsFragment fragment=new OptionsFragment();
        getFragmentManager().beginTransaction().replace(R.id.tblSample,fragment).commit();
    }
}
