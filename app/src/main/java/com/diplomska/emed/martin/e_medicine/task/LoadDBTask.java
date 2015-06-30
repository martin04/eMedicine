package com.diplomska.emed.martin.e_medicine.task;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.diplomska.emed.martin.e_medicine.adapter.AdviseAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.ContraindicationAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.OnTaskCompleted;
import com.diplomska.emed.martin.e_medicine.models.Advise;
import com.diplomska.emed.martin.e_medicine.models.Contraindication;
import com.diplomska.emed.martin.e_medicine.models.Drug;

import java.io.File;
import java.util.List;


/**
 * Created by Martin on 19-Jun-15.
 */
public class LoadDBTask extends AsyncTask<Void, Void, List<Drug>> {
    private DrugAdapter drug;
    private ContraindicationAdapter contra;
    private AdviseAdapter advise;
    private Context ctx;
    private OnTaskCompleted listener;

    public LoadDBTask(Context ctx, OnTaskCompleted listener) {
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        drug = new DrugAdapter(ctx);
        contra = new ContraindicationAdapter(ctx);
        advise = new AdviseAdapter(ctx);
    }

    @Override
    protected List<Drug> doInBackground(Void... params) {
        if (!checkDB()) {
            Drug d1 = new Drug("DB001", "Paracetamol", "paracetamol");
            Drug d2 = new Drug("DB002", "Vitamin C", "ascorbic acid");
            Drug d3 = new Drug("DB003", "Brufen", "ibuprofen");
            Drug d4 = new Drug("DB004", "Diazepam", "diazepam");

            Contraindication c1 = new Contraindication(d1, "Hypersensitivity to paracetamol or any constituent component of the drug");
            Contraindication c2 = new Contraindication(d2, "Hypersensitivity to any ingredient of the drug");
            Contraindication c31 = new Contraindication(d3, "Patients with active peptic ulcer");
            Contraindication c32 = new Contraindication(d3, "Patients in pregnancy and lactation");
            Contraindication c33 = new Contraindication(d3, "Patients with known hypersensitivity to NSAIDs");
            Contraindication c41 = new Contraindication(d4, "Known hypersensitivity to benzodiazepines and any other ingredient of the drug");
            Contraindication c42 = new Contraindication(d4, "Phobic or obsessional states; chronic psychoses (may appear paradoxical reactions)");
            Contraindication c43 = new Contraindication(d4, "Acute pulmonary insufficiency; respiratory depression (can worsen ventilatory failure)");
            Contraindication c44 = new Contraindication(d4, "Myasthenia gravis (condition may deteriorate)");
            Contraindication c45 = new Contraindication(d4, "\"Sleep apnea\" syndrome (condition may deteriorate)");
            Contraindication c46 = new Contraindication(d4, "Severe hepatic insufficiency (elimination half-life of diazepam can be continued)");
            Contraindication c47 = new Contraindication(d4, "Acute porphyria");
            Contraindication c48 = new Contraindication(d4, "Diazepam should not be used as monotherapy in patients with depression or those with anxiety and depression, because in such patients can cause suicide");

            Advise a11 = new Advise(d1, "You should drink warm tea");
            Advise a12 = new Advise(d1, "Drink lot of juice");
            Advise a2 = new Advise(d2, "Consider drinking tea, juice, yoghurt, water");
            Advise a31 = new Advise(d3, "Consider taking after a meal");
            Advise a32 = new Advise(d3, "Drink lots of fluids (water, juice, etc)");
            Advise a41 = new Advise(d4, "Avoid driving");
            Advise a42 = new Advise(d4, "Do not drink alcohol");

            try {
                drug.open();
                drug.insert(d1);
                drug.insert(d2);
                drug.insert(d3);
                drug.insert(d4);
                drug.close();

                contra.open();
                contra.insert(c1);

                contra.insert(c2);

                contra.insert(c31);
                contra.insert(c32);
                contra.insert(c33);

                contra.insert(c41);
                contra.insert(c42);
                contra.insert(c43);
                contra.insert(c44);
                contra.insert(c45);
                contra.insert(c46);
                contra.insert(c47);
                contra.insert(c48);
                contra.close();

                advise.open();
                advise.insert(a11);
                advise.insert(a12);

                advise.insert(a2);

                advise.insert(a31);
                advise.insert(a32);

                advise.insert(a41);
                advise.insert(a42);
                advise.close();

                drug.open();
                List<Drug> pom = drug.getAllItems();
                drug.close();
                return pom;
            } catch (Exception ex) {
                ex.printStackTrace();
                listener.onTaskNotCompleted();
                return null;
            }
        } else {
            drug.open();
            List<Drug> pom = drug.getAllItems();
            drug.close();
            return pom;
        }
    }

    @Override
    protected void onPostExecute(List<Drug> drugs) {
            listener.onTaskCompleted(drugs);
    }

    //Function for checking if the database exists
    private boolean checkDB() {
        File db = ctx.getApplicationContext().getDatabasePath("emedicine.db");
        if (!db.exists()) {
            return false;
        } else {
            return true;
        }
    }
}
