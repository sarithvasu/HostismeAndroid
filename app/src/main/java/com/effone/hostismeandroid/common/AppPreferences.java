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
    private  String TANLE_NAME="table_name";

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

    public String getRESTAURANT_NAME() {
        return sharedPrefs.getString(RESTAURANT_NAME, "");
    }
    public void setRRESTAURANT_NAME(String rest_name) {
        prefsEditor.putString(RESTAURANT_NAME, rest_name);
        prefsEditor.commit();
    }




}
