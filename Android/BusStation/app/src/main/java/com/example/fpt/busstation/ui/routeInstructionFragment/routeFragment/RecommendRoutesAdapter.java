package com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment.routeViewHolder.RecommendRoutes1BusViewHolder;
import com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment.routeViewHolder.RecommendRoutes2BusViewHolder;
import com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment.routeViewHolder.RecommendRoutes3BusViewHolder;

import java.util.List;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class RecommendRoutesAdapter extends BaseRcvAdapter {
    List<RecommendRoutesDto> listRecommend;
    Context context;

    public RecommendRoutesAdapter(List<RecommendRoutesDto> listRecommend, Context context) {
        this.listRecommend = listRecommend;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        Log.d("onCreateViewHolder", "viewTye: " + viewType);
        switch (viewType) {
            case 0:
                break;
            case 1: //1 bus
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_route_style2_1bus, parent, false);
                viewHolder = new RecommendRoutes1BusViewHolder(view);
                break;
            case 2: //2 bus
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_route_style2_2bus, parent, false);
                viewHolder = new RecommendRoutes2BusViewHolder(view);
                break;

            case 3: //3 bus
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_route_style2_3bus, parent, false);
                viewHolder = new RecommendRoutes3BusViewHolder(view);
                break;
            default:
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        Log.d("onBindViewHolder", "viewType: "+ viewType);
        switch (viewType) {
            case 0:
                break;
            case 1:
                RecommendRoutes1BusViewHolder viewHolder1Bus = (RecommendRoutes1BusViewHolder) holder;
                viewHolder1Bus.bindItem(mListener, listRecommend.get(position), position);
                break;
            case 2:
                RecommendRoutes2BusViewHolder viewHolder2Bus = (RecommendRoutes2BusViewHolder) holder;
                viewHolder2Bus.bindItem(mListener, listRecommend.get(position), position);
                break;
            case 3:
                RecommendRoutes3BusViewHolder viewHolder3Bus = (RecommendRoutes3BusViewHolder) holder;
                viewHolder3Bus.bindItem(mListener, listRecommend.get(position), position);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(listRecommend.get(position).getTotalBus()==1){
            return 1;
        }
        else if (listRecommend.get(position).getTotalBus() == 2) {
            return 2;
        } else if (listRecommend.get(position).getTotalBus() == 3) {
            return 3;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (listRecommend != null && listRecommend.size() > 0) {
            return listRecommend.size();
        }
        return 1;
    }

}
