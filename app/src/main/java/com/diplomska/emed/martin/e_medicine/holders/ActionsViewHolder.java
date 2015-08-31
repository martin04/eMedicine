package com.diplomska.emed.martin.e_medicine.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.MainActivity;
import com.diplomska.emed.martin.e_medicine.PillIdActivity;
import com.diplomska.emed.martin.e_medicine.R;

/**
 * Created by Martin on 01-Jul-15.
 */
public class ActionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView picture;
    public TextView text;

    public ActionsViewHolder(View itemView) {
        super(itemView);
        picture=(ImageView)itemView.findViewById(R.id.imgActionFind);
        text=(TextView)itemView.findViewById(R.id.txtAction);

        picture.setOnClickListener(this);
        text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context ctx = itemView.getContext();
        int position = getAdapterPosition();
        switch (position) {
            case 0:
                Intent intent = new Intent(ctx, MainActivity.class);
                ctx.startActivity(intent);
                break;
            case 1:
                Intent intent2 = new Intent(ctx, PillIdActivity.class);
                ctx.startActivity(intent2);
                //Toast.makeText(ctx, "Pill identifier is still under construction !", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(ctx, "Wrong selection! Please try again.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
