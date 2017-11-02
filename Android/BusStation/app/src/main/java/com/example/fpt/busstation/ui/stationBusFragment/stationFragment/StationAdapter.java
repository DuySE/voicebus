package com.example.fpt.busstation.ui.stationBusFragment.stationFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.data.db.StationDto;

import java.util.List;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class StationAdapter extends BaseRcvAdapter {
    private List<StationDto> lstStation;
    private Context context;

    public StationAdapter(List<StationDto> lstStation, Context context) {
        this.lstStation = lstStation;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_station, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final StationViewHolder stationViewHolder = (StationViewHolder) holder;
        stationViewHolder.bindItem(mListener, lstStation.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (lstStation != null && lstStation.size() > 0) {
            return lstStation.size();
        }
        return 0;
    }

    public void addItems(List<StationDto> stationDtos) {
        this.lstStation.addAll(stationDtos);
        notifyDataSetChanged();
    }

}
