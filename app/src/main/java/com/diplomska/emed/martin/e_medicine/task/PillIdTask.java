package com.diplomska.emed.martin.e_medicine.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.diplomska.emed.martin.e_medicine.interfaces.onPillIdTaskHandler;
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

/**
 * Created by Martin on 8/28/2015.
 */
public class PillIdTask extends AsyncTask<String, Void, List<PillModel>> {

    private onPillIdTaskHandler listener;

    public PillIdTask(onPillIdTaskHandler listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onPillIdStarted();
    }

    @Override
    protected List<PillModel> doInBackground(String... params) {
        try {
            return jsonParser(EmedUtils.readJsonFeed(params[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<PillModel> pillModels) {
        if (pillModels != null) {
            if (pillModels.size() == 0) {
                listener.onPillIdNoResults();
            } else {
                listener.onPillIdResult(pillModels);
            }
        } else {
            listener.onPillIdError("An error occurred, please try again.");
        }
    }

    protected List<PillModel> jsonParser(String json) {
        List<PillModel> pills = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json).getJSONObject("replyStatus");
            if (obj.getBoolean("success")) {
                if (obj.getInt("totalImageCount") != 0) {
                    JSONArray arr = new JSONObject(json).getJSONArray("nlmRxImages");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject info = arr.getJSONObject(i);
                        PillModel pill = new PillModel();
                        pill.setRxcui(info.getInt("rxcui"));
                        pill.setName(TextUtils.isEmpty(info.getString("name")) ? "Unknown" : info.getString("name"));
                        pill.setImgUrl(info.getString("imageUrl"));
                        pills.add(pill);
                    }
                    return pills;
                } else {
//                    listener.onPillIdNoResults();
                    return pills;
                }
            } else {
                listener.onPillIdError(obj.getString("errorMsg"));
                return pills;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
