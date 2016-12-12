package com.congress.thejas.web571.mycongressapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.congress.thejas.web571.mycongressapp.favorites.Model;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("main----");
        super.onActivityResult(requestCode, resultCode, data);

    }

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

   String committeesURL = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=committee";
    String legislatorUrl = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=legislator";
    String billsURL = "http://congressresponsive.r2pjzawyau.us-west-2.elasticbeanstalk.com/congressService.php?query=bills";
    String committees = null;
    String legislators = null;
    String bills = null;
    static Boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setTitle("Legislators");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content_frame,new FragmentLegislator()).commit();

        CongressImpl congressImpl1 =  new CongressImpl();
        CongressImpl congressImpl2 =  new CongressImpl();
        CongressImpl congressImpl3 =  new CongressImpl();

       congressImpl1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"legislators",legislatorUrl);
        congressImpl2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"committees",committeesURL);
        congressImpl3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"bills",billsURL);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_legislators) {

            setTitle("Legislators");
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,new FragmentLegislator()).commit();

        } else if (id == R.id.nav_bills) {
            setTitle("Bills");
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,new FragmentBills()).commit();
          //fm.beginTransaction().replace(R.id.content_frame,new FragmentBills()).commit();

        } else if (id == R.id.nav_committees) {
            setTitle("Committees");
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,new FragmentCommittees()).commit();
           //fm.beginTransaction().replace(R.id.content_frame,new FragmentCommittees()).commit();

        } else if (id == R.id.nav_favs) {
            setTitle("Favorites");
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,new FragmentFavs()).commit();
           //fm.beginTransaction().replace(R.id.content_frame,new FragmentFavs()).commit();

        } else if (id == R.id.nav_about) {

            Intent intent = new Intent(this, AboutMe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




   class CongressImpl extends AsyncTask<String,Void,String>{

        String responseStr = "";
        JSONArray responseJson = null;


        @Override
        protected void onPostExecute(String value) {


                Model model = Model.getInstance();

                if (StringUtils.equalsIgnoreCase(value, "legislators")) {
                    model.setLegislator(legislators);

                } else if (StringUtils.equalsIgnoreCase(value, "committees")) {
                    model.setCommittee(committees);


                } else {

                    model.setBills(bills);
                }



          /*  String[] val1 = new String[]{"1","2","3"};
            String[] val2 = new String[]{"4","2","3"};
            String[] val3 = new String[]{"3","4","31"};
            ListView listView = null;
            ListAdapter listAdapter = null;



            if(value=="legislators"){

             //legislators = responseJson;
            listView  = (ListView)findViewById(R.id.list_house);
            listAdapter= new LegislatorRowAdapter(MainActivity.this,val1);

            }else if(value=="bills"){
               // bills = responseJson;
                listView  = (ListView)findViewById(R.id.list_bystate);
                listAdapter= new LegislatorRowAdapter(MainActivity.this,val2);
            }else if(value=="committees"){
                System.out.println(value);
                //committees = responseJson;
                listView  = (ListView)findViewById(R.id.list_house);
                listAdapter= new LegislatorRowAdapter(MainActivity.this,val3);
            }

            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String message = "You Clicked "+ String.valueOf(parent.getItemAtPosition(position));

                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            });*/





        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[1]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");

                con.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line = null;

                // readLine reads all characters up to a \n and then stores them
                while((line = reader.readLine()) != null){

                    sb.append(line + "\n");

                }

                // Save the results in a String
                responseStr = sb.toString();
                //JSONObject resp = new JSONObject(responseStr);
                if(params[0]=="committees"){
                    System.out.println("committees: "+responseStr);
                    committees = responseStr;
                    return "committees";

                }else if(params[0]=="legislators"){

                    System.out.println("legislators: "+responseStr);
                    legislators =  responseStr;
                    return "legislators";

                }else if(params[0]=="bills"){
                    System.out.println("bills: "+responseStr);
                    bills =  responseStr;
                    return "bills";


                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
