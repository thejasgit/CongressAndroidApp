package com.congress.thejas.web571.mycongressapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LegislatorDetails extends AppCompatActivity {

    Boolean favflag = false;
    String type;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_details);
        Gson gs = new Gson();
        SharedPreferences sharedPref = getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
        Set<String> favLegislator = sharedPref.getStringSet("legislator", null);
        type  = this.getIntent().getExtras().getString("type");
        position = this.getIntent().getExtras().getInt("pos");
        final LegislatorItem item = gs.fromJson(this.getIntent().getExtras().getString("item"),LegislatorItem.class);
        ImageView facebook = (ImageView) findViewById(R.id.facebook_img);
        ImageView twitter = (ImageView) findViewById(R.id.twitter_img);
        ImageView website = (ImageView) findViewById(R.id.website_img);
        ImageView dp = (ImageView) findViewById(R.id.profile_dp);
        TextView partyName = (TextView) findViewById(R.id.party_name);
        ImageView partyLogo = (ImageView) findViewById(R.id.party_logo);
        final ImageView fav = (ImageView) findViewById(R.id.fav_img);
        //check if its in favrouites
        if(favLegislator!=null && favLegislator.contains(item.getBioguide_id())){
            favflag = true;
            fav.setImageResource(R.mipmap.favstar);
        }

        Picasso.with(this)
                .load("https://theunitedstates.io/images/congress/225x275/"+item.getBioguide_id()+".jpg")
                .resize(150, 150)
                .centerCrop()
                .into(dp);



        if(item.getParty().equalsIgnoreCase("R")){
            partyName.setText("Republican");
            partyLogo.setImageResource(R.mipmap.r);
        }else{
            partyName.setText("Democrate");
            partyLogo.setImageResource(R.mipmap.d);
        }





            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(item.getFacebook_id())) {
                        String message = "Legislator does not have Facebook Page" ;
                        Toast.makeText(LegislatorDetails.this,message,Toast.LENGTH_SHORT).show();
                    }else{
                    String url = "https://www.facebook.com/"+item.getFacebook_id();
                    openLink(url);

                }
                }
            });




            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(item.getTwitter_id())) {
                        String message = "Legislator does not have Twitter Page";
                        Toast.makeText(LegislatorDetails.this, message, Toast.LENGTH_SHORT).show();
                    }else{
                    String url = "https://www.twitter.com/"+item.getTwitter_id();
                    openLink(url);

                }}
            });



            website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(item.getWebsite())) {
                        String message = "Legislator does not have Website.";
                        Toast.makeText(LegislatorDetails.this, message, Toast.LENGTH_SHORT).show();
                    }else {
                        String url = item.getWebsite();
                        openLink(url);
                    }
                }
            });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPref = getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
                Set<String> favLegislator = sharedPref.getStringSet("legislator", null);
                SharedPreferences.Editor editor = sharedPref.edit();
                System.out.println(sharedPref.getStringSet("legislator", null));

                if(favLegislator ==null){
                    favLegislator = new HashSet<String>();
                }

                if(favflag){
                    String message = "Removed from favorites" ;
                    Toast.makeText(LegislatorDetails.this,message,Toast.LENGTH_SHORT).show();
                    fav.setImageResource(R.drawable.ic_star_favs);
                    favLegislator.remove(item.getBioguide_id());
                    editor.remove("legislator");
                    editor.apply();
                    editor.putStringSet("legislator", favLegislator);

                }
                else{
                    String message = "Added to favorites" ;
                    Toast.makeText(LegislatorDetails.this,message,Toast.LENGTH_SHORT).show();
                    fav.setImageResource(R.mipmap.favstar);
                   favLegislator.add(item.getBioguide_id());
                   editor.remove("legislator");
                    editor.apply();
                    editor.putStringSet("legislator", favLegislator);

                }

                favflag = !favflag;
                editor.commit();
                System.out.println(sharedPref.getStringSet("legislator", null));

            }
        });


        try {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.details_table_legislator);
        tableLayout.addView(getLegisatorRow("Name",item.getTitle()+". "+item.getLast_name()+", "+item.getFirst_name()));
        tableLayout.addView(getLegisatorRow("Email",item.getOc_email()));
        tableLayout.addView(getLegisatorRow("Chamber", StringUtils.capitalize(item.getChamber())));
        tableLayout.addView(getLegisatorRow("Contact",item.getPhone()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date newDate = null,start=null,end =null;
        newDate = format.parse(item.getTerm_start());
            start = format.parse(item.getTerm_start());
        SimpleDateFormat reqformat = new SimpleDateFormat("MMM dd,yyyy");
        String date = reqformat.format(newDate);
        tableLayout.addView(getLegisatorRow("Start Term",date));
            newDate = format.parse(item.getTerm_end());
            end =  format.parse(item.getTerm_end());
         date = reqformat.format(newDate);
            tableLayout.addView(getLegisatorRow("End Term",date));
            Date now = new Date();
            long diff = (now.getTime()-start.getTime())*100/(end.getTime()-start.getTime());



            tableLayout.addView(getLegisatorRow("Term", Long.toString(diff)));

           // tableLayout.addView();
            tableLayout.addView(getLegisatorRow("Office",item.getOffice()));
            tableLayout.addView(getLegisatorRow("State",item.getState()));
            tableLayout.addView(getLegisatorRow("Fax",item.getFax()));
            newDate = format.parse(item.getBirthday());
            date = reqformat.format(newDate);
            tableLayout.addView(getLegisatorRow("Birthday",date));





        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public TableRow getLegisatorRow(String name,String value){



        TableRow row = new TableRow(this);
        row.setPadding(0,0,0,70);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView label = new TextView(this);
        label.setTextColor(Color.BLACK);
        label.setTypeface(null, Typeface.BOLD);
        TextView val = new TextView(this);
        val.setGravity(Gravity.RIGHT);
        label.setText(name+":");
        row.addView(label, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        val.setText(TextUtils.isEmpty(value)?"N.A":value);


        if(name.equalsIgnoreCase("term")){

            RelativeLayout rl = (RelativeLayout) getLayoutInflater().inflate(
                    R.layout.custom_progress_bar,null);

            ProgressBar pbar =(ProgressBar) rl.findViewById(R.id.pb_horizontal);
            pbar.setProgress(Integer.valueOf(value));

            TextView tv = (TextView) rl.findViewById(R.id.progress_per);
            tv.setText(value+"%");
          /* ProgressBar pbar = new ProgressBar(this,
                    null,
                    android.R.attr.progressBarStyleHorizontal);

            pbar.setProgress(Integer.valueOf(value));*/
            row.addView(rl, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            return row;
        }
        row.addView(val, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        return row;
    }

    public void openLink(String url){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("type",type);
                intent.putExtra("pos",position);
                intent.putExtra("favflag",favflag);
                setResult(RESULT_OK, intent);
               finish();

                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("type",type);
        intent.putExtra("pos",position);
        intent.putExtra("favflag",favflag);
        setResult(RESULT_OK, intent);
        finish();
    }
}
