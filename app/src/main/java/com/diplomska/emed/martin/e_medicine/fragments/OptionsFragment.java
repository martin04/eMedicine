package com.diplomska.emed.martin.e_medicine.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.adapter.ColorsAdapter;
import com.diplomska.emed.martin.e_medicine.models.ColorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 06-Jul-15.
 */
public class OptionsFragment extends Fragment {

    private Spinner colorSpin;
    private Spinner shapeSpin;
    public ArrayList<ColorModel> colors;


    public OptionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.options_fragment, container, false);

        colorSpin = (Spinner) v.findViewById(R.id.lstColors);
        shapeSpin = (Spinner) v.findViewById(R.id.lstShapes);

        loadData();


        ColorsAdapter colorAdapter = new ColorsAdapter(getActivity(), R.layout.spinner_color_item, colors);
        colorSpin.setAdapter(colorAdapter);

        return v;
    }

    public void loadData() {
        colors = new ArrayList<>();
        String[] colors = getResources().getStringArray(R.array.colors_hex);
        String[] colorNames = getResources().getStringArray(R.array.colors);

        for (int i = 0; i < colorNames.length; i++) {
            this.colors.add(new ColorModel(colorNames[i], colors[i]));
        }

    }
}
