package com.diplomska.emed.martin.e_medicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplomska.emed.martin.e_medicine.PillIdActivity;
import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.models.ColorModel;

import java.util.ArrayList;

/**
 * Created by Martin on 8/25/2015.
 */
public class ColorsAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList lstColors;
    LayoutInflater inflater;
    ColorModel color = null;

    public ColorsAdapter(Activity act, int itemLayout, ArrayList colors) {
        super(act, itemLayout, colors);
        activity = act;
        lstColors = colors;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        View row = inflater.inflate(R.layout.spinner_color_item, parent, false);

        color = null;
        color = (ColorModel) lstColors.get(position);

        TextView name = (TextView) row.findViewById(R.id.txtColorString);
        ImageView hexColor = (ImageView) row.findViewById(R.id.imgColor);

        if (position == 0) {
            name.setText("Please select a color");
            hexColor.setVisibility(View.GONE);
        } else {
            name.setText(color.getName());
            hexColor.setBackgroundColor(Color.parseColor(color.getColorHex()));
        }

        return row;
    }

}

