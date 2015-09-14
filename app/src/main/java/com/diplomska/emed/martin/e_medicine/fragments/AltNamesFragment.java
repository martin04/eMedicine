package com.diplomska.emed.martin.e_medicine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.R;

/**
 * Created by Martin on 9/5/2015.
 */
public class AltNamesFragment extends Fragment {

    private TextView txtAltNames;

    public AltNamesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alt_name_fragment, container, false);

        txtAltNames = (TextView) v.findViewById(R.id.txtAltNames);
        String[] names= getArguments().getStringArray("alt_names");
        String result = "";
        for (int i = 0; i < names.length; i++) {
                result += names[i]+"\n\n";
        }

        txtAltNames.setText(result);
        return v;
    }
}
