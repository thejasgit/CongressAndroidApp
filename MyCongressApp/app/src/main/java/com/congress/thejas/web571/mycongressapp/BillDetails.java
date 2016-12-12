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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.congress.thejas.web571.mycongressapp.bill.BillItem;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BillDetails extends AppCompatActivity {

    Boolean favflag = false;
    String type;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        Gson gs = new Gson();
        final BillItem item = gs.fromJson(this.getIntent().getExtras().getString("item"),BillItem.class);
        type = this.getIntent().getStringExtra("type");
        position = this.getIntent().getExtras().getInt("pos");
        SharedPreferences sharedPref = getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
        Set<String> favBills = sharedPref.getStringSet("bills", null);

        try {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.details_table_bills);
            tableLayout.addView(getBillRow("Bill ID", StringUtils.upperCase(item.getBill_id())));
            tableLayout.addView(getBillRow("Title",TextUtils.isEmpty(item.getShort_title())? item.getOfficial_title() : item.getShort_title()));
            tableLayout.addView(getBillRow("Bill Type", StringUtils.upperCase(item.getBill_type())));
            tableLayout.addView(getBillRow("Sponsor", item.getSponsor().getTitle()+", "+item.getSponsor().getLast_name()+", "+item.getSponsor().getFirst_name()));
            tableLayout.addView(getBillRow("Chamber", StringUtils.capitalize(item.getChamber())));
            tableLayout.addView(getBillRow("Status", item.getHistory().getActive()? "Active":"New"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            Date newDate = null;
            newDate = format.parse(item.getIntroduced_on());
            SimpleDateFormat reqformat = new SimpleDateFormat("MMM dd,yyyy");
            String date = reqformat.format(newDate);
            tableLayout.addView(getBillRow("Introduced On",date));
            tableLayout.addView(getBillRow("Congress URL",item.getUrls().getCongress()));
            tableLayout.addView(getBillRow("Version Status",item.getLast_version().getVersion_name()));
            tableLayout.addView(getBillRow("Bill URL",TextUtils.isEmpty(item.getLast_version().getUrls().getPdf())? "N.A": item.getLast_version().getUrls().getPdf()));
            final ImageView fav = (ImageView) findViewById(R.id.fav_img);
            //check if its in favrouites
            if(favBills!=null && favBills.contains(item.getBill_id())){
                favflag = true;
                fav.setImageResource(R.mipmap.favstar);
            }


            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    SharedPreferences sharedPref = getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
                    Set<String> favBills = sharedPref.getStringSet("bills", null);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    System.out.println(sharedPref.getStringSet("bills", null));

                    if(favBills ==null){
                        favBills = new HashSet<String>();
                    }

                    if(favflag){
                        String message = "Removed from favorites" ;
                        Toast.makeText(BillDetails.this,message,Toast.LENGTH_SHORT).show();
                        fav.setImageResource(R.drawable.ic_star_favs);
                        favBills.remove(item.getBill_id());
                        editor.remove("bills");
                        editor.apply();
                        editor.putStringSet("bills", favBills);

                    }
                    else{
                        String message = "Added to favorites" ;
                        Toast.makeText(BillDetails.this,message,Toast.LENGTH_SHORT).show();
                        fav.setImageResource(R.mipmap.favstar);
                        favBills.add(item.getBill_id());
                        editor.remove("bills");
                        editor.apply();
                        editor.putStringSet("bills", favBills);

                    }

                    favflag = !favflag;
                    editor.commit();
                    System.out.println(sharedPref.getStringSet("bills", null));

                }
            });



        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




    public TableRow getBillRow(String name, String value){



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
