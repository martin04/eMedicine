package com.diplomska.emed.martin.e_medicine.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.diplomska.emed.martin.e_medicine.fragments.AdvicesFragment;
import com.diplomska.emed.martin.e_medicine.fragments.ContraindicationsFragment;
import com.diplomska.emed.martin.e_medicine.fragments.RemindersFragment;

import java.util.ArrayList;

/**
 * Created by Martin on 9/5/2015.
 */
public class DrugViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> names;
    private int numOfTabs;
    private String[] contra;
    private String[] adv;

    public DrugViewPagerAdapter(FragmentManager fm, ArrayList<String> names, int numOfTabs, String[] contra, String[] adv) {
        super(fm);
        this.names = names;
        this.numOfTabs = numOfTabs;
        this.contra = contra;
        this.adv = adv;
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
                b.putStringArray("contraindications", contra);
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
                RemindersFragment rf = new RemindersFragment();
                return new RemindersFragment();
        }
        return new ContraindicationsFragment();
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
