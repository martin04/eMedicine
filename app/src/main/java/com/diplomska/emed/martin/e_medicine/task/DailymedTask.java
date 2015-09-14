package com.diplomska.emed.martin.e_medicine.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.models.PillModel;

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

/**
 * Created by Martin on 9/13/2015.
 */
public class DailymedTask extends AsyncTask<String, Void, List<Drug>> {

    private OnTaskCompleted listener;


    public DailymedTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onTaskStarted();
    }

    @Override
    protected List<Drug> doInBackground(String... params) {
        //here we take first 20 drugs of the first page
        try {
            return jsonParser(readJsonFeed(params[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Drug> drugs) {
        if (drugs != null && drugs.size() > 0) {
            listener.onTaskCompleted(drugs);
        }else if(drugs != null && drugs.size()==0){
            listener.onTaskCompleted(drugs);
        }else{
            listener.onTaskNotCompleted();
        }
    }

    protected String readJsonFeed(String urlLink) {
        try {
            URL url = new URL(urlLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // read the response
            System.out.println("Response Code: " + conn.getResponseCode());
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return IOUtils.toString(in, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected List<Drug> jsonParser(String json) {
        List<Drug> drugs = new ArrayList<>();
        try {
            JSONArray arr = new JSONObject(json).getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Drug d = new Drug();
                d.setCode("");
                if(!obj.getString("drug_name").equalsIgnoreCase("-")) {
                    if (obj.getString("name_type").equalsIgnoreCase("G")) {
                        d.setGeneric_name(obj.getString("drug_name").replace("(", "").replace(")", "").replace("-", " "));
                        d.setLatin_name("");
                    } else {
                        d.setGeneric_name("");
                        d.setLatin_name(obj.getString("drug_name").replace("(", "").replace(")", "").replace("-", " "));
                    }
                }else{
                    d.setGeneric_name("Name not available");
                    d.setLatin_name("Name not available");
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
