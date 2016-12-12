package com.congress.thejas.web571.mycongressapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.congress.thejas.web571.mycongressapp.favorites.Model;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorPageAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class FragmentLegislator extends android.support.v4.app.Fragment{

    String legislatorUrl = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=legislator";
    static Boolean isDataLoaded = false;
    static JSONArray legisItems = null;
    static JSONArray legisHouse = new JSONArray();
    static  JSONArray legisSenate = new JSONArray();

    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LegislatorItems legislatorItems = new LegislatorItems();
        legislatorItems.execute(legislatorUrl);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.legislator_fragment,container,false);

        tabLayout = (TabLayout) view.findViewById(R.id.legis_tab);
        viewPager = (ViewPager) view.findViewById(R.id.legis_view);

       /*final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.legis_tab);
       final ViewPager viewPager = (ViewPager) view.findViewById(R.id.legis_view);
        LegislatorPageAdapter legislatorPageAdapter = new LegislatorPageAdapter(getChildFragmentManager(),str);
        viewPager.setAdapter(legislatorPageAdapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });*/


        return view;
    }

    class LegislatorItems extends AsyncTask<String,Void,String> {

        String responseStr = "";
        JSONArray responseJson = null;


        @Override
        protected void onPostExecute(String value) {

                System.out.println(legisItems.toString());


            LegislatorPageAdapter legislatorPageAdapter = new LegislatorPageAdapter(getChildFragmentManager(),legisItems,legisHouse,legisSenate);
            viewPager.setAdapter(legislatorPageAdapter);
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

                if(!isDataLoaded) {

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
                    legisItems = new JSONObject(responseStr).getJSONArray("results");
                    //Gson gn = new Gson();
                    //Type listType = new TypeToken<List<LegislatorItem>>(){}.getType();
                    //List<LegislatorItem> cList = (List<LegislatorItem>) gn.fromJson(legisItems.toString(), listType);
                    Model model = Model.getInstance();
                    model.setLegislator(responseStr);
                    for(int i = 0 ;i<legisItems.length();i++){
                        JSONObject temp = legisItems.getJSONObject(i);
                        if(temp.getString("chamber").equalsIgnoreCase("house")){
                            legisHouse.put(temp);
                        }else{
                            legisSenate.put(temp);
                        }

                    }
                    isDataLoaded=true;
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
