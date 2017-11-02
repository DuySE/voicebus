package com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment.routeViewHolder;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.ui.base.BaseRcvViewHolder;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.util.LayoutUtils;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class RecommendRoutes1BusViewHolder extends BaseRcvViewHolder {
    CardView cardView;
    TextView tvDuration;
    TextView tvRouteName;
    TextView tvNoOfBus1;


    public RecommendRoutes1BusViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cvRoute1Bus);
        tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
        tvRouteName = (TextView) itemView.findViewById(R.id.tvRouteName);
        tvNoOfBus1 = (TextView) itemView.findViewById(R.id.tvNoOfBus1);

    }

    @Override
    public void bindItem(final BaseRcvAdapter.OnItemListener listener, final Object item, final int position) {
        RecommendRoutesDto dto = (RecommendRoutesDto) item;
        tvRouteName.setText(((RecommendRoutesDto) item).generateRouteName());
        String txtDuration = LayoutUtils.handleDisplayTime(dto.getDuration());
        tvDuration.setText(txtDuration);
        String[] busNumber = ((RecommendRoutesDto) item).getListBusNo().split(",");
        if (busNumber.length == 1) {
            tvNoOfBus1.setText(busNumber[0]);

        } else {
            Log.d("Bind Item View Holder", "List: " + busNumber.toString());
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, item, position);
            }
        });

    }
}
