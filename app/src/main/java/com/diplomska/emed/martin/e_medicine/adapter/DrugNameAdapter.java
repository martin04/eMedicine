package com.diplomska.emed.martin.e_medicine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.holders.DrugNameViewHolder;
import com.diplomska.emed.martin.e_medicine.models.Drug;

import java.util.List;

/**
 * Created by Martin on 26-Jun-15.
 */
public class DrugNameAdapter extends RecyclerView.Adapter<DrugNameViewHolder> {


    List<Drug> drugsDataSet;

    public DrugNameAdapter(List<Drug> drugs) {
        drugsDataSet = drugs;
    }

    @Override
    public DrugNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new DrugNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrugNameViewHolder holder, int position) {
        Drug d = drugsDataSet.get(position);
        holder.genericName.setText(d.getGeneric_name());
        holder.latinName.setText(d.getLatin_name());
        holder.drugCode.setText(d.getCode());
    }

    @Override
    public int getItemCount() {
        return drugsDataSet.size();
    }
}
