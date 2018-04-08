package com.opop.brazius.bitcoinexchangerates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.opop.brazius.bitcoinexchangerates.json_models.BitcoinItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ListAdapter extends ArrayAdapter<BitcoinItem> {
    private final List<BitcoinItem> mItems;
    private final Context mContext;
    private final LayoutInflater inflater;

    public ListAdapter(Context context, int resourceId, List<BitcoinItem> items) {
        super(context, resourceId, items);
        mContext = context;
        mItems = items;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.chartName = convertView.findViewById(R.id.tv_chart_name);
            holder.timeUpdated = convertView.findViewById(R.id.tv_time_updated);
            holder.bpiRate = convertView.findViewById(R.id.tv_bpi_rate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitcoinItem item = getItem(position);
        if (item != null) {
            holder.chartName.setText(item.getChartName());
            holder.timeUpdated.setText(getTime(item));
            String str = String.format(Locale.ENGLISH,"USD :%.2f, GPB : %.2f, EUR : %.2f"
                    ,item.getBpi().getUSD().getRateFloat(),item.getBpi().getGBP().getRateFloat(),item.getBpi().getEUR().getRateFloat());
            holder.bpiRate.setText(str);
        }

        return convertView;
    }

    private String getTime(BitcoinItem item) {
        String changedDate = "";

        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ",Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
            Date date = sdf.parse(item.getTime().getUpdatedISO());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM hh:mm a",Locale.ENGLISH); //this format changeable according to your choice
            dateFormatter.setTimeZone(TimeZone.getDefault());
            changedDate = dateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changedDate;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public BitcoinItem getItem(int position) {
        return mItems.get(position);
    }

    public void setItems(List<BitcoinItem> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView chartName;
        TextView timeUpdated;
        TextView bpiRate;

    }
}