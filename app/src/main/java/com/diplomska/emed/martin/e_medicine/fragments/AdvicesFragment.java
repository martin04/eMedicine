package com.diplomska.emed.martin.e_medicine.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class AdvicesFragment extends Fragment {

    private TableLayout tableAdvice;
    private TextView adviceDesc;

    public AdvicesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.advice_fragment, container, false);


        fillTblAdvices(v,getArguments().getStringArray("advices"));

        return v;
    }

    //Function for creating the table rows for advices
    private void fillTblAdvices(View v,String[] adv) {
        tableAdvice = (TableLayout) v.findViewById(R.id.tblAdvice);

        for (int i = 0; i < adv.length; i++) {
            TableRow row = new TableRow(v.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            adviceDesc = new TextView(v.getContext());
            adviceDesc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            adviceDesc.setText(Html.fromHtml("&#183;") + " " + adv[i]);
            row.addView(adviceDesc);
            tableAdvice.addView(row, i);
        }
    }
}
