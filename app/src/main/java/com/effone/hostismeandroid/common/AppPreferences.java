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
    private String REST_NAME="rest_name";


    public AppPreferences(Context context) {
        this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
    }


    public String getREST_NAME() {
        return sharedPrefs.getString(REST_NAME, "");
    }
    public void setREST_NAME(String rest_name) {
        prefsEditor.putString(REST_NAME, rest_name);
        prefsEditor.commit();
    }

    private String LOGIN_EMAIL="login_email";
    private String LOGIN_PASSWORD="login_password";

    private String PUSH_NOTIFICATION_REGISTRATION_ID="push_notification_registration_id";











    public String getSERVER_DATE() {
        return sharedPrefs.getString(SERVER_DATE, "");
    }
    public void saveSERVER_DATE(String server_date) {
        prefsEditor.putString(SERVER_DATE, server_date);
        prefsEditor.commit();
    }

    public boolean getFIRST_TIME_LAUNCH() {
        return sharedPrefs.getBoolean(FIRST_TIME_LAUNCH, true);
    }


    public int get_USER_ID() {
        return sharedPrefs.getInt(USER_ID, 0);
    }
    public void save_USER_ID(int user_id ) {
        prefsEditor.putInt(USER_ID, user_id);
        prefsEditor.commit();
    }
    public String get_USER_EMAIL_ID() {
        return sharedPrefs.getString(USER_EMAIL_ID, "");
    }
    public void save_USER_EMAIL_ID(String user_id ) {
        prefsEditor.putString(USER_EMAIL_ID, user_id);
        prefsEditor.commit();
    }
    public String get_USER_NAME() {
        return sharedPrefs.getString(USER_NAME, "");
    }
    public void save_USER_NAME(String user_name ) {
        prefsEditor.putString(USER_NAME, user_name);
        prefsEditor.commit();
    }




    public String getLOGIN_EMAIL() {
        return sharedPrefs.getString(LOGIN_EMAIL, "");
    }
    public void saveLOGIN_EMAIL(String login_email) {
        prefsEditor.putString(LOGIN_EMAIL, login_email);
        prefsEditor.commit();
    }
    public String getPUSH_NOTIFICATION_REGISTRATION_ID() {
        return sharedPrefs.getString(PUSH_NOTIFICATION_REGISTRATION_ID, "");
    }
    public void savePUSH_NOTIFICATION_REGISTRATION_ID(String push_notification_registration_id) {
        prefsEditor.putString(PUSH_NOTIFICATION_REGISTRATION_ID, push_notification_registration_id);
        prefsEditor.commit();
    }
    public String getLOGIN_PASSWORD() {
        return sharedPrefs.getString(LOGIN_PASSWORD, "");
    }
    public void saveLOGIN_PASSWORD(String login_password) {
        prefsEditor.putString(LOGIN_PASSWORD, login_password);
        prefsEditor.commit();
    }




}
