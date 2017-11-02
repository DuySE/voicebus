package com.example.fpt.busstation.ui.stationBusFragment.stationFragment;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.ui.base.BaseRcvViewHolder;
import com.example.fpt.busstation.data.db.StationDto;

/**
 * Created by Vi Nguyen on 17/10/2017.
 */

public class StationViewHolder extends BaseRcvViewHolder {
    CardView cardView;
    TextView tvStaName;
    TextView tvStaAddr;
    TextView tvDuration;
    TextView tvDistance;

    public StationViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cvStation);
        tvStaName = (TextView) itemView.findViewById(R.id.tvStaName);
        tvStaAddr = (TextView) itemView.findViewById(R.id.tvStaAddr);
        tvDuration = (TextView) itemView.findViewById(R.id.tvStaDuration);
        tvDistance = (TextView) itemView.findViewById(R.id.tvStaDistance);
    }

    @Override
    public void bindItem(final BaseRcvAdapter.OnItemListener listener, final Object item, final int position) {
        StationDto station = (StationDto) item;
        tvStaName.setText(station.getStationName());
        tvStaAddr.setText(station.getStationAddress());
        String timeUnit = "phút";
        String lengthUnit = "m";
        Double duration = station.getDuration();
        Double distance = station.getDistance();

        if(duration >= 100){
            duration = duration/100;
            timeUnit = "giờ";
        }

        if(distance >= 1000){
            distance = distance/1000;
            lengthUnit = "km";
        }
        tvDuration.setText(String.valueOf((double)Math.round(duration)) + " " + timeUnit);
        tvDistance.setText(String.valueOf((double)Math.round(distance)) + " " + lengthUnit);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, item, position);
            }
        });

    }
}
