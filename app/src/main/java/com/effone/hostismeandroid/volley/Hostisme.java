package com.effone.hostismeandroid.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumanth.peddinti on 5/2/2017.
 */

public class Hostisme extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private final int MY_SOCKET_TIMEOUT_MS=20000;


    public Map<String, String> responseHeaders;

    public Hostisme(int method, String url, Response.Listener<byte[]> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        setShouldCache(false);
        mListener = listener;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        byte[] json = null;

        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));

    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("", "");
        return headers;
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        super.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return null;
    }
}
