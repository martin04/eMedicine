package com.diplomska.emed.martin.e_medicine.task;

import android.content.Context;
import android.os.AsyncTask;

import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Drug;

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
        drug=new DrugAdapter(ctx);
    }

    @Override
    protected List<Drug> doInBackground(String... params) {
        try {
            drug.open();
            List<Drug> result=drug.getDrugByGenName(params[0]);
            drug.close();
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            listener.onTaskNotCompleted();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Drug> drugs) {
        listener.onTaskCompleted(drugs);
    }
}
