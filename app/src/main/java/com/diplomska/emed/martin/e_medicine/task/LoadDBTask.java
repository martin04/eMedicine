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
            Drug d5 = new Drug("DB005", "Losartan", "losartan");
            Drug d6 = new Drug("DB006", "Loratadine", "loratadine");
            Drug d7 = new Drug("DB007", "Carvedilol", "carvedilol");
            Drug d8 = new Drug("DB008", "Amikacin", "amikacin");
            Drug d9=new Drug("DB009","Metformin","metformin");

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
            Contraindication c51 = new Contraindication(d5, "Hypersensitivity to losartan kalilum or any of the excipients of the drug");
            Contraindication c52 = new Contraindication(d5, "Second and third trimester of pregnancy");
            Contraindication c53 = new Contraindication(d5, "Severe impairment of hepatic function");
            Contraindication c6 = new Contraindication(d6, "Hypersensitivity to loratadine or any of the components of the drug");
            Contraindication c71 = new Contraindication(d7, "Hypersensitivity to carvedilol or constituent components of the drug");
            Contraindication c72 = new Contraindication(d7, "Class IV heart decompensation, asthma, chronic obstructive pulmonary disease with bronchospastic component clinically manifested liver disease, bradycardia (<50), cardiac shock, cor pulmonale, pulmonary hypertension, sinus syndrome PEC, AV block of 2 or 3 degrees, hypotension");
            Contraindication c8=new Contraindication(d8,"Known hypersensitivity to the drug. History of hypersensitivity or serious toxic reactions to aminoglycosides may contraindicated the use of any other aminoglycoside because of the known cross-sensitivity of patients to drugs of this class");
            Contraindication c91=new Contraindication(d9,"Hypersensitivity to metformin hydrochloride or to any of the excipients of the drug");
            Contraindication c92=new Contraindication(d9,"Diabetic ketoacidosis, diabetic pre-coma");
            Contraindication c93=new Contraindication(d9,"Renal failure or renal dysfunction");
            Contraindication c94=new Contraindication(d9,"Acute conditions that may affect renal function for eg: dehydration, severe infection, shock, intravascular administration of iodinated contrast agents");
            Contraindication c95=new Contraindication(d9,"Acute or chronic disease which may cause tissue hypoxia for eg cardiac or respiratory failure, recent myocardial infarction, shock");
            Contraindication c96=new Contraindication(d9,"Hepatic insufficiency, acute alcohol intoxication, alcoholism");

            Advise a11 = new Advise(d1, "You should drink warm tea");
            Advise a12 = new Advise(d1, "Drink lot of juice");
            Advise a2 = new Advise(d2, "Consider drinking tea, juice, yoghurt, water");
            Advise a31 = new Advise(d3, "Consider taking after a meal");
            Advise a32 = new Advise(d3, "Drink lots of fluids (water, juice, etc)");
            Advise a41 = new Advise(d4, "Avoid driving");
            Advise a42 = new Advise(d4, "Do not drink alcohol");
            Advise a51 = new Advise(d5, "Drinking alcohol can further lower your blood pressure and may increase certain side effects of losartan");
            Advise a52 = new Advise(d5, "Do not use potassium supplements or salt substitutes while you are taking this medicine, unless your doctor has told you to");
            Advise a53 = new Advise(d5, "Avoid getting up too fast from a sitting or lying position, or you may feel dizzy. Get up slowly and steady yourself to prevent a fall");
            Advise a6 = new Advise(d6, "Follow your doctor's instructions about any restrictions on food, beverages, or activity");
            Advise a71 = new Advise(d7, "Carvedilol works best if you take it with food");
            Advise a72 = new Advise(d7, "Take carvedilol at the same time every day. Do not skip doses or stop taking carvedilol without first talking to your doctor. Stopping suddenly may make your condition worse");
            Advise a73 = new Advise(d7, "You may open the carvedilol capsule and sprinkle the medicine into a spoonful of pudding or applesauce to make swallowing easier. Swallow right away without chewing. Do not save the mixture for later use");
            Advise a81=new Advise(d8,"Drink plenty of liquids while you are taking amikacin. This will help keep your kidneys working properly");
            Advise a82=new Advise(d8,"While using amikacin, you may need frequent blood or urine tests. Your hearing, kidney function, and nerve function may also need to be checked");
            Advise a83=new Advise(d8,"If you need surgery, tell the surgeon ahead of time that you are using amikacin");
            Advise a9=new Advise(d9,"Take metformin with a meal, unless your doctor tells you otherwise");

            try {
                drug.open();
                drug.insert(d1);
                drug.insert(d2);
                drug.insert(d3);
                drug.insert(d4);
                drug.insert(d5);
                drug.insert(d6);
                drug.insert(d7);
                drug.insert(d8);
                drug.insert(d9);
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

                contra.insert(c51);
                contra.insert(c52);
                contra.insert(c53);

                contra.insert(c6);

                contra.insert(c71);
                contra.insert(c72);

                contra.insert(c8);

                contra.insert(c91);
                contra.insert(c92);
                contra.insert(c93);
                contra.insert(c94);
                contra.insert(c95);
                contra.insert(c96);
                contra.close();

                advise.open();
                advise.insert(a11);
                advise.insert(a12);

                advise.insert(a2);

                advise.insert(a31);
                advise.insert(a32);

                advise.insert(a41);
                advise.insert(a42);

                advise.insert(a51);
                advise.insert(a52);
                advise.insert(a53);

                advise.insert(a6);

                advise.insert(a71);
                advise.insert(a72);
                advise.insert(a73);

                advise.insert(a81);
                advise.insert(a82);
                advise.insert(a83);

                advise.insert(a9);
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
