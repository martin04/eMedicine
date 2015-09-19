package com.diplomska.emed.martin.e_medicine.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.diplomska.emed.martin.e_medicine.fragments.AdvicesFragment;
import com.diplomska.emed.martin.e_medicine.fragments.AltNamesFragment;
import com.diplomska.emed.martin.e_medicine.fragments.ContraindicationsFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Martin on 9/19/2015.
 */
public class DrugNoCacheViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> names;
    private int numOfTabs;
    private List<String> contra;
    private List<String> adv;
    private String[] altNames;
    private LinkedHashMap<String, String> drugDrug;

    public DrugNoCacheViewPagerAdapter(FragmentManager fm, ArrayList<String> names, int numOfTabs,
                                LinkedHashMap<String, String> drugDrug, List<String> contra, List<String> adv, String[] altNames) {
        super(fm);
        this.names = names;
        this.numOfTabs = numOfTabs;
        this.drugDrug = drugDrug;
        this.contra = contra;
        this.adv = adv;
        this.altNames = altNames;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle b = new Bundle();
                String[] tmpC = new String[contra.size()];
                b.putStringArray("contraindications", contra.toArray(tmpC));
                b.putString("drug_drug_interactions",formatDrugInteractions(drugDrug));
                ContraindicationsFragment cf = new ContraindicationsFragment();
                cf.setArguments(b);
                return cf;
            case 1:
                Bundle ba = new Bundle();
                String[] tmpA = new String[3];
                ba.putStringArray("advices", adv.toArray(tmpA));
                AdvicesFragment af = new AdvicesFragment();
                af.setArguments(ba);
                return af;

            case 2:
                Bundle anf = new Bundle();
                anf.putStringArray("alt_names", altNames);
                AltNamesFragment rf = new AltNamesFragment();
                rf.setArguments(anf);
                return rf;
        }
        return new ContraindicationsFragment();
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    private String formatDrugInteractions(LinkedHashMap<String,String> drugs){
        String formatted="";
        for(Map.Entry<String,String> entry : drugs.entrySet()){
            formatted+=entry.getKey()+" - "+entry.getValue()+"\n\n";
        }
        return formatted;
    }
}
