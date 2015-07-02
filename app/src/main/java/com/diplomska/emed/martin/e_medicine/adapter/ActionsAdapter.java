package com.diplomska.emed.martin.e_medicine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.holders.ActionsViewHolder;

/**
 * Created by Martin on 01-Jul-15.
 */
public class ActionsAdapter extends RecyclerView.Adapter<ActionsViewHolder> {

    private String[] actions={"Medicine search & information","Pill identifier"};
    private int[] pictures={R.drawable.ic_action_find,R.drawable.ic_pills};

    @Override
    public ActionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.action_item_layout,parent,false);
        return new ActionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ActionsViewHolder holder, int position) {
        holder.picture.setImageResource(pictures[position]);
        holder.text.setText(actions[position]);
    }

    @Override
    public int getItemCount() {
        return actions.length;
    }
}
