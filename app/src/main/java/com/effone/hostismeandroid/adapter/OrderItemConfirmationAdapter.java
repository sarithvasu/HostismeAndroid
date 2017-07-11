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
import com.effone.hostismeandroid.model_for_confirmation.OrderDetails;
import com.effone.hostismeandroid.model_for_json.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarith.vasu on 13-06-2017.
 */

public class OrderItemConfirmationAdapter extends ArrayAdapter<OrderItem> {
    private List<OrderItem> taxItemses;
    private LayoutInflater inflater;


    public OrderItemConfirmationAdapter(ConfirmationActivity context, int order_summary_items, List<OrderItem> orderItems) {
        super(context, order_summary_items, (List<OrderItem>) orderItems);
        this.taxItemses =(List<OrderItem>) orderItems;
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
            holder.tv_tax_name.setText(getContext().getString(R.string.no_data));

        } else {
            /***** Get each Model object from Arraylist ********/
            OrderItem value = (OrderItem) taxItemses.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_tax_name.setText(value.getName());
            holder.tv_tax_money.setText("$ "+value.getItem_total_price());
            holder.tv_tax_quantity.setText(value.getQuantity()+" X $ "+ value.getUnit_price());

        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_tax_name;
        TextView tv_tax_money;
        TextView tv_tax_quantity;


    }
}
