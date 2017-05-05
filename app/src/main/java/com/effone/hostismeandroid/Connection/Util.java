package com.effone.hostismeandroid.Connection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by sarith.vasu on 22-02-2017.
 */

public final class Util {
    public static final class Operations {
        private Operations() throws InstantiationException {
            throw new InstantiationException("This class is not for instantiation");
        }
        /**
         * Checks to see if the device is online before carrying out any operations.
         *
         * @return
         */
        public static boolean isOnline(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }
    }
    private Util() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }
    public void openActivity(Context context, Class<?> calledActivity) {
        Intent myIntent = new Intent(context, calledActivity);
        context.startActivity(myIntent);
    }
    public static  void StringRequestVolley(Context context, int RequestMothod_Code, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, final Map<String, String> params) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = null;
        if (RequestMothod_Code == Request.Method.GET) {
            stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        } else if (RequestMothod_Code == Request.Method.POST) {
            stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }};
        }
        queue.add(stringRequest);
    }
}
