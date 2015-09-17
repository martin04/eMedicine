package com.diplomska.emed.martin.e_medicine.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.R;

/**
 * Created by Martin on 9/5/2015.
 */
public class AdvicesFragment extends Fragment {

    private TextView precautions;
    private TextView warnings;
    private TextView generalPrecautions;

    public AdvicesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.advice_fragment, container, false);

        precautions = (TextView) v.findViewById(R.id.txtPrecautions);
        warnings = (TextView) v.findViewById(R.id.txtWarnings);
        generalPrecautions = (TextView) v.findViewById(R.id.txtGenralP);

        String[] adv = getArguments().getStringArray("advices");
        if(adv!=null) {
            precautions.setText(TextUtils.isEmpty(adv[0])? "" : adv[0].substring(11));
            warnings.setText(TextUtils.isEmpty(adv[1]) ? "" : adv[1].substring(9));
            generalPrecautions.setText(TextUtils.isEmpty(adv[2])? "" : adv[2].substring(8));
        }

        return v;
    }
}
