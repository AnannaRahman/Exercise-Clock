package com.lifestyle.application.exerciseclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lifestyle.application.exerciseclock.R;
import com.lifestyle.application.exerciseclock.model.WorkOutPlan;

import java.util.ArrayList;


/**
 * Created by Ananna on 10/25/2017.
 */

public class HistoryItemAdapter extends BaseAdapter {
    Context c;
    ArrayList<WorkOutPlan> arrayList;

    public HistoryItemAdapter(Context c, ArrayList<WorkOutPlan> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    public void clearAll() {
         arrayList.clear();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_historyitem,null);

        TextView tvSets = (TextView) customView.findViewById(R.id.tvSets);
        TextView tvWODuration = (TextView)customView.findViewById(R.id.tvWODuration);
        TextView tvRestInterval = (TextView)customView.findViewById(R.id.tvRestInterval);

        tvSets.setText(String.valueOf(arrayList.get(position).getSets()));
        tvWODuration.setText(String.valueOf(arrayList.get(position).getCode1()));
        tvRestInterval.setText(String.valueOf(arrayList.get(position).getCode2()));

        return customView;
    }


}
