package com.example.fpt.busstation.ui.stationBusFragment.busFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.data.db.BusDto;

import java.util.List;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class BusAdapter extends BaseRcvAdapter {
    private List<BusDto> lstBus;
    private Context context;

    public BusAdapter(List<BusDto> lstBus, Context context) {
        this.lstBus = lstBus;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BusViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bus, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BusViewHolder busViewHolder = (BusViewHolder) holder;
        busViewHolder.bindItem(mListener, lstBus.get(position), position);

    }

    @Override
    public int getItemCount() {
        if (lstBus != null && lstBus.size() > 0) {
            return lstBus.size();
        }
        return 0;
    }

    public void addItems(List<BusDto> listBus) {
        this.lstBus.addAll(listBus);
        notifyDataSetChanged();
    }

    public void changeItems(List<BusDto> busDtoList) {
        lstBus = busDtoList;
        notifyDataSetChanged();
    }
}
