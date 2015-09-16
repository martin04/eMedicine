package com.diplomska.emed.martin.e_medicine.task;

import android.content.Context;
import android.os.AsyncTask;
import android.system.Os;

import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;
import com.diplomska.emed.martin.e_medicine.utils.EmedUtils;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 06-Jul-15.
 */
public class SearchTask extends AsyncTask<String, Void, List<Drug>> {

    private OnTaskCompleted listener;
    private Context ctx;
    private DrugAdapter drug;

    public SearchTask(OnTaskCompleted listener, Context ctx) {
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        //drug=new DrugAdapter(ctx);
        listener.onTaskStarted();
    }

    @Override
    protected List<Drug> doInBackground(String... params) {
        try {
           /* drug.open();
            List<Drug> result=drug.getDrugByGenName(params[0]);
            drug.close();
            return result;*/
            String url="http://dailymed.nlm.nih.gov/dailymed/services/v2/drugnames.json?drug_name="+URLEncoder.encode(params[0],"UTF-8")+"&pagesize=50&page=1";
            return jsonParser(EmedUtils.readJsonFeed(url));
        }
        catch (Exception ex){
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
