package com.example.fpt.busstation.ui.stationBusFragment.stationFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseFragment;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.data.db.StationDto;

import java.util.List;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class StationFragment extends BaseFragment implements BaseRcvAdapter.OnItemListener {
    RecyclerView recyclerView;
    StationAdapter stationAdapter;
    List<StationDto> stationDtoList;
    Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public StationFragment() {
    }

    public List<StationDto> getStationDtoList() {
        return stationDtoList;
    }

    public void setStationDtoList(List<StationDto> stationDtoList) {
        this.stationDtoList = stationDtoList;
    }

    @Override
    protected int getContentViewResource() {
        return R.layout.rcv_station_fragment;
    }

    @Override
    protected void onInit(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_station);
        stationAdapter = new StationAdapter(stationDtoList, this.getContext());
        stationAdapter.setmListener(this);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(stationAdapter);
        recyclerView.setNestedScrollingEnabled(true);
    }


    @Override
    public void onItemClick(View view, Object data, int position) {
        Log.d("OnItemClick", ((StationDto) data).getStationName());
        callback.changeListBusCross(position);
    }

    public interface Callback {
        /*Change stations list which selected bus cross*/
        void changeListBusCross(int position);
    }

}
