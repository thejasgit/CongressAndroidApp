package com.congress.thejas.web571.mycongressapp.legislator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.congress.thejas.web571.mycongressapp.R;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Thejas on 11/17/2016.
 */

public class LegislatorRowAdapter extends ArrayAdapter<LegislatorItem> {
    public LegislatorRowAdapter(Context context, List<LegislatorItem> values) {
        super(context, R.layout.legislator_row_layout,values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.legislator_row_layout,parent,false);
        LegislatorItem item = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.legislator_name);
        textView.setText(item.getLast_name()+','+item.getFirst_name());
        TextView state = (TextView) view.findViewById(R.id.legislator_state);
        state.setText("("+item.getParty()+")"+item.getState_name()+"- District "+ (TextUtils.isEmpty(item.getDistrict())?"0":item.getDistrict()));
        ImageView imageView = (ImageView) view.findViewById(R.id.legislator_dp);
       // imageView.setImageResource(R.mipmap.visa);
        Picasso.with(getContext())
                .load("https://theunitedstates.io/images/congress/225x275/"+item.getBioguide_id()+".jpg")
                .resize(150, 150)
                .centerCrop()
                .into(imageView);


        return view;


    }
}
