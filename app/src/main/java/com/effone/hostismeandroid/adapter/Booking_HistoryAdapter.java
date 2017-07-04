package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.model.BookingHistoryItem;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 4/18/2017.
 */

public class Booking_HistoryAdapter extends ArrayAdapter<BookingHistoryItem> {
    private ArrayList<BookingHistoryItem> orderSummaries;
    private LayoutInflater inflater;
    private Context context;

    public Booking_HistoryAdapter(Context context, int resource, ArrayList<BookingHistoryItem> orderSummaries) {
        super(context, resource, orderSummaries);
        this.context=context;
        this.orderSummaries = (ArrayList<BookingHistoryItem>) orderSummaries;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final FilterViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.booking_table_items, null);
            holder = new FilterViewHolder();
            holder.tv_date_time = (TextView) vi.findViewById(R.id.tv_data_time);
            holder.tv_rest_name = (TextView) vi.findViewById(R.id.tv_rest_name);
            holder.tv_booking_id = (TextView) vi.findViewById(R.id.tv_booking_id);
            holder.tv_table_no = (TextView) vi.findViewById(R.id.tv_table_no);
            holder.tv_bill_ammount = (TextView) vi.findViewById(R.id.tv_bill_ammount);
            holder.tv_description = (TextView) vi.findViewById(R.id.tv_decription);
            holder.tv_order_total = (TextView) vi.findViewById(R.id.tv_order_total);
            holder.tv_order_status = (TextView) vi.findViewById(R.id.tv_order_status);
            //hidden variable declarrations

            vi.setTag(holder);
        } else
            holder = (FilterViewHolder) vi.getTag();

        if (orderSummaries.size() <= 0) {
            holder.tv_date_time.setText(getContext().getString(R.string.no_data));

        } else {
            /***** Get each Model object from Arraylist ********/
            BookingHistoryItem value = (BookingHistoryItem) orderSummaries.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_date_time.setText(": " + value.getDate());
            holder.tv_rest_name.setText(": " + value.getRestaurant_name());
            holder.tv_booking_id.setText(": " + value.getOrder_id());
            holder.tv_description.setText(": " + value.getDescription());
            if (value.getTable_no().equals("9999"))
                holder.tv_table_no.setText(": "+context.getResources().getString(R.string.togo));
            else if (value.getTable_no().equals("8888"))
                holder.tv_table_no.setText(": "+context.getResources().getString(R.string.bar));
            else
                holder.tv_table_no.setText(": " + value.getTable_no());
            holder.tv_order_total.setText(": " + value.getBill_no());
            holder.tv_order_status.setText(": " + value.getStatus());
            holder.tv_bill_ammount.setText(": " + value.getBill_amount());

        }

        return vi;

    }

    public static class FilterViewHolder {
        TextView tv_date_time;
        TextView tv_rest_name;
        TextView tv_booking_id;
        TextView tv_description;
        TextView tv_table_no;
        TextView tv_quantiates, quantity;
        TextView tv_order_total;
        TextView tv_order_status;
        TextView desctiption;
        TextView tv_bill_ammount;
        TextView date;


    }

}
