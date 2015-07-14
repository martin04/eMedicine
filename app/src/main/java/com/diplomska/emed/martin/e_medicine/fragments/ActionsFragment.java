package com.diplomska.emed.martin.e_medicine.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.diplomska.emed.martin.e_medicine.MainActivity;
import com.diplomska.emed.martin.e_medicine.PillIdActivity;
import com.diplomska.emed.martin.e_medicine.R;

/**
 * Created by Martin on 7/14/2015.
 */
public class ActionsFragment extends Fragment {

    private Button btnDrugs;
    private Button btnPillIdentifier;

    public ActionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.actions_fragment, container, false);
        btnDrugs = (Button) v.findViewById(R.id.btnDrugs);
        btnPillIdentifier = (Button) v.findViewById(R.id.btnPill);

        btnDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Intent intent = new Intent(ctx, MainActivity.class);
                ctx.startActivity(intent);
            }
        });

        btnPillIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Intent intent = new Intent(ctx, PillIdActivity.class);
                ctx.startActivity(intent);
            }
        });
        return v;
    }
}
