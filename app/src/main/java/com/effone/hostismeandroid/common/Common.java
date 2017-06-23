package com.effone.hostismeandroid.common;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;

/**
 * Created by sarith.vasu on 20-04-2017.
 */

public class Common {
    public static  void setCustomTitile(final AppCompatActivity context, String title, String subTitile) {
         context.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(context).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        context.getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile=(TextView)context.getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(title);
        if(subTitile!=null) {
            TextView cust_sub_ttile
                    = (TextView) context.getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
            cust_sub_ttile.setText(subTitile);
        }else{
            TextView cust_sub_ttile = (TextView) context.getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
            cust_sub_ttile.setVisibility(View.GONE);
        }
        ImageView iv_back=(ImageView)context.getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }
    public static  void setCustomTitileCOnfirmation(final AppCompatActivity context, String title, String subTitile) {
        context.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(context).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        context.getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile=(TextView)context.getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(title);
        if(subTitile!=null) {
            TextView cust_sub_ttile
                    = (TextView) context.getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
            cust_sub_ttile.setText(subTitile);
        }else{
            TextView cust_sub_ttile = (TextView) context.getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
            cust_sub_ttile.setVisibility(View.GONE);
        }
        ImageView iv_back=(ImageView)context.getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setVisibility(View.INVISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }
    public static String commonUrlBuilder(String uri, String appendQuery)  {
        String newQuery=uri;
        if (appendQuery != null) {
            newQuery += "&" + appendQuery;
        }
        return newQuery;
    }
}