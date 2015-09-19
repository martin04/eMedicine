package com.diplomska.emed.martin.e_medicine.task;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.diplomska.emed.martin.e_medicine.adapter.AdviseAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.ContraindicationAdapter;
import com.diplomska.emed.martin.e_medicine.adapter.DrugAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.CacheTaskHandler;
import com.diplomska.emed.martin.e_medicine.models.Advice;
import com.diplomska.emed.martin.e_medicine.models.Contraindication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin on 9/19/2015.
 */
public class CacheResultsTask extends AsyncTask<Void, Void, LinkedHashMap<String, List<String>>> {
    private Context ctx;
    private DrugAdapter drug;
    private ContraindicationAdapter contra;
    private AdviseAdapter advice;
    private CacheTaskHandler listener;
    private String code;
    private LinkedHashMap<String, List<String>> fda;
    private LinkedHashMap<String, String> drugDrug;

    public CacheResultsTask(Context ctx, CacheTaskHandler listener, String code,
                            LinkedHashMap<String, List<String>> fda, LinkedHashMap<String, String> drugDrug) {
        this.ctx = ctx;
        this.listener = listener;
        this.code = code;
        this.fda = fda;
        this.drugDrug = drugDrug;
    }

    @Override
    protected void onPreExecute() {
        drug = new DrugAdapter(ctx);
        contra = new ContraindicationAdapter(ctx);
        advice = new AdviseAdapter(ctx);
    }

    @Override
    protected LinkedHashMap<String, List<String>> doInBackground(Void... params) {
        LinkedHashMap<String, List<String>> tmp = new LinkedHashMap<String, List<String>>();
        try {
            contra.open();
            String s = contra.getContraByDrugCode(code);
            contra.close();

            advice.open();
            String a = advice.getAdviseByDrugCode(code);
            advice.close();

            if ((s == null || a == null) || (TextUtils.isEmpty(s) || TextUtils.isEmpty(s))) {

                String ddInter = "";
                for (Map.Entry<String, String> entry : drugDrug.entrySet()) {
                    ddInter += entry.getKey() + "-" + entry.getValue() + "XX";
                }
                contra.open();
                Contraindication c = new Contraindication();
                drug.open();
                c.setDrug(drug.getDrugByCode(code));
                drug.close();
                c.setContraindication(ddInter);
                contra.insert(c);
                contra.close();

                for (Map.Entry<String, List<String>> entry : fda.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("contraindications")) {
                        contra.open();
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            Contraindication cc = new Contraindication();
                            drug.open();
                            cc.setDrug(drug.getDrugByCode(code));
                            drug.close();
                            cc.setContraindication(entry.getValue().get(i));
                            contra.insert(cc);
                        }
                        contra.close();
                    } else {
                        advice.open();
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            Advice aa = new Advice();
                            drug.open();
                            aa.setDrug(drug.getDrugByCode(code));
                            drug.close();
                            aa.setAdvise(entry.getValue().get(i));
                            advice.insert(aa);
                        }
                        advice.close();
                    }
                }
                return tmp;
            } else {
                contra.open();
                String co = contra.getContraByDrugCode(code);
                contra.close();

                advice.open();
                String ad = advice.getAdviseByDrugCode(code);
                advice.close();

                String[] dd = co.split("\\.\\n");
                String[] ff = ad.split("\\.\\n");

                tmp.put("drug_drug_contra", Arrays.asList(dd[0]));
                List<String> contras = new ArrayList<String>();
                for (int i = 1; i < dd.length; i++) {
                    contras.add(dd[i]);
                }
                tmp.put("contraindications", contras);

                List<String> advices = new ArrayList<String>();
                for (int k = 0; k < ff.length; k++) {
                    advices.add(ff[k]);
                }
                tmp.put("advices", advices);

                return tmp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LinkedHashMap<String, List<String>> stringListLinkedHashMap) {
        if (stringListLinkedHashMap != null) {
            listener.onCacheResult(stringListLinkedHashMap);
        } else {
            listener.onCacheError();
        }
    }
}
