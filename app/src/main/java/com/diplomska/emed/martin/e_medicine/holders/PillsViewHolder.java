package com.diplomska.emed.martin.e_medicine.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.interfaces.onPillIdDetailsHandler;

/**
 * Created by Martin on 8/28/2015.
 */
public class PillsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgPill;
    public TextView pillName;
    public Button btnDetails;
    private onPillIdDetailsHandler listener;

    public PillsViewHolder(View itemView, onPillIdDetailsHandler listener) {
        super(itemView);

        imgPill = (ImageView) itemView.findViewById(R.id.imgPill);
        pillName = (TextView) itemView.findViewById(R.id.pillName);
        btnDetails = (Button) itemView.findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(this);

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onDetailsClicked(v.getContext(),getAdapterPosition());
    }
}
