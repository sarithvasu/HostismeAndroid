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
    private ArrayList<OrderedItemSummary> restaurants;
    private LayoutInflater inflater;
    public MenuItemSummeryListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OrderedItemSummary> restaurants) {
        super(context, resource,  restaurants);
        this.restaurants =(ArrayList<OrderedItemSummary>) restaurants;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final RestaurantListAdapter.FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.restaurant_list_item, null);
            holder = new RestaurantListAdapter.FilterViewHolder();
            holder.tv_restaurant_name=(TextView)vi.findViewById(R.id.tv_restaurant_name);
            holder.tv_restaurant_adress = (TextView) vi.findViewById(R.id.tv_restaurant_adress);
            vi.setTag( holder );
        }
        else
            holder = (RestaurantListAdapter.FilterViewHolder) vi.getTag();

        if (restaurants.size() <= 0) {
            holder.tv_restaurant_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            OrderedItemSummary value = (OrderedItemSummary) restaurants.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_restaurant_name.setText(value.getItemName());
           // holder.tv_restaurant_adress.setText(value.getRestAdress()+", "+value.getCity()+", "+value.getCountry());
        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_restaurant_name;
        TextView tv_restaurant_adress;



    }
}

