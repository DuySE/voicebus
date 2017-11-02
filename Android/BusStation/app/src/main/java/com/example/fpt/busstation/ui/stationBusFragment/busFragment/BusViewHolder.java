package com.example.fpt.busstation.ui.stationBusFragment.busFragment;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.ui.base.BaseRcvViewHolder;
import com.example.fpt.busstation.data.db.BusDto;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class BusViewHolder extends BaseRcvViewHolder {
    CardView cardView;
    TextView tvBusNum;
    TextView tvBusRoute;

    public BusViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cvBus);
        tvBusNum = (TextView) itemView.findViewById(R.id.tvBusNum);
        tvBusRoute = (TextView) itemView.findViewById(R.id.tvBusRoute);
    }

    @Override
    public void bindItem(final BaseRcvAdapter.OnItemListener listener, final Object item, final int position) {
        BusDto bus = (BusDto) item;
        tvBusNum.setText(bus.getBusNumber());
        tvBusRoute.setText(bus.getBusRoute());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, item, position);
            }
        });

    }
}
