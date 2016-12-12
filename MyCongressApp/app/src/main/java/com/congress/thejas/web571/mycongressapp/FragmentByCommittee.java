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

import com.congress.thejas.web571.mycongressapp.commitee.CommiteeRowAdapter;
import com.congress.thejas.web571.mycongressapp.commitee.CommitteeItem;
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

public class FragmentByCommittee extends Fragment {

    ListView listView;
    List<CommitteeItem> comList;
    ListAdapter listAdapter;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(StringUtils.equalsIgnoreCase(data.getStringExtra("type"),"fav")) {
           /* System.out.println("inside result callback");
            SharedPreferences sharedPref = getActivity().getSharedPreferences("CONGRESS_APP", MODE_PRIVATE);
            Set<String> set = sharedPref.getStringSet("committee", null);
            List<CommitteeItem> temp1 = new ArrayList<CommitteeItem>();
            for (int i = 0; i < comList.size(); i++) {
                if (set.contains(comList.get(i).getCommittee_id())) {
                    temp1.add(comList.get(i));
                }
            }
            listAdapter = new CommiteeRowAdapter(getActivity(), temp1);
            listView.setAdapter(listAdapter);*/

            if(!data.getBooleanExtra("favflag",true)) {
                comList.remove(data.getIntExtra("pos", 0));
                listAdapter = new CommiteeRowAdapter(getActivity(), comList);
                listView.setAdapter(listAdapter);

            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.committee_display_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.list_committee);

        String values  = getArguments().getString("result");
        final String type = getArguments().getString("type");
        Gson gn = new Gson();

        Type listType = new TypeToken<List<CommitteeItem>>(){}.getType();
        comList = (List<CommitteeItem>) gn.fromJson(values, listType);


        Collections.sort(comList, new Comparator<CommitteeItem>() {

            public int compare(CommitteeItem o1, CommitteeItem o2) {


               return o1.getName().compareToIgnoreCase(o2.getName());

            }
        });



         listAdapter = new CommiteeRowAdapter(getActivity(),comList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CommitteeItem item = (CommitteeItem) parent.getItemAtPosition(position);
                // String message = "You Clicked "+ String.valueOf(item.getLast_name());

                // Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CommitteeDetails.class);
                Gson gs = new Gson();
                String itemStr = gs.toJson(item);
                intent.putExtra("item",itemStr);
                intent.putExtra("type",type);
                intent.putExtra("pos",position);
                //startActivity(intent);
                startActivityForResult(intent,0);
            }
        });


        return view;
    }
}
