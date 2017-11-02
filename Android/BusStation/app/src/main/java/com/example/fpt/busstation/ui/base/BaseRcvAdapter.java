package com.example.fpt.busstation.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cuong on 9/12/2017.
 */

public abstract class BaseRcvAdapter extends RecyclerView.Adapter {
    protected OnItemListener mListener;

    public void setmListener(OnItemListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemListener {
        void onItemClick(View view, Object data, final int position);
    }
}
