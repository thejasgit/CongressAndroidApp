package com.congress.thejas.web571.mycongressapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.congress.thejas.web571.mycongressapp.bill.BillItem;
import com.congress.thejas.web571.mycongressapp.bill.BillsPageAdapter;
import com.congress.thejas.web571.mycongressapp.commitee.CommiteePageAdapter;
import com.congress.thejas.web571.mycongressapp.commitee.CommitteeItem;
import com.congress.thejas.web571.mycongressapp.favorites.FavPageAdapter;
import com.congress.thejas.web571.mycongressapp.favorites.Model;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Thejas on 11/16/2016.
 */

public class FragmentFavs extends android.support.v4.app.Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    static Boolean isLoaded = false;
    static List<LegislatorItem> les = new ArrayList<LegislatorItem>();
    static List<BillItem> bil = new ArrayList<BillItem>();
    static List<CommitteeItem> com = new ArrayList<CommitteeItem>();
    String committeesURL = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=committee";
    String legislatorUrl = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=legislator";
    String billsURL = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=bills";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FavAsync favAsync = new FavAsync();
        //FavAsync favAsync2 = new FavAsync();
        //FavAsync favAsync3 = new FavAsync();
        favAsync.execute(legislatorUrl,committeesURL,billsURL);
       // favAsync2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"committee",legislatorUrl);
        //favAsync3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"bills",billsURL);

           }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("inside main Fragment --------- ********");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fav_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.fav_tab);
        viewPager = (ViewPager) view.findViewById(R.id.fav_view);

        Model model = Model.getInstance();

        System.out.println("model: "+model.getBills());
        System.out.println("model: "+model.getCommittee());
        System.out.println("model: "+model.getLegislator());


       /* FavPageAdapter favPageAdapter = new FavPageAdapter(getChildFragmentManager(),,hosuseCom,senateCom);
        viewPager.setAdapter(favPageAdapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
*/
        return view;
    }


    class FavAsync extends AsyncTask<String,Void,String> {

        String responseStr = "";
        JSONArray responseJson = null;


        @Override
        protected void onPostExecute(String value) {

            String lesStr=null,comStr=null,bilStr=null;

            SharedPreferences sharedPref = getActivity().getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
            Set<String> set = sharedPref.getStringSet("legislator", null);
            List<LegislatorItem> temp1 = new ArrayList<LegislatorItem>();
            if(set!=null)
            for(int i=0;i<les.size();i++){
                if(set.contains(les.get(i).getBioguide_id())){
                    temp1.add(les.get(i));
                }
            }
           set = sharedPref.getStringSet("committee", null);
            List<CommitteeItem> temp2 = new ArrayList<CommitteeItem>();
            if(set!=null)
            for(int i=0;i<com.size();i++){
                if(set.contains(com.get(i).getCommittee_id())){
                    temp2.add(com.get(i));
                }
            }
            set = sharedPref.getStringSet("bills", null);
            List<BillItem> temp3 = new ArrayList<BillItem>();
            if(set!=null)
            for(int i=0;i<bil.size();i++){
                if(set.contains(bil.get(i).getBill_id())){
                    temp3.add(bil.get(i));
                }
            }


            Gson gson = new Gson();
            Type listType = new TypeToken<List<LegislatorItem>>() {}.getType();
            lesStr = gson.toJson(temp1, listType);
             listType = new TypeToken<List<BillItem>>() {}.getType();
            bilStr = gson.toJson(temp3, listType);
             listType = new TypeToken<List<CommitteeItem>>() {}.getType();
            comStr = gson.toJson(temp2, listType);
            System.out.println("L: "+lesStr);
            System.out.println("B: "+bilStr);
            System.out.println("C: "+comStr);
            FavPageAdapter favPageAdapter = null;
            try {
                favPageAdapter = new FavPageAdapter(getChildFragmentManager(),
                      new JSONArray(lesStr),new JSONArray(bilStr),new JSONArray(comStr));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            viewPager.setOffscreenPageLimit(1);
            viewPager.setAdapter(favPageAdapter);
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                }
            });


        }

        String getResponse(String link ){


            try {
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                con.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line = null;

                // readLine reads all characters up to a \n and then stores them
                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                // Save the results in a String
                return sb.toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return  null;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                if(!isLoaded) {

                    System.out.println("Loading Data for first Time");
                    /*URL url = new URL(params[1]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    con.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();

                    String line = null;

                    // readLine reads all characters up to a \n and then stores them
                    while ((line = reader.readLine()) != null) {

                        sb.append(line + "\n");

                    }*/

                    // Save the results in a String
                    Model model = Model.getInstance();
                   // responseStr = getResponse(params[0]);
                    JSONArray temp = new JSONObject(model.getLegislator()).getJSONArray("results");
                    Gson gn = new Gson();

                        Type listType = new TypeToken<List<LegislatorItem>>() {
                        }.getType();
                    les = (List<LegislatorItem>) gn.fromJson(temp.toString(), listType);

                  //  responseStr = getResponse(params[1]);
                    temp = new JSONObject(model.getCommittee()).getJSONArray("results");
                        listType = new TypeToken<List<CommitteeItem>>() {
                        }.getType();
                        com = (List<CommitteeItem>) gn.fromJson(temp.toString(), listType);

                   // responseStr = getResponse(params[2]);
                    temp = new JSONObject(model.getBills()).getJSONArray("results");
                        listType = new TypeToken<List<BillItem>>() {
                        }.getType();
                        bil = (List<BillItem>) gn.fromJson(temp.toString(), listType);

                    isLoaded=true;
                }else{

                    //Map favorites
                    System.out.println("using Loaded Data");

                }

            }  catch (JSONException e) {
                e.printStackTrace();
            }


            return params[0];
        }
    }


}