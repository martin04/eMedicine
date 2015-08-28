package com.diplomska.emed.martin.e_medicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.models.ShapeModel;

import java.util.ArrayList;

/**
 * Created by Martin on 8/28/2015.
 */
public class ShapesAdapter extends ArrayAdapter<ShapeModel> {

    private Activity act;
    private ArrayList shapes;
    LayoutInflater inflater;
    ShapeModel sm;

    public ShapesAdapter(Activity act, int itemLayout, ArrayList shapes) {
        super(act, itemLayout, shapes);
        this.act = act;
        this.shapes = shapes;
        inflater = (LayoutInflater) this.act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_shape_item, parent, false);

        sm = null;
        sm = (ShapeModel) shapes.get(position);

        TextView name = (TextView) row.findViewById(R.id.txtColorString);
        ImageView shapeImg = (ImageView) row.findViewById(R.id.imgColor);

        if (position == 0) {
            name.setText(sm.getShape());
            shapeImg.setVisibility(View.GONE);
        } else {
            name.setText(sm.getShape());
            shapeImg.setImageResource(sm.getImage());
        }

        return row;
    }
}
