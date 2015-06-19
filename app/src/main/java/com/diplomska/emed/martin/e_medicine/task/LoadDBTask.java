package com.diplomska.emed.martin.e_medicine.task;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.diplomska.emed.martin.e_medicine.adapter.AdviseAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.ContraindicationAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.models.Advise;
import com.diplomska.emed.martin.e_medicine.models.Contraindication;
import com.diplomska.emed.martin.e_medicine.models.Drug;


/**
 * Created by Martin on 19-Jun-15.
 */
public class LoadDBTask extends AsyncTask<Void, Void, Boolean> {
    private DrugAdapter drug;
    private ContraindicationAdapter contra;
    private AdviseAdapter advise;
    private Context ctx;

    public LoadDBTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        drug = new DrugAdapter(ctx);
        contra = new ContraindicationAdapter(ctx);
        advise = new AdviseAdapter(ctx);
        Toast.makeText(ctx, "Preparing to load database...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Drug d1 = new Drug("DB001", "Paracetamol", "paracetamol");
        Drug d2 = new Drug("DB002", "Vitamin C", "ascorbic acid");
        Drug d3 = new Drug("DB003", "Brufen", "ibuprofen");
        Drug d4 = new Drug("DB004", "Diazepam", "diazepam");
        try {
            drug.open();
            drug.insert(d1);
            drug.insert(d2);
            drug.insert(d3);
            drug.insert(d4);
            drug.close();

            contra.open();
            contra.insert(new Contraindication(d1, "Hypersensitivity to paracetamol or any constituent component of the drug"));

            contra.insert(new Contraindication(d2, "Hypersensitivity to any ingredient of the drug"));

            contra.insert(new Contraindication(d3, "Patients with active peptic ulcer"));
            contra.insert(new Contraindication(d3, "Patients in pregnancy and lactation"));
            contra.insert(new Contraindication(d3, "Patients with known hypersensitivity to NSAIDs"));

            contra.insert(new Contraindication(d4, "Known hypersensitivity to benzodiazepines and any other ingredient of the drug"));
            contra.insert(new Contraindication(d4, "Phobic or obsessional states; chronic psychoses (may appear paradoxical reactions)"));
            contra.insert(new Contraindication(d4, "Acute pulmonary insufficiency; respiratory depression (can worsen ventilatory failure)"));
            contra.insert(new Contraindication(d4, "Myasthenia gravis (condition may deteriorate)"));
            contra.insert(new Contraindication(d4, "\"Sleep apnea\" syndrome (condition may deteriorate)"));
            contra.insert(new Contraindication(d4, "Severe hepatic insufficiency (elimination half-life of diazepam can be continued)"));
            contra.insert(new Contraindication(d4, "Acute porphyria"));
            contra.insert(new Contraindication(d4, "Diazepam should not be used as monotherapy in patients with depression or those with anxiety and depression, because in such patients can cause suicide"));
            contra.close();

            advise.open();
            advise.insert(new Advise(d1, "You should drink warm tea"));
            advise.insert(new Advise(d1, "Drink lot of juice"));

            advise.insert(new Advise(d2, "Consider drinking tea, juice, yoghurt, water"));

            advise.insert(new Advise(d3, "Consider taking after a meal"));
            advise.insert(new Advise(d3, "Drink lots of fluids (water, juice, etc)"));

            advise.insert(new Advise(d4, "Avoid driving"));
            advise.insert(new Advise(d4, "Do not drink alcohol"));
            advise.close();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean == true) {
            Toast.makeText(ctx, "DB Successfully loaded!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "Oops there's sth wrong !", Toast.LENGTH_SHORT).show();
        }
    }
}
