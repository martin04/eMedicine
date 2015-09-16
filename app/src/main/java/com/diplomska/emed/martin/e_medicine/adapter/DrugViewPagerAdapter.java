package com.diplomska.emed.martin.e_medicine.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.diplomska.emed.martin.e_medicine.fragments.AdvicesFragment;
import com.diplomska.emed.martin.e_medicine.fragments.ContraindicationsFragment;
import com.diplomska.emed.martin.e_medicine.fragments.AltNamesFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 9/5/2015.
 */
public class DrugViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> names;
    private int numOfTabs;
    private String[] contra;
    private String[] adv;
    private String[] altNames;
    private HashMap<String, String> drugDrug;

    public DrugViewPagerAdapter(FragmentManager fm, ArrayList<String> names, int numOfTabs, HashMap<String, String> drugDrug, String[] contra, String[] adv, String[] altNames) {
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
                //b.putStringArray("contraindications", contra);
                b.putString("drug_drug_interactions",formatDrugInteractions(drugDrug));
                ContraindicationsFragment cf = new ContraindicationsFragment();
                cf.setArguments(b);
                return cf;
            case 1:
                Bundle ba = new Bundle();
                ba.putStringArray("advices", adv);
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

    private String formatDrugInteractions(HashMap<String,String> drugs){
        String formatted="";
        for(Map.Entry<String,String> entry : drugs.entrySet()){
            formatted+=entry.getKey()+" - "+entry.getValue()+"\n\n";
        }
        return formatted;
    }
}
