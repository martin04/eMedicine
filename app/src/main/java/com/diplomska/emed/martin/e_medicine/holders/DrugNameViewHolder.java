package com.diplomska.emed.martin.e_medicine.holders;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.DrugDetailsActivity;
import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.adapter.AdviseAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.ContraindicationAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.onAlarmCreated;


/**
 * Created by Martin on 26-Jun-15.
 */
public class DrugNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //I use this to find all the views in the list_item_layout.xml

    public ImageButton alarm;
    public ImageView pills;
    public TextView genericName;
    public TextView latinName;
    public TextView drugCode;

    private ContraindicationAdapter ca;
    private AdviseAdapter aa;
    private onAlarmCreated listener;

    public DrugNameViewHolder(View itemView, onAlarmCreated listener) {
        super(itemView);

        pills = (ImageView) itemView.findViewById(R.id.imgPill);
        alarm = (ImageButton) itemView.findViewById(R.id.imgAlarm);
        genericName = (TextView) itemView.findViewById(R.id.txtGeneric);
        latinName = (TextView) itemView.findViewById(R.id.txtLatin);
        drugCode = (TextView) itemView.findViewById(R.id.txtCode);

        this.listener = listener;

        pills.setOnClickListener(this);
        alarm.setOnClickListener(this);
        genericName.setOnClickListener(this);
        latinName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context ctx = itemView.getContext();
        int pos = getAdapterPosition();
        String code = "";

        if (v instanceof ImageButton) {
            listener.createAlarm(ctx, latinName.getText().toString());
        } else {
            code = drugCode.getText().toString();
            String[] contraindications = getContraindications(ctx, code).split("\n");
            String[] advices = getAdvices(ctx, code).split("\n");
            String name = latinName.getText().toString();

            Intent details = new Intent(ctx, DrugDetailsActivity.class);
            details.putExtra("name", name);
            details.putExtra("contraindications", contraindications);
            details.putExtra("advices", advices);
            ctx.startActivity(details);
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
