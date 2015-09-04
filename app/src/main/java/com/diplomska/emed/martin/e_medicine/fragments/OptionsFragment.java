package com.diplomska.emed.martin.e_medicine.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.adapter.ColorsAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.ShapesAdapter;
import com.diplomska.emed.martin.e_medicine.models.ColorModel;
import com.diplomska.emed.martin.e_medicine.models.ShapeModel;
import com.diplomska.emed.martin.e_medicine.task.PillIdTask;

import java.net.URL;
import java.net.URLEncoder;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 06-Jul-15.
 */
public class OptionsFragment extends Fragment implements View.OnClickListener {

    private Spinner colorSpin;
    private Spinner shapeSpin;
    private Button btnSearch;
    public ArrayList<ColorModel> colors;
    public ArrayList<ShapeModel> shapes;


    public OptionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.options_fragment, container, false);

        colorSpin = (Spinner) v.findViewById(R.id.lstColors);
        shapeSpin = (Spinner) v.findViewById(R.id.lstShapes);
        btnSearch = (Button) v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        loadData();

        ColorsAdapter colorAdapter = new ColorsAdapter(getActivity(), R.layout.spinner_color_item, colors);
        colorSpin.setAdapter(colorAdapter);

        ShapesAdapter shapeAdapter = new ShapesAdapter(getActivity(), R.layout.spinner_shape_item, shapes);
        shapeSpin.setAdapter(shapeAdapter);

        return v;
    }

    public void loadData() {
        colors = new ArrayList<>();
        shapes = new ArrayList<>();

        String[] colors = getResources().getStringArray(R.array.colors_hex);
        String[] colorNames = getResources().getStringArray(R.array.colors);

        String[] shapeNames = getResources().getStringArray(R.array.shapes);
        TypedArray arr = getResources().obtainTypedArray(R.array.shapes_images);


        for (int i = 0; i < colorNames.length; i++) {
            this.colors.add(new ColorModel(colorNames[i], colors[i]));
        }

        for (int i = 0; i < shapeNames.length; i++) {
            this.shapes.add(new ShapeModel(shapeNames[i], arr.getResourceId(i, -1)));
        }

        arr.recycle();
    }

    @Override
    public void onClick(View v) {
        String shape=((ShapeModel)shapeSpin.getSelectedItem()).getShape().toLowerCase();
        if(shape.contains(" ")){
            try {
                shape = URLEncoder.encode(shape, "UTF-8");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        if (colorSpin.getSelectedItemPosition() == 0 && shapeSpin.getSelectedItemPosition() == 0) {
            new AlertDialog.Builder(v.getContext())
                    .setTitle(getString(R.string.warning_title))
                    .setMessage(getString(R.string.color_shape_selection_msg))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else if (colorSpin.getSelectedItemPosition() != 0 && shapeSpin.getSelectedItemPosition() == 0) {
            ResultFragment fragment = new ResultFragment();
            Bundle args = new Bundle();
            args.putString("url", "http://rximage.nlm.nih.gov/api/rximage/1/rxnav?color="
                    +((ColorModel)colorSpin.getSelectedItem()).getName().toLowerCase());
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.tblSample, fragment,"result").addToBackStack(null).commit();

        } else if (colorSpin.getSelectedItemPosition() == 0) {
            ResultFragment fragment = new ResultFragment();
            Bundle args = new Bundle();
            args.putString("url", "http://rximage.nlm.nih.gov/api/rximage/1/rxnav?shape="
                    +shape);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.tblSample, fragment,"result").addToBackStack(null).commit();
        } else {
            ResultFragment fragment = new ResultFragment();
            Bundle args = new Bundle();
            args.putString("url", "http://rximage.nlm.nih.gov/api/rximage/1/rxnav?color="
                    +((ColorModel)colorSpin.getSelectedItem()).getName().toLowerCase()+
                    "&shape="
                    +shape);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.tblSample, fragment,"result").addToBackStack(null).commit();
        }

    }
}
