package com.example.fpt.busstation.data.conn;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.fpt.busstation.R;
import com.example.fpt.busstation.service.OnResponseStringListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuong on 9/12/2017.
 */

public abstract class BaseRestClient {
    private final static String TAG = "BaseClientRequest";
    public static String TAG_POST_REQ = "post_string_req";
    public static String TAG_GET_REQ = "get_string_req";
    public static String CONTENT_TYPE = "Content-Type";
    public static String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded; charset=UTF-8";
    public static String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
    protected RequestQueue mRequestQueue;
    private static final int TIME_OUT = 30000;

    protected static BaseRestClient mInstance;
    public BaseRestClient() {
    }

    public abstract Context getContext();

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    //POST STRING REQUEST
    //using getParams
    public void postRequest(String URL_STRING_REQ, final Map<String, String> params, final OnResponseStringListener listener) {
        // String request
        UTF8StringRequest str = new UTF8StringRequest(Request.Method.POST, URL_STRING_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null)
                    listener.onError(error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
                return headers;
            }
        };
        addToRequestQueue(str, TAG_POST_REQ);
    }
    //using GetBody
    public void postAudioRequest(String URL_STRING_REQ, final String text, final OnResponseStringListener listener) {
        // String request
        UTF8StringRequest str = new UTF8StringRequest(Request.Method.POST, URL_STRING_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null)
                    listener.onError(error);
            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return text == null ? null : text.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", text, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("api_key", getContext().getString(R.string.openfpt_api_key));
                headers.put("speed","0");
                headers.put("voice","female");
                headers.put("prosody","1");
                headers.put("Cache-Control","no-cache");
                return headers;
            }
        };
        addToRequestQueue(str, TAG_POST_REQ);
    }
    //get Using getUrl
    public void getRequest(final String URL_STRING_REQ, final Map<String,String> params, final OnResponseStringListener listener){
        UTF8StringRequest str = new UTF8StringRequest(Request.Method.GET, URL_STRING_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.onResponse(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null)
                    listener.onError(error);
            }
        }){
            @Override
            public String getUrl() {

                StringBuilder stringBuilder = new StringBuilder(URL_STRING_REQ);
                int i = 1;
                for (Map.Entry<String,String> entry: params.entrySet()) {
                    String key;
                    String value;
                    try {
                        key = URLEncoder.encode(entry.getKey(), "UTF-8");
                        value = URLEncoder.encode(entry.getValue(), "UTF-8");
                        if(i == 1) {
                            stringBuilder.append("?" + key + "=" + value);
                        } else {
                            stringBuilder.append("&" + key + "=" + value);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    i++;

                }
                String url = stringBuilder.toString();
                Log.d("URL",url);
                return url;
            }
        };
        addToRequestQueue(str, TAG_GET_REQ);
    }
}
