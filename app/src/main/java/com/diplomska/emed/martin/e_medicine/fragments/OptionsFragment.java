package com.diplomska.emed.martin.e_medicine.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.diplomska.emed.martin.e_medicine.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin on 06-Jul-15.
 */
public class OptionsFragment extends Fragment {

    private Spinner colorSpin;
    private Spinner shapeSpin;

    private Map<String,String> spinSet;

    public OptionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.options_fragment, container, false);

        colorSpin = (Spinner) v.findViewById(R.id.lstColors);
        shapeSpin = (Spinner) v.findViewById(R.id.lstShapes);

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.colors));
        colorAdapter.setDropDownViewResource(R.layout.color_spinner_item);
        colorSpin.setAdapter(colorAdapter);

        return v;
    }

    public void loadMap(){
        spinSet=new HashMap<String,String>();
        String[] colors=getResources().getStringArray(R.array.colors_hex);
        String[] colorNames=getResources().getStringArray(R.array.colors);

        for(int i=0;i<colorNames.length;i++){
            spinSet.put(colors[i],colorNames[i]);
        }

    }
}
