package com.effone.hostismeandroid.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.activity.View_Pay_BillActivity;
import com.effone.hostismeandroid.model.Restaurant;
import com.effone.hostismeandroid.model.TaxItems;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class TaxDetailsAdapter  extends ArrayAdapter<TaxItems> {
    private ArrayList<TaxItems> taxItemses;
    private LayoutInflater inflater;
    public TaxDetailsAdapter(Context context, int tax_items, ArrayList<TaxItems> taxItemses) {
        super(context, tax_items,  taxItemses);
        this.taxItemses =(ArrayList<TaxItems>) taxItemses;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.tax_items, null);
            holder = new  FilterViewHolder();
            holder.tv_tax_name=(TextView)vi.findViewById(R.id.tv_tax_name);
            holder.tv_tax_money= (TextView) vi.findViewById(R.id.tv_tax_money);
            vi.setTag( holder );
        }
        else
            holder = (FilterViewHolder) vi.getTag();

        if (taxItemses.size() <= 0) {
            holder.tv_tax_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            TaxItems value = (TaxItems) taxItemses.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_tax_name.setText(value.getName());
            holder.tv_tax_money.setText(""+value.getValue());
        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_tax_name;
        TextView tv_tax_money;



    }
}
