package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.activity.MenusActivity;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.db.SqlOperations;
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
    private  Context mContext;
    private SqlOperations sqliteoperation;
    private OnDataChangeListener mOnDataChangeListener;
    public MenuItemSummeryListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OrderedItemSummary> orderedItemSummaries) {
        super(context, resource,  orderedItemSummaries);
        this.mContext=context;
        this.orderedItemSummaries =(ArrayList<OrderedItemSummary>) orderedItemSummaries;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mOnDataChangeListener= (OnDataChangeListener) context;
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
            holder.tv_minus=(TextView) vi.findViewById(R.id.tv_minus);
            holder.tv_add=(TextView) vi.findViewById(R.id.tv_plus);
            holder.tv_quantity=(TextView) vi.findViewById(R.id.tv_qutity);
            holder.tv_close=(TextView)vi.findViewById(R.id.tv_close);
            vi.setTag( holder );
        }
        else
            holder = (MenuItemSummeryListAdapter.FilterViewHolder) vi.getTag();

        if (orderedItemSummaries.size() <= 0) {
            holder.tv_summery_item_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            final OrderedItemSummary value = (OrderedItemSummary) orderedItemSummaries.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_summery_item_name.setText(value.getItemName()+"("+value.getQuatity()+")");
            holder.tv_item_total_price.setText("$ "+value.getTotalCost());
            holder.tv_service_charges_value.setText("$ "+value.getServiceCharges());
            holder.tv_vat_value.setText("$ "+value.getVat());
            holder.tv_service_tax_value.setText("$ "+value.getServiceTax());
            holder.tv_total.setText("$ "+totalAmmount(value.getTotalCost(),value.getServiceCharges(),value.getVat(),value.getServiceTax()));
            holder.tv_quantity.setText(""+value.getQuatity());

        final int[] qty = new int[1];
        if(! holder.tv_quantity.getText().toString().equals(""))
            qty[0] =Integer.parseInt(holder.tv_quantity.getText().toString());
        holder.tv_close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sqliteoperation = new SqlOperations(mContext);
                sqliteoperation.open();
                if(sqliteoperation.getCartItem()!=1) {
                    sqliteoperation.itemDelete(value.getItem_cat(), value.getItem_food());
                }else{
                    sqliteoperation.itemDelete(value.getItem_cat(), value.getItem_food());
                    Intent intent=new Intent(mContext, MenusActivity.class);
                    intent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);

            }
            });
        holder.tv_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(qty[0] <99) {
                    qty[0]++;
                    holder.tv_quantity.setText("" + qty[0]);
                }

                sqliteoperation = new SqlOperations(mContext);
                sqliteoperation.open();
                sqliteoperation.AddOrSubstractProduct(value.getItem_cat(), value.getItem_food(), value.getItemName(), value.getPrice(), 1);
                //mOnDataChangeListener.onDataChanged(1);
                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);
                // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 1));
            }
        });


        holder.tv_minus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(qty[0] >0) {
                    qty[0]--;
                    holder.tv_quantity.setText("" + qty[0]);
                }
          //      Toast.makeText(context, "click remove", Toast.LENGTH_LONG).show();
                sqliteoperation = new SqlOperations(mContext);
                sqliteoperation.open();
                sqliteoperation.AddOrSubstractProduct(value.getItem_cat(), value.getItem_food(), value.getItemName(), value.getPrice(), 2);
                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);
                // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 2));

            }
        });
        }
        return vi;

    }

    private double totalAmmount(float price, double serviceCharges, double vat, double serviceTax) {
        double sum=price+serviceCharges+vat+serviceTax;
        return sum;
    }

    public static  class FilterViewHolder {
        TextView tv_summery_item_name;
        TextView tv_item_total_price;
        TextView tv_service_charges_value;
        TextView tv_vat_value;
        TextView tv_service_tax_value;
        TextView tv_total;
        TextView tv_minus;
        TextView tv_add;
        TextView tv_quantity;
        TextView tv_close;
    }
}

