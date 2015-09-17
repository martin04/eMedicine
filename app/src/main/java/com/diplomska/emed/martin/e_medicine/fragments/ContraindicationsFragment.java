package com.diplomska.emed.martin.e_medicine.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
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
public class ContraindicationsFragment extends Fragment {

    private TextView txtDrugDrug;
    private TextView txtContraindications;


    public ContraindicationsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contra_fragment, container, false);

        txtDrugDrug = (TextView)v.findViewById(R.id.txtDrugDrug);
        txtContraindications = (TextView)v.findViewById(R.id.txtContraindications);

        txtDrugDrug.setText(getArguments().getString("drug_drug_interactions"));
        txtContraindications.setText(fillTblContra(getArguments().getStringArray("contraindications")));

        return v;
    }

    //Function for creating the table rows for contraindications
    private String fillTblContra(String[] contra) {
        String formatted = "";
        for (int i = 0; i <contra.length ; i++) {
            formatted += contra[i]+"\n\n";
        }
        return formatted;
    }
}
