package com.congress.thejas.web571.mycongressapp.legislator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.congress.thejas.web571.mycongressapp.FragmentByState;

import org.json.JSONArray;

/**
 * Created by Thejas on 11/16/2016.
 */

public class LegislatorPageAdapter extends FragmentStatePagerAdapter {

    Context context;
    private int pageCount = 3;
    String[] tabTitles = new String[]{"By State", "House","Senate"};
    String [] values = null;
    String house = null;
    String senate = null;
    String result = null;
    public LegislatorPageAdapter(FragmentManager fm) {
        super(fm);


    }
    public LegislatorPageAdapter(FragmentManager fm,JSONArray results,JSONArray house,JSONArray senate) {
        super(fm);
        String[] str = new String[]{"sss","bbb","thejas"};
        this.values = str;
        this.result = results.toString();
        this.house = house.toString();
        this.senate = senate.toString();



        System.out.println("senate: "+senate.toString());

    }
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();



        switch (position){
            case 0 : {   Fragment fr = new FragmentByState();
                bundle.putString("result",result);
                bundle.putString("type","all");
                        fr.setArguments(bundle);
                        return fr ;}
            case 1 : {   Fragment fr = new FragmentByState();
                bundle.putString("result",house);
                bundle.putString("type","house");
                fr.setArguments(bundle);
                return fr ;}
            case 2 : {   Fragment fr = new FragmentByState();
                bundle.putString("result",senate);
                bundle.putString("type","senate");
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
