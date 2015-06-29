package com.diplomska.emed.martin.e_medicine.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.R;

/**
 * Created by Martin on 26-Jun-15.
 */
public class DrugNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //I use this to find all the views in the list_item_layout.xml

    public ImageView alarm;
    public TextView genericName;
    public TextView latinName;

    public DrugNameViewHolder(View itemView) {
        super(itemView);
        
        alarm=(ImageView)itemView.findViewById(R.id.imgAlarm);
        genericName = (TextView) itemView.findViewById(R.id.txtGeneric);
        latinName = (TextView) itemView.findViewById(R.id.txtLatin);

        alarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context ctx=itemView.getContext();
        int pos=getPosition();
        Toast.makeText(ctx,"Button "+pos+ " Clicked",Toast.LENGTH_SHORT).show();
    }
}
