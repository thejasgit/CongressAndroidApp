package com.congress.thejas.web571.mycongressapp.commitee;

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

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Thejas on 11/21/2016.
 */

public class CommiteeRowAdapter extends ArrayAdapter<CommitteeItem> {
    public CommiteeRowAdapter(Context context, List<CommitteeItem> values) {
        super(context, R.layout.legislator_row_layout,values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.comittee_row_fragment,parent,false);
        CommitteeItem item = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.committee_id);
        textView.setText(item.getCommittee_id());
        TextView name = (TextView) view.findViewById(R.id.committee_name);
        name.setText(item.getName());
        TextView cham = (TextView) view.findViewById(R.id.committee_chamber);
        cham.setText(StringUtils.capitalize(item.getChamber()));


        return view;


    }
}
