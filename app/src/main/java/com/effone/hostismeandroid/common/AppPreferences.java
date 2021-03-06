package com.effone.hostismeandroid.common;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by sarith.vasu on 29-02-2016.
 */
public class AppPreferences {
    private static final String PHASE_ID ="phase_id" ;
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
    private String NUMBER_OF_TABLES="number_of_tables";
    private String QUANTITY ="quanity";
    private String DESCRIPTION ="desc";
    private String FLAG="flag";

    public AppPreferences(Context context) {
        this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
    }

    public int getQunatity(){
        return  sharedPrefs.getInt(QUANTITY,0);
    }
    public void setQunatity(int table_name) {
        prefsEditor.putInt(QUANTITY, table_name);
        prefsEditor.commit();
    }

    public String getDescription(){
        return  sharedPrefs.getString(DESCRIPTION,"");
    }
    public void setDESCRIPTION(String table_namess) {
        prefsEditor.putString(DESCRIPTION, table_namess);
        prefsEditor.commit();
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
    public int getNUMBER_OF_TABLES(){
        return  sharedPrefs.getInt(NUMBER_OF_TABLES,0);
    }
    public void setNUMBER_OF_TABLES(int number_of_tables) {
        prefsEditor.putInt(NUMBER_OF_TABLES, number_of_tables);
        prefsEditor.commit();
    }
    public int getPhaseId(){
        return  sharedPrefs.getInt(PHASE_ID,0);
    }
    public void setPhaseId(int phase_id) {
        prefsEditor.putInt(PHASE_ID, phase_id);
        prefsEditor.commit();
    }

    public Boolean getFlag(){
        return  sharedPrefs.getBoolean(FLAG,false);
    }
    public void setFlag(Boolean phase_id) {
        prefsEditor.putBoolean(FLAG, phase_id);
        prefsEditor.commit();
    }
}
