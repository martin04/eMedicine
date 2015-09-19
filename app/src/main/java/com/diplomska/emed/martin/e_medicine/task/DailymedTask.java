package com.diplomska.emed.martin.e_medicine.task;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.models.PillModel;
import com.diplomska.emed.martin.e_medicine.utils.EmedUtils;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Martin on 9/13/2015.
 */
public class DailymedTask extends AsyncTask<String, Void, List<Drug>> {

    private OnTaskCompleted listener;
    private Context ctx;
    private DrugAdapter drug;
    private boolean fromDB;


    public DailymedTask(Context ctx, OnTaskCompleted listener) {
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        drug = new DrugAdapter(ctx);
        listener.onTaskStarted();
    }

    @Override
    protected List<Drug> doInBackground(String... params) {
        //here we take first 20 drugs of the first page
        List<Drug> drugs = new ArrayList<Drug>();
        try {
            if (!EmedUtils.checkDB(ctx)) {
                drugs = jsonParser(EmedUtils.readJsonFeed(params[0]));
                drug.open();
                for (Drug d : drugs) {
                    drug.insert(d);
                }
                drug.close();
                return drugs;
            } else {
                drug.open();
                drugs = drug.getAllItems();
                drug.close();
                return drugs;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Drug> drugs) {
        if (drugs != null && drugs.size() > 0) {
            listener.onTaskCompleted(drugs);
        } else if (drugs != null && drugs.size() == 0) {
            listener.onTaskCompleted(drugs);
        } else {
            listener.onTaskNotCompleted();
        }
    }

    protected List<Drug> jsonParser(String json) {
        List<Drug> drugs = new ArrayList<>();
        try {
            JSONArray arr = new JSONObject(json).getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Drug d = new Drug();
                Random rnd = new Random();
                d.setCode(String.format("%d", rnd.nextInt(999999)));
                if (!obj.getString("drug_name").equalsIgnoreCase("-")) {
                    if (obj.getString("name_type").equalsIgnoreCase("G")) {
                        d.setGeneric_name(obj.getString("drug_name").replace("(", "").replace(")", "").replace("-", " ").replace(".", ""));
                        d.setLatin_name("");
                    } else {
                        d.setGeneric_name("");
                        d.setLatin_name(obj.getString("drug_name").replace("(", "").replace(")", "").replace("-", " ").replace(".", ""));
                    }
                } else {
                    d.setGeneric_name(ctx.getString(R.string.name_not_available));
                    d.setLatin_name(ctx.getString(R.string.name_not_available));
                }
                drugs.add(d);
            }
            return drugs;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
