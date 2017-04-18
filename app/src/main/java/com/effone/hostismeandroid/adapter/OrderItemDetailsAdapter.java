package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.activity.View_Pay_BillActivity;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.TaxItems;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class OrderItemDetailsAdapter extends ArrayAdapter<Order_Items> {
    private ArrayList<Order_Items> taxItemses;
    private LayoutInflater inflater;
    public OrderItemDetailsAdapter(Context context, int tax_items, ArrayList<Order_Items> taxItemses) {
        super(context, tax_items,  taxItemses);
        this.taxItemses =(ArrayList<Order_Items>) taxItemses;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.order_summary_items, null);
            holder = new FilterViewHolder();
            holder.tv_tax_name=(TextView)vi.findViewById(R.id.tv_item_name);
            holder.tv_tax_money = (TextView) vi.findViewById(R.id.tv_item_price);
            holder.tv_tax_quantity = (TextView) vi.findViewById(R.id.tv_item_price_quantity);

            vi.setTag( holder );
        }
        else
            holder = (FilterViewHolder) vi.getTag();

        if (taxItemses.size() <= 0) {
            holder.tv_tax_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            Order_Items value = (Order_Items) taxItemses.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_tax_name.setText(value.getName());
            holder.tv_tax_money.setText(""+value.getPrice()*value.getQuantity());
            holder.tv_tax_quantity.setText(value.getPrice()+" * "+ value.getQuantity());

        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_tax_name;
        TextView tv_tax_money;
        TextView tv_tax_quantity;


    }
}
