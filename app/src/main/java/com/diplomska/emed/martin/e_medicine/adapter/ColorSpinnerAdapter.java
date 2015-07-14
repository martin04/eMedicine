package com.diplomska.emed.martin.e_medicine.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.diplomska.emed.martin.e_medicine.PillIdActivity;
import com.diplomska.emed.martin.e_medicine.fragments.OptionsFragment;
import com.diplomska.emed.martin.e_medicine.models.ColorSpinner;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Martin on 7/8/2015.
 */
public class ColorSpinnerAdapter extends ArrayAdapter<String> {

    private AppCompatActivity activity;
    private ArrayList data;
    ColorSpinner model = null;
    LayoutInflater inflater;
    
    public ColorSpinnerAdapter(PillIdActivity activity, int itemLayout, ArrayList values) {
        super(activity, itemLayout, values);


    }
}
