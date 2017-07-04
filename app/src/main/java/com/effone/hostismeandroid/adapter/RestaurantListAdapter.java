package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarith.vasu on 11-04-2017.
 */

public class RestaurantListAdapter extends ArrayAdapter<Restaurant>{
    private Restaurant[] restaurants;
    private LayoutInflater inflater;
    public RestaurantListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Restaurant[] restaurants) {
        super(context, resource, restaurants);
        this.restaurants =restaurants;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.restaurant_list_item, null);
            holder = new FilterViewHolder();
            holder.tv_restaurant_name=(TextView)vi.findViewById(R.id.tv_restaurant_name);
            holder.tv_restaurant_adress = (TextView) vi.findViewById(R.id.tv_restaurant_adress);
            holder.tv_restaurant_email = (TextView) vi.findViewById(R.id.tv_restaurant_email);
            holder.tv_restaurant_mob = (TextView) vi.findViewById(R.id.tv_restaurant_mob);
            holder.tv_restaurant_land = (TextView) vi.findViewById(R.id.tv_restaurant_land);
            vi.setTag( holder );
        }
        else
            holder = (FilterViewHolder) vi.getTag();

        if (restaurants.length < 0 ) {
            holder.tv_restaurant_name.setText(getContext().getString(R.string.no_data));

        } else {
            /***** Get each Model object from Arraylist ********/
            /************  Set Model values in Holder elements ***********/
            holder.tv_restaurant_name.setText(restaurants[position].getRestaurant_name());
            holder.tv_restaurant_adress.setText(restaurants[position].getArea()+", "+restaurants[position].getStreet()+", "+restaurants[position].getState()+" "+restaurants[position].getPincode());
            holder.tv_restaurant_email.setText(": "+restaurants[position].getEmail());
            holder.tv_restaurant_mob.setText(": "+restaurants[position].getPhno_1());
            holder.tv_restaurant_land.setText(": "+restaurants[position].getPhno_2());
        }

        return vi;

    }
    public static  class FilterViewHolder {
        TextView tv_restaurant_name;
        TextView tv_restaurant_adress;
        TextView tv_restaurant_email;
        TextView tv_restaurant_mob;
        TextView tv_restaurant_land;





    }
}
