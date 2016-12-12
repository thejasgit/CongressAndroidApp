package com.congress.thejas.web571.mycongressapp.bill;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.congress.thejas.web571.mycongressapp.FragmentByBill;

import org.json.JSONArray;

/**
 * Created by Thejas on 11/20/2016.
 */

public class BillsPageAdapter extends FragmentPagerAdapter {

    Context context;
    private int pageCount = 2;
    String[] tabTitles = new String[]{"Active Bills","New Bills"};
    String [] values = null;
    String activebill = null;
    String newbill = null;

    public BillsPageAdapter(FragmentManager fm) {
        super(fm);

    }
    public BillsPageAdapter(FragmentManager fm, JSONArray activebill, JSONArray newbill) {
        super(fm);
        String[] str = new String[]{"sss","bbb","thejas"};
        this.values = str;

        this.activebill = activebill.toString();
        this.newbill = newbill.toString();


    }
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();



        switch (position){
            case 0 : {   Fragment fr = new FragmentByBill();
                bundle.putString("result",activebill);
                bundle.putString("type","active");
                fr.setArguments(bundle);
                return fr ;}
            case 1 : {   Fragment fr = new FragmentByBill();
                bundle.putString("result",newbill);
                bundle.putString("type","new");
                fr.setArguments(bundle);
                return fr ;}

        }

        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
