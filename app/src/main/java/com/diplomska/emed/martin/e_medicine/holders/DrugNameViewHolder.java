package com.diplomska.emed.martin.e_medicine.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.R;

/**
 * Created by Martin on 26-Jun-15.
 */
public class DrugNameViewHolder extends RecyclerView.ViewHolder {
    //I use this to find all the views in the list_item_layout.xml

    //public ImageView pills;
    //public ImageView alarm;
    public TextView genericName;
    public TextView latinName;

    public DrugNameViewHolder(View itemView) {
        super(itemView);
        //pills=(ImageView)itemView.findViewById(R.id.imgPill);
        //alarm=(ImageView)itemView.findViewById(R.id.imgAlarm);
        genericName = (TextView) itemView.findViewById(R.id.txtGeneric);
        latinName = (TextView) itemView.findViewById(R.id.txtLatin);
    }
}
