package com.congress.thejas.web571.mycongressapp;

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
import com.congress.thejas.web571.mycongressapp.favorites.Model;
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

public class FragmentBills extends Fragment{

    TabLayout tabLayout;
    ViewPager viewPager;
    static Boolean isBillLoaded = false;

    static JSONArray activeBills = new JSONArray();
    static JSONArray newBills = new JSONArray();
    String billsURL = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=bills";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BillAsync billAsync = new BillAsync();
        billAsync.execute(billsURL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bills_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.bills_tab);
        viewPager = (ViewPager) view.findViewById(R.id.bills_view);

        return view;
    }


    class BillAsync extends AsyncTask<String,Void,String> {

        String responseStr = "";
        JSONArray responseJson = null;


        @Override
        protected void onPostExecute(String value) {

            System.out.println("active: "+activeBills.toString());

            System.out.println("new: "+newBills.toString());
           BillsPageAdapter billsPageAdapter = new BillsPageAdapter(getChildFragmentManager(),activeBills,newBills);
            viewPager.setAdapter(billsPageAdapter);
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

                if(!isBillLoaded) {

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
                    //Gson gn = new Gson();
                    //Type listType = new TypeToken<List<BillItem>>(){}.getType();
                    //List<BillItem> cList = (List<BillItem>) gn.fromJson(temp.toString(), listType);
                    Model model = Model.getInstance();
                    model.setBills(responseStr);
                    for(int i = 0 ;i<temp.length();i++){
                        JSONObject history = temp.getJSONObject(i).getJSONObject("history");

                        if(history.getBoolean("active")){
                            activeBills.put(temp.getJSONObject(i));
                        }else{
                            newBills.put(temp.getJSONObject(i));
                        }

                    }
                    isBillLoaded=true;
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
