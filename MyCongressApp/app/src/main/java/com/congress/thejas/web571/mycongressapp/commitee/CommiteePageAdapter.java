package com.congress.thejas.web571.mycongressapp.commitee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.congress.thejas.web571.mycongressapp.FragmentByCommittee;
import com.congress.thejas.web571.mycongressapp.FragmentByState;

import org.json.JSONArray;

/**
 * Created by Thejas on 11/20/2016.
 */

public class CommiteePageAdapter extends FragmentPagerAdapter {

    Context context;
    private int pageCount = 3;
    String[] tabTitles = new String[]{"House", "Senate","Joint"};
    String [] values = null;
    String house = null;
    String senate = null;
    String joint = null;
    public CommiteePageAdapter(FragmentManager fm) {
        super(fm);


    }
    public CommiteePageAdapter(FragmentManager fm, JSONArray joint, JSONArray house, JSONArray senate) {
        super(fm);
        String[] str = new String[]{"sss","bbb","thejas"};
        this.values = str;
        this.joint = joint.toString();
        this.house = house.toString();
        this.senate = senate.toString();
    }
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();



        switch (position){
            case 0 : {   Fragment fr = new FragmentByCommittee();
                bundle.putString("result",house);
                bundle.putString("type","house");
                fr.setArguments(bundle);
                return fr ;}
            case 1 : {   Fragment fr = new FragmentByCommittee();

                bundle.putString("result",senate);
                bundle.putString("type","senate");
                fr.setArguments(bundle);
                return fr ;}
            case 2 : {   Fragment fr = new FragmentByCommittee();

                bundle.putString("result",joint);
                bundle.putString("type","joint");
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
