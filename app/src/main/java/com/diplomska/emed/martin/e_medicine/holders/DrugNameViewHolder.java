package com.diplomska.emed.martin.e_medicine.holders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.DrugDetailsActivity;
import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.adapter.AdviseAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.ContraindicationAdapter;


/**
 * Created by Martin on 26-Jun-15.
 */
public class DrugNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //I use this to find all the views in the list_item_layout.xml

    public ImageView alarm;
    public TextView genericName;
    public TextView latinName;
    public TextView drugCode;

    private ContraindicationAdapter ca;
    private AdviseAdapter aa;

    public DrugNameViewHolder(View itemView) {
        super(itemView);

        alarm = (ImageView) itemView.findViewById(R.id.imgAlarm);
        genericName = (TextView) itemView.findViewById(R.id.txtGeneric);
        latinName = (TextView) itemView.findViewById(R.id.txtLatin);
        drugCode = (TextView) itemView.findViewById(R.id.txtCode);

        alarm.setOnClickListener(this);
        genericName.setOnClickListener(this);
        latinName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context ctx = itemView.getContext();
        int pos = getPosition();
        String code = "";

        if (v instanceof TextView) {

            code = drugCode.getText().toString();
            String[] contraindications = getContraindications(ctx, code).split("\n");
            String[] advices = getAdvices(ctx, code).split("\n");
            String name = latinName.getText().toString();

            Intent details = new Intent(ctx, DrugDetailsActivity.class);
            details.putExtra("name", name);
            details.putExtra("contraindications", contraindications);
            details.putExtra("advices", advices);
            ctx.startActivity(details);

        } else if (v instanceof ImageView) {
            //ovde sredi so alarmi rabota
            Toast.makeText(ctx, "Button " + pos + " Clicked", Toast.LENGTH_SHORT).show();

        }
    }

    //Helper function for getting the contraindicatons
    private String getContraindications(Context ctx, String code) {
        ca = new ContraindicationAdapter(ctx);
        String result = "";

        ca.open();
        result = ca.getContraByDrugCode(code);
        ca.close();

        return result;

    }

    //Helper function for getting the advices
    private String getAdvices(Context ctx, String code) {
        aa = new AdviseAdapter(ctx);
        String result = "";

        aa.open();
        result = aa.getAdviseByDrugCode(code);
        aa.close();

        return result;

    }
}
