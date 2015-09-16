package com.diplomska.emed.martin.e_medicine.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.diplomska.emed.martin.e_medicine.interfaces.RxNormHandler;
import com.diplomska.emed.martin.e_medicine.models.PillModel;
import com.diplomska.emed.martin.e_medicine.utils.EmedUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 9/16/2015.
 */
public class RxNormTask extends AsyncTask<String, Void, List<String>> {

    private RxNormHandler listener;

    public RxNormTask(RxNormHandler listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onRxNormStarted();
    }

    @Override
    protected List<String> doInBackground(String... params) {
        try {
            String url = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name=" + URLEncoder.encode(params[0], "UTF-8");
            return jsonParser(EmedUtils.readJsonFeed(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<String> s) {
        if (s != null) {
            listener.onRxNormResult(s);
        } else {
            listener.onRxNormError();
        }
    }

    protected List<String> jsonParser(String json) {
        List<String> rxcuis = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json).getJSONObject("idGroup");
            JSONArray arr = obj.getJSONArray("rxnormId");
            for (int i = 0; i < arr.length(); i++) {
                rxcuis.add(arr.getString(i));
            }
            return rxcuis;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
