package com.congress.thejas.web571.mycongressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.congress.thejas.web571.mycongressapp.bill.BillItem;
import com.congress.thejas.web571.mycongressapp.bill.BillRowAdapter;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorRowAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Thejas on 11/20/2016.
 */

public class FragmentByBill extends Fragment {

    ListView listView;
    List<BillItem> billList;
    ListAdapter listAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(StringUtils.equalsIgnoreCase(data.getStringExtra("type"),"fav")) {
            /*System.out.println("inside result callback");
            SharedPreferences sharedPref = getActivity().getSharedPreferences("CONGRESS_APP", MODE_PRIVATE);
            Set<String> set = sharedPref.getStringSet("bills", null);
            List<BillItem> temp1 = new ArrayList<BillItem>();
            for (int i = 0; i < billList.size(); i++) {
                if (set.contains(billList.get(i).getBill_id())) {
                    temp1.add(billList.get(i));
                }
            }
            listAdapter = new BillRowAdapter(getActivity(), temp1);
            listView.setAdapter(listAdapter);*/
            if(!data.getBooleanExtra("favflag",true)) {
                billList.remove(data.getIntExtra("pos", 0));
                listAdapter = new BillRowAdapter(getActivity(), billList);
                listView.setAdapter(listAdapter);

            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bill_display_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.list_bills);

        String values  = getArguments().getString("result");
        final String type = getArguments().getString("type");
        Gson gn = new Gson();

        Type listType = new TypeToken<List<BillItem>>(){}.getType();
        billList = (List<BillItem>) gn.fromJson(values, listType);
        System.out.println("length: "+billList.size());

        Collections.sort(billList, new Comparator<BillItem>() {

            public int compare(BillItem o2, BillItem o1) {

                return o1.getIntroduced_on().compareToIgnoreCase(o2.getIntroduced_on());


            }
        });


        listAdapter = new BillRowAdapter(getActivity(),billList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              BillItem item = (BillItem) parent.getItemAtPosition(position);
               //  String message = "You Clicked "+ String.valueOf(item.getBill_id());

                //Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getActivity(), BillDetails.class);
                Gson gs = new Gson();
                String itemStr = gs.toJson(item);
                intent.putExtra("item",itemStr);
                intent.putExtra("type",type);
                intent.putExtra("pos",position);
                //startActivity(intent);
                startActivityForResult(intent,0);
                //startActivity(intent);
            }
        });


        return view;
    }
}
