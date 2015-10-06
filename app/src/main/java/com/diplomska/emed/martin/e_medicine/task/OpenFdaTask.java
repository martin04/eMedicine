package com.diplomska.emed.martin.e_medicine.task;

import android.os.AsyncTask;

import com.diplomska.emed.martin.e_medicine.interfaces.OpenFDAHandler;
import com.diplomska.emed.martin.e_medicine.utils.EmedUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Martin on 9/13/2015.
 */
public class OpenFdaTask extends AsyncTask<String, Void, LinkedHashMap<String, List<String>>> {

    private OpenFDAHandler listener;

    public OpenFdaTask(OpenFDAHandler listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LinkedHashMap<String, List<String>> doInBackground(String... params) {
        try {
            String url = "https://api.fda.gov/drug/label.json?search=" + URLEncoder.encode(params[0], "UTF-8") + "&limit=2";
            return jsonParser(EmedUtils.readJsonFeed(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LinkedHashMap<String, List<String>> fda) {
        if (fda != null) {
            listener.openFdaResult(fda);
        } else {
            listener.openFdaError();
        }
    }

    protected LinkedHashMap<String, List<String>> jsonParser(String json) {
        LinkedHashMap<String, List<String>> fda = new LinkedHashMap<String, List<String>>();
        List<String> advices = new ArrayList<String>();
        List<String> contraindications = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(json.replace("\n", ""));

            int total = obj.getJSONObject("meta").getJSONObject("results").getInt("total");

            if (total == 0) {
                return fda;
            } else {
                JSONArray arr = obj.getJSONArray("results");

                String effectiveTime1 = arr.getJSONObject(0).getString("effective_time").substring(0, 4);
                String effectiveTime2 = arr.getJSONObject(1).getString("effective_time").substring(0, 4);

                JSONObject moreRecent = null;

                if (Integer.parseInt(effectiveTime1) > Integer.parseInt(effectiveTime2)) {
                    moreRecent = arr.getJSONObject(0);
                } else {
                    moreRecent = arr.getJSONObject(1);
                }

                //contraindications
                if(moreRecent.has("drug_interactions")) {
                    JSONArray drugInter = moreRecent.getJSONArray("drug_interactions");
                    contraindications.add(drugInter.getString(0));
                }else{
                    contraindications.add("");
                }
                if (moreRecent.has("contraindications")) {
                    JSONArray contraind = moreRecent.getJSONArray("contraindications");
                    contraindications.add(contraind.getString(0));
                }else{
                    contraindications.add("");
                }
                if(moreRecent.has("adverse_reactions")) {
                    JSONArray adverseReactions = moreRecent.getJSONArray("adverse_reactions");
                    contraindications.add(adverseReactions.getString(0));
                }else{
                    contraindications.add("");
                }
                if(moreRecent.has("overdosage")) {
                    JSONArray adverseReactions = moreRecent.getJSONArray("overdosage");
                    contraindications.add(adverseReactions.getString(0));
                }else{
                    contraindications.add("");
                }

                fda.put("contraindications", contraindications);

                //advices
                if(moreRecent.has("precautions")){
                    JSONArray precautions = moreRecent.getJSONArray("precautions");
                    advices.add(precautions.getString(0));
                }else{
                    advices.add("");
                }if(moreRecent.has("warnings")){
                    JSONArray warnings = moreRecent.getJSONArray("warnings");
                    advices.add(warnings.getString(0));
                }else{
                    advices.add("");
                }if(moreRecent.has("general_precautions")){
                    JSONArray generalPrecautions = moreRecent.getJSONArray("general_precautions");
                    advices.add(generalPrecautions.getString(0));
                }else{
                    advices.add("");
                }if(moreRecent.has("indications_and_usage")){
                    JSONArray generalPrecautions = moreRecent.getJSONArray("indications_and_usage");
                    advices.add(generalPrecautions.getString(0));
                }else{
                    advices.add("");
                }

                fda.put("advices", advices);

                return fda;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
