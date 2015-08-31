package com.diplomska.emed.martin.e_medicine.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.holders.PillsViewHolder;
import com.diplomska.emed.martin.e_medicine.models.PillModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Martin on 8/28/2015.
 */
public class PillsAdapter extends RecyclerView.Adapter<PillsViewHolder> {

    private List<PillModel> pills;
    private ViewGroup viewGroup;

    public PillsAdapter(List<PillModel> pills) {
        this.pills = pills;
    }

    @Override
    public PillsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.pill_item_layout, parent, false);
        viewGroup = parent;
        return new PillsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PillsViewHolder holder, int position) {
        PillModel pill = pills.get(position);
        Picasso.with(viewGroup.getContext()).load(pill.getImgUrl()).into(holder.imgPill);
        holder.pillName.setText(pill.getName());
    }

    @Override
    public int getItemCount() {
        return pills.size();
    }
}
