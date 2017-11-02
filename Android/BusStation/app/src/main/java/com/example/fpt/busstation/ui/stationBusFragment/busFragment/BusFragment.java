package com.example.fpt.busstation.ui.stationBusFragment.busFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseFragment;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.data.db.BusDto;

import java.util.List;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class BusFragment extends BaseFragment implements BaseRcvAdapter.OnItemListener {
    RecyclerView recyclerView;
    BusAdapter busAdapter;
    List<BusDto> busDtoList;

    public List<BusDto> getList() {
        return busDtoList;
    }

    public List<BusDto> getBusDtoList() {
        return busDtoList;
    }

    public void setBusDtoList(List<BusDto> busDtoList) {
        this.busDtoList = busDtoList;
    }

    @Override
    protected int getContentViewResource() {
        return R.layout.rcv_bus_fragment;
    }

    @Override
    protected void onInit(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_bus);
        busAdapter = new BusAdapter(busDtoList, this.getContext());
        busAdapter.setmListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(busAdapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Log.d("OnItemClick", ((BusDto) data).getBusNumber());
    }


    public void changeBusCross(List<BusDto> busList) {
        busAdapter.changeItems(busList);
    }


}
