package com.example.fpt.busstation.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cuong on 9/12/2017.
 */

public abstract class BaseRcvViewHolder extends RecyclerView.ViewHolder {
    //bind button here

    public BaseRcvViewHolder(View itemView) {
        super(itemView);
    }
    //listener item inside itemview and itemview
    public abstract void bindItem(BaseRcvAdapter.OnItemListener listener, Object item, final int position);

}
