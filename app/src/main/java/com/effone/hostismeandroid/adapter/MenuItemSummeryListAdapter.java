package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class MenuItemSummeryListAdapter extends ArrayAdapter<OrderedItemSummary> {
    private ArrayList<OrderedItemSummary> orderedItemSummaries;
    private LayoutInflater inflater;
    public MenuItemSummeryListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OrderedItemSummary> orderedItemSummaries) {
        super(context, resource,  orderedItemSummaries);
        this.orderedItemSummaries =(ArrayList<OrderedItemSummary>) orderedItemSummaries;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final MenuItemSummeryListAdapter.FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.summery_list_item, null);
            holder = new MenuItemSummeryListAdapter.FilterViewHolder();
            holder.tv_summery_item_name=(TextView)vi.findViewById(R.id.tv_summery_item_name);
            holder.tv_item_total_price = (TextView) vi.findViewById(R.id.tv_item_total_price);
            holder.tv_service_charges_value=(TextView)vi.findViewById(R.id.tv_service_charges_value);
            holder.tv_vat_value = (TextView) vi.findViewById(R.id.tv_vat_value);
            holder.tv_service_tax_value=(TextView)vi.findViewById(R.id.tv_service_tax_value);
            holder.tv_total = (TextView) vi.findViewById(R.id.tv_total);
            vi.setTag( holder );
        }
        else
            holder = (MenuItemSummeryListAdapter.FilterViewHolder) vi.getTag();

        if (orderedItemSummaries.size() <= 0) {
            holder.tv_summery_item_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            OrderedItemSummary value = (OrderedItemSummary) orderedItemSummaries.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_summery_item_name.setText(value.getItemName()+"("+value.getQuatity()+")");
            holder.tv_item_total_price.setText("$ "+value.getPrice());
            holder.tv_service_charges_value.setText("$ "+value.getServiceCharges());
            holder.tv_vat_value.setText("$ "+value.getVat());
            holder.tv_service_tax_value.setText("$ "+value.getServiceTax());
        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_summery_item_name;
        TextView tv_item_total_price;
        TextView tv_service_charges_value;
        TextView tv_vat_value;
        TextView tv_service_tax_value;
        TextView tv_total;
    }
}

