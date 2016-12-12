package com.congress.thejas.web571.mycongressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.congress.thejas.web571.mycongressapp.bill.BillItem;
import com.congress.thejas.web571.mycongressapp.commitee.CommitteeItem;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CommitteeDetails extends AppCompatActivity {

    Boolean favflag = false;
    String type;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_details);
        Gson gs = new Gson();
        final CommitteeItem item = gs.fromJson(this.getIntent().getExtras().getString("item"),CommitteeItem.class);
        type = this.getIntent().getStringExtra("type");
        position = this.getIntent().getExtras().getInt("pos");
        SharedPreferences sharedPref = getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
        Set<String> favCommittees = sharedPref.getStringSet("committee", null);

            TableLayout tableLayout = (TableLayout) findViewById(R.id.details_table_committee);
            tableLayout.addView(getCommitteeRow("Committee ID", StringUtils.upperCase(item.getCommittee_id())));
            tableLayout.addView(getCommitteeRow("Name", item.getName()));
        tableLayout.addView(getCommitteeRow("Chamber", StringUtils.capitalize(item.getChamber())));
        tableLayout.addView(getCommitteeRow("Parent Committee", StringUtils.upperCase(item.getParent_committee_id())));
            tableLayout.addView(getCommitteeRow("Contact", item.getPhone()));
            tableLayout.addView(getCommitteeRow("Office", item.getOffice()));
            final ImageView fav = (ImageView) findViewById(R.id.fav_img);

        //check if its in favrouites
        if(favCommittees!=null && favCommittees.contains(item.getCommittee_id())){
            favflag = true;
            fav.setImageResource(R.mipmap.favstar);
        }


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPref = getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
                Set<String> favCommittees = sharedPref.getStringSet("committee", null);
                SharedPreferences.Editor editor = sharedPref.edit();
                System.out.println(sharedPref.getStringSet("committee", null));

                if(favCommittees ==null){
                    favCommittees = new HashSet<String>();
                }

                if(favflag){
                    String message = "Removed from favorites" ;
                    Toast.makeText(CommitteeDetails.this,message,Toast.LENGTH_SHORT).show();
                    fav.setImageResource(R.drawable.ic_star_favs);
                    favCommittees.remove(item.getCommittee_id());
                    editor.remove("committee");
                    editor.apply();
                    editor.putStringSet("committee", favCommittees);

                }
                else{
                    String message = "Added to favorites" ;
                    Toast.makeText(CommitteeDetails.this,message,Toast.LENGTH_SHORT).show();
                    fav.setImageResource(R.mipmap.favstar);
                    favCommittees.add(item.getCommittee_id());
                    editor.remove("committee");
                    editor.apply();
                    editor.putStringSet("committee", favCommittees);

                }

                favflag = !favflag;
                editor.commit();
                System.out.println(sharedPref.getStringSet("committee", null));

            }
        });



    }




    public TableRow getCommitteeRow(String name, String value){

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
        if(StringUtils.equalsIgnoreCase(name,"chamber")){

            ImageView im= new ImageView(this);
            if(StringUtils.equalsIgnoreCase(value,"house")){
                im.setImageResource(R.mipmap.h);
            }else{
                im.setImageResource(R.mipmap.s);
            }

            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setGravity(Gravity.RIGHT);
           im.setLayoutParams(new TableRow.LayoutParams(100, 100));
            im.setPadding(0,0,5,0);
            l.addView(im);
            l.addView(val);

            row.addView(l,new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            return row;

        }

        row.addView(val, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        return row;
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
