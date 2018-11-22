package com.sndo9.robert.gaslog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EntryAdaptor extends BaseAdapter {

    public static class EntryViewHolder {
        public TextView mileage;
        public TextView gallons;
        public TextView totalPrice;
        public TextView pricePerGallon;
        public TextView date;
    }

    private Context entryContext;
    private List<LogEntry> entryList;

    public EntryAdaptor(Context context, List<LogEntry> entries) {
        entryList = entries;
        entryContext = context;
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Object getItem(int position) {
        return entryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("adaptor", "Getting View");
        Log.d("adaptor", "adding entry ");
        EntryViewHolder holder;

        if(convertView == null) {
            LayoutInflater entryInflater = (LayoutInflater) entryContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = entryInflater.inflate(R.layout.entry, null);

            holder = new EntryViewHolder();
            holder.mileage = (TextView) convertView.findViewById(R.id.mileage);
            holder.gallons = (TextView) convertView.findViewById(R.id.gallons);
            holder.totalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
            holder.pricePerGallon = (TextView) convertView.findViewById(R.id.pricePerGallon);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (EntryViewHolder) convertView.getTag();
        }

        LogEntry entry = (LogEntry) getItem(position);
        holder.mileage.setText(String.valueOf(entry.getMileage()));
        holder.gallons.setText(String.valueOf(entry.getGallons()));
        holder.totalPrice.setText(String.valueOf(entry.getTotalPrice()));
        holder.pricePerGallon.setText(String.valueOf(entry.getPricePerGallon()));
        holder.date.setText(entry.getDate());

        return convertView;
    }

    public void add(LogEntry entry) {
        Log.d("adaptor", "adding entry " + entry.toString());
        entryList.add(entry);
        notifyDataSetChanged();
    }
}
