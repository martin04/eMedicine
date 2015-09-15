package com.diplomska.emed.martin.e_medicine.task;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Martin on 9/16/2015.
 */
public class InteractionsApiTask extends AsyncTask<String,Void,List<String>>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
    }
}
