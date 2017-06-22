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
import com.effone.hostismeandroid.activity.ConfirmationActivity;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model_for_json.OrderItem;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 13-06-2017.
 */

public class OrderItemConfirmationAdapter extends ArrayAdapter<com.effone.hostismeandroid.model_for_confirmation.OrderItem> {
    private ArrayList<com.effone.hostismeandroid.model_for_confirmation.OrderItem> taxItemses;
    private LayoutInflater inflater;


    public OrderItemConfirmationAdapter(ConfirmationActivity context, int order_summary_items, ArrayList<com.effone.hostismeandroid.model_for_confirmation.OrderItem> orderItems) {
        super(context, order_summary_items, orderItems);
        this.taxItemses =(ArrayList<com.effone.hostismeandroid.model_for_confirmation.OrderItem>) orderItems;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final OrderItemDetailsAdapter.FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.order_summary_items, null);
            holder = new OrderItemDetailsAdapter.FilterViewHolder();
            holder.tv_tax_name=(TextView)vi.findViewById(R.id.tv_item_name);
            holder.tv_tax_money = (TextView) vi.findViewById(R.id.tv_item_price);
            holder.tv_tax_quantity = (TextView) vi.findViewById(R.id.tv_item_price_quantity);

            vi.setTag( holder );
        }
        else
            holder = (OrderItemDetailsAdapter.FilterViewHolder) vi.getTag();

        if (taxItemses.size() <= 0) {
            holder.tv_tax_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            com.effone.hostismeandroid.model_for_confirmation.OrderItem value = (com.effone.hostismeandroid.model_for_confirmation.OrderItem) taxItemses.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_tax_name.setText(value.getName());
            holder.tv_tax_money.setText("$ "+value.getItemTotalPrice());
            holder.tv_tax_quantity.setText(value.getUnitPrice()+" * "+ value.getQuantity());

        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_tax_name;
        TextView tv_tax_money;
        TextView tv_tax_quantity;


    }
}
