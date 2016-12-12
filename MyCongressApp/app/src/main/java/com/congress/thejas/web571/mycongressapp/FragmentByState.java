package com.congress.thejas.web571.mycongressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorRowAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Thejas on 11/16/2016.
 */

public class FragmentByState extends Fragment {

    ListView listView;
    ListAdapter listAdapter=null;
    List<LegislatorItem> legisList;
    Map<String, Integer> mapIndex;
    LinearLayout indexLayout;
    LayoutInflater currentInflater;



    private void getIndexList(List<LegislatorItem> items) {
        mapIndex = new LinkedHashMap<String, Integer>();


        for (int i = 0; i < items.size(); i++) {
            LegislatorItem temp = items.get(i);
            String index=null;
            if(StringUtils.equalsIgnoreCase(getArguments().getString("type"),"all"))
            index =  temp.getState().substring(0, 1);
            else
            index = temp.getLast_name().substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

       // super.onActivityResult(requestCode,resultCode,data);

        if(StringUtils.equalsIgnoreCase(data.getStringExtra("type"),"fav")) {
            System.out.println("inside result callback");
            /*SharedPreferences sharedPref = getActivity().getSharedPreferences("CONGRESS_APP", MODE_PRIVATE);
            Set<String> set = sharedPref.getStringSet("legislator", null);
            List<LegislatorItem> temp1 = new ArrayList<LegislatorItem>();
            for (int i = 0; i < legisList.size(); i++) {
                if (set.contains(legisList.get(i).getBioguide_id())) {
                    temp1.add(legisList.get(i));
                }
            }
            listAdapter = new LegislatorRowAdapter(getActivity(), temp1);
            listView.setAdapter(listAdapter);
            getIndexList(temp1);
            displayIndex();*/
            if(!data.getBooleanExtra("favflag",true)) {
                legisList.remove(data.getIntExtra("pos", 0));
                listAdapter = new LegislatorRowAdapter(getActivity(), legisList);
                listView.setAdapter(listAdapter);
                getIndexList(legisList);
                displayIndex();
            }

        }


    }


    private void displayIndex(){
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());

        indexLayout.removeAllViews();
        for (String index : indexList) {
            textView = (TextView) currentInflater.inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            //textView.setOnClickListener(this);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView selectedIndex = (TextView) view;
                    listView.setSelection(mapIndex.get(selectedIndex.getText()));

                    Toast.makeText(getActivity(),selectedIndex.getText(),Toast.LENGTH_SHORT).show();
                }
            });
            indexLayout.addView(textView);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bystate_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.list_bystate);
        currentInflater = inflater;

        String values  = getArguments().getString("result");
        final String type = getArguments().getString("type");
        Gson gn = new Gson();
            System.out.println("BYSTATE:" +values);
        Type listType = new TypeToken<List<LegislatorItem>>(){}.getType();
        legisList = (List<LegislatorItem>) gn.fromJson(values, listType);
        //sort
        Collections.sort(legisList, new Comparator<LegislatorItem>() {

            public int compare(LegislatorItem o1, LegislatorItem o2) {

                int val;
                if(type=="all")
                    val= o1.getState().compareToIgnoreCase(o2.getState());
                else val=0;
                if(val==0){
                    return o1.getLast_name().compareToIgnoreCase(o2.getLast_name());
                }
                return val;
            }
        });


         indexLayout = (LinearLayout) view.findViewById(R.id.side_index);


        getIndexList(legisList);
        displayIndex();

        /*SharedPreferences sharedPref = getActivity().getSharedPreferences("CONGRESS_APP",MODE_PRIVATE);
        Set<String> set = sharedPref.getStringSet("legislator", null);
        List<LegislatorItem> temp1 = new ArrayList<LegislatorItem>();
        for(int i=0;i<legisList.size();i++){
            if(set.contains(legisList.get(i).getBioguide_id())){
                temp1.add(legisList.get(i));
            }
        }

        if(StringUtils.equalsIgnoreCase(type,"fav")){
            legisList = temp1;
        }*/


        Collections.sort(legisList, new Comparator<LegislatorItem>() {

            public int compare(LegislatorItem o1, LegislatorItem o2) {

                int val;
                if(type=="all")
                val= o1.getState().compareToIgnoreCase(o2.getState());
                else val=0;
                if(val==0){
                    return o1.getLast_name().compareToIgnoreCase(o2.getLast_name());
                }
                return val;
            }
        });



        listAdapter = new LegislatorRowAdapter(getActivity(),legisList);


        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    LegislatorItem item = (LegislatorItem) parent.getItemAtPosition(position);
               // String message = "You Clicked "+ String.valueOf(item.getLast_name());

               // Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LegislatorDetails.class);
                Gson gs = new Gson();
                String itemStr = gs.toJson(item);
                intent.putExtra("item",itemStr);
                intent.putExtra("type",type);
                intent.putExtra("pos",position);
                //startActivity(intent);
               startActivityForResult(intent,0);

               // startActivity(getActivity().getIntent());
              // listView.invalidateViews();
                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.detach().attach().commit();

                  // ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

            }
        });


        return view;
    }
}
