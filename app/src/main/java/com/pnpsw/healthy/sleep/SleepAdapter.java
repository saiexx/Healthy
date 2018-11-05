package com.pnpsw.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pnpsw.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter {
    List<Sleep> sleeps;
    Context context;

    public SleepAdapter(Context context, int resource, List<Sleep> objects){
        super(context, resource, objects);
        this.sleeps = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_list_item,parent,false);
        TextView date = sleepItem.findViewById(R.id.sleep_list_item_date);
        TextView time = sleepItem.findViewById(R.id.sleep_list_item_time);
        TextView sleepTime = sleepItem.findViewById(R.id.sleep_list_item_sleep_time);
        Sleep sleep = sleeps.get(position);
        date.setText(sleep.getDate());
        time.setText(sleep.getToBedTime() + " - " + sleep.getAwakeTime());
        sleepTime.setText(sleep.getSleepTime());
        return sleepItem;
    }
}
