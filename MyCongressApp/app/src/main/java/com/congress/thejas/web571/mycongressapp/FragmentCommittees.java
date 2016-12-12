package com.congress.thejas.web571.mycongressapp;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.congress.thejas.web571.mycongressapp.commitee.CommiteePageAdapter;
import com.congress.thejas.web571.mycongressapp.commitee.CommitteeItem;
import com.congress.thejas.web571.mycongressapp.favorites.Model;
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
import java.net.URL;
import java.util.List;

/**
 * Created by Thejas on 11/16/2016.
 */

public class FragmentCommittees extends android.support.v4.app.Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    static Boolean isCommitteeLoaded = false;
    static JSONArray hosuseCom = new JSONArray();
    static JSONArray senateCom = new JSONArray();
    static JSONArray jointCom = new JSONArray();
    String committeesURL = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=committee";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommitteeAsync committeeAsync = new CommitteeAsync();
        committeeAsync.execute(committeesURL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.committees_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.committee_tab);
        viewPager = (ViewPager) view.findViewById(R.id.committee_view);

        return view;
    }


    class CommitteeAsync extends AsyncTask<String,Void,String> {

        String responseStr = "";
        JSONArray responseJson = null;


        @Override
        protected void onPostExecute(String value) {

            System.out.println("house: "+hosuseCom.toString());

            System.out.println("senate: "+senateCom.toString());
            System.out.println("joint: "+jointCom.toString());
            CommiteePageAdapter commiteePageAdapter = new CommiteePageAdapter(getChildFragmentManager(),jointCom,hosuseCom,senateCom);
            viewPager.setAdapter(commiteePageAdapter);
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                }
            });


        }

        @Override
        protected String doInBackground(String... params) {

            try {

                if(!isCommitteeLoaded) {

                    System.out.println("Loading Data for first Time");
                    URL url = new URL(params[0]);
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
                    responseStr = sb.toString();
                    JSONArray temp = new JSONObject(responseStr).getJSONArray("results");
                   // Gson gn = new Gson();
                    //Type listType = new TypeToken<List<CommitteeItem>>(){}.getType();
                    //List<CommitteeItem> cList = (List<CommitteeItem>) gn.fromJson(temp.toString(), listType);

                    for(int i = 0 ;i<temp.length();i++){


                        if(StringUtils.equalsIgnoreCase(temp.getJSONObject(i).getString("chamber"),"senate")){
                            senateCom.put(temp.getJSONObject(i));
                        }else if(StringUtils.equalsIgnoreCase(temp.getJSONObject(i).getString("chamber"),"house")){
                            hosuseCom.put(temp.getJSONObject(i));
                        }else{
                            jointCom.put(temp.getJSONObject(i));
                        }

                    }
                    isCommitteeLoaded=true;
                }else{

                    //Map favorites
                    System.out.println("using Loaded Data");

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return responseStr;
        }
    }


}
