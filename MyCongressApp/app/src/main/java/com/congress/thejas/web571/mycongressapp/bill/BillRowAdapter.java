package com.congress.thejas.web571.mycongressapp.bill;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.congress.thejas.web571.mycongressapp.R;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Thejas on 11/20/2016.
 */

public class BillRowAdapter extends ArrayAdapter<BillItem> {
    public BillRowAdapter(Context context, List<BillItem> values) {
        super(context, R.layout.legislator_row_layout,values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.bill_row_layout, parent, false);

        try {

            BillItem item = getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.bill_id);
            textView.setText(StringUtils.upperCase(item.getBill_id()));
            TextView title = (TextView) view.findViewById(R.id.bill_title);
            title.setText(TextUtils.isEmpty(item.getShort_title()) ? item.getOfficial_title() : item.getShort_title());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            Date newDate = null, start = null, end = null;
            newDate = format.parse(item.getIntroduced_on());
            SimpleDateFormat reqformat = new SimpleDateFormat("MMM dd,yyyy");
            String date = reqformat.format(newDate);
            TextView dateon = (TextView) view.findViewById(R.id.bill_date);
            dateon.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;


    }
}
