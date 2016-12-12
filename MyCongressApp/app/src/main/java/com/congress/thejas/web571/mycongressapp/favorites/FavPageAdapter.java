package com.congress.thejas.web571.mycongressapp.favorites;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.congress.thejas.web571.mycongressapp.FragmentByBill;
import com.congress.thejas.web571.mycongressapp.FragmentByCommittee;
import com.congress.thejas.web571.mycongressapp.FragmentByState;

import org.json.JSONArray;

/**
 * Created by Thejas on 11/21/2016.
 */

public class FavPageAdapter extends FragmentStatePagerAdapter {

    Context context;
    private int pageCount = 3;
    String[] tabTitles = new String[]{"Legislators", "Bills","Committees"};

    String Legislators = null;
    String Bills = null;
    String Committees = null;
    public FavPageAdapter(FragmentManager fm) {
        super(fm);


    }
    public FavPageAdapter(FragmentManager fm, JSONArray Legislators, JSONArray Bills, JSONArray Committees) {
        super(fm);

        this.Legislators = Legislators.toString();
        this.Bills = Bills.toString();
        this.Committees = Committees.toString();
    }
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();



        switch (position){
            case 0 : {   Fragment fr = new FragmentByState();
                bundle.putString("result",Legislators);
                bundle.putString("type","fav");
                fr.setArguments(bundle);
                System.out.println("BYSTATE:1 " +Legislators);
                return fr ;}
            case 1 : {   Fragment fr = new FragmentByBill();

                bundle.putString("result",Bills);
                bundle.putString("type","fav");
                fr.setArguments(bundle);
                return fr ;}
            case 2 : {   Fragment fr = new FragmentByCommittee();

                bundle.putString("result",Committees);
                bundle.putString("type","fav");
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
