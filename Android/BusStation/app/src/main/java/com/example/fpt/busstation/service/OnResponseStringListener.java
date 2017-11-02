package com.example.fpt.busstation.service;

import com.android.volley.VolleyError;

/**
 * Created by cuong on 9/12/2017.
 */

public interface OnResponseStringListener {
    void onResponse(String data);
    void onError(VolleyError error);
}
