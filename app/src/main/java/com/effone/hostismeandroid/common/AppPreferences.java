package com.effone.hostismeandroid.common;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by sarith.vasu on 29-02-2016.
 */
public class AppPreferences {
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String APP_SHARED_PREFS	= AppPreferences.class.getSimpleName();




    private String FIRST_TIME_LAUNCH="first_time_launch";
    private String SERVER_DATE="server_date";
    private String USER_ID="user_id";
    private String USER_EMAIL_ID="email_id";
    private String USER_NAME="user_name";
    private String RESTAURANT_NAME="rest_name";
    private String RESTAURANT_ADDRESS="restaurant_address";
    private  String TANLE_NAME="table_name";
    private  String ORDER_ID="order_id";
    private String RESTAURANT_ID="restaurant_id";
    private String DEVICE_ID="device_id";


    public AppPreferences(Context context) {
        this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
    }

    public int getTABLE_NAME(){
        return  sharedPrefs.getInt(TANLE_NAME,0);
    }
    public void setTABLE_NAME(int table_name) {
        prefsEditor.putInt(TANLE_NAME, table_name);
        prefsEditor.commit();
    }

    public String getRESTAURANT_ADDRESS() {
        return sharedPrefs.getString(RESTAURANT_ADDRESS, "");
    }
    public void setRESTAURANT_ADDRESS(String restaurant_address) {
        prefsEditor.putString(RESTAURANT_ADDRESS, restaurant_address);
        prefsEditor.commit();
    }
    public String getRESTAURANT_NAME() {
        return sharedPrefs.getString(RESTAURANT_NAME, "");
    }
    public void setRRESTAURANT_NAME(String rest_name) {
        prefsEditor.putString(RESTAURANT_NAME, rest_name);
        prefsEditor.commit();
    }
    public String getORDER_ID() {

        return sharedPrefs.getString(ORDER_ID, "");
    }

    public void setORDER_ID(String order_id) {
        prefsEditor.putString(ORDER_ID, order_id);
        prefsEditor.commit();
    }
    public String getRESTAURANT_ID() {
        return sharedPrefs.getString(RESTAURANT_ID,"");
    }
    public void setRESTAURANT_ID(String restaurant_id) {
        prefsEditor.putString(RESTAURANT_ID, restaurant_id);
        prefsEditor.commit();
    }
    public String getDEVICE_ID() {
        return sharedPrefs.getString(DEVICE_ID,"");
    }
    public void setDEVICE_ID(String device_id) {
        prefsEditor.putString(DEVICE_ID, device_id);
        prefsEditor.commit();
    }


}
