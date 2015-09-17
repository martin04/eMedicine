package com.diplomska.emed.martin.e_medicine.task;

import android.os.AsyncTask;

import com.diplomska.emed.martin.e_medicine.interfaces.InteractionsHandler;
import com.diplomska.emed.martin.e_medicine.utils.EmedUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Martin on 9/16/2015.
 */
public class InteractionsApiTask extends AsyncTask<String, Void, LinkedHashMap<String, String>> {

    private InteractionsHandler listener;

    public InteractionsApiTask(InteractionsHandler listener) {
        this.listener = listener;
    }


    @Override
    protected LinkedHashMap<String, String> doInBackground(String... params) {
        try {
            String url = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=" + params[0];
            return jsonParser(EmedUtils.readJsonFeed(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(LinkedHashMap<String, String> drugs) {
        if(drugs!=null){
            listener.onInteractionsResult(drugs);
        }else{
            listener.onInteractionsError();
        }
    }

    protected LinkedHashMap<String, String> jsonParser(String json) {
        LinkedHashMap<String, String> drugs = new LinkedHashMap<String, String>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arrType = obj.getJSONArray("interactionTypeGroup");
            for (int i = 0; i < arrType.length(); i++) {
                JSONObject interarctions = arrType.getJSONObject(i);
                JSONArray drugDrug = interarctions.getJSONArray("interactionType");
                for (int j = 0; j < drugDrug.length(); j++) {
                    JSONObject pair = drugDrug.getJSONObject(j);
                    JSONArray pairs = pair.getJSONArray("interactionPair");
                    for (int k = 0; k < pairs.length(); k++) {
                        JSONObject drugPair = pairs.getJSONObject(k);
                        String description = drugPair.getString("description");
                        JSONArray drugPairs = drugPair.getJSONArray("interactionConcept");
                        JSONObject info = drugPairs.getJSONObject(1);
                        drugs.put(info.getJSONObject("sourceConceptItem").getString("name"), description);
                    }
                }

            }
            return drugs;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
