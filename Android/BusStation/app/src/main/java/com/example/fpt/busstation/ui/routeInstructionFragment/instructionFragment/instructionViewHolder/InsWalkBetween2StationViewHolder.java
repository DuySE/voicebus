package com.example.fpt.busstation.ui.routeInstructionFragment.instructionFragment.instructionViewHolder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.ui.base.BaseRcvViewHolder;
import com.example.fpt.busstation.data.db.WalkInstructionDto;
import com.example.fpt.busstation.util.LayoutUtils;

/**
 * Created by Vi Nguyen on 22/10/2017.
 */

public class InsWalkBetween2StationViewHolder extends BaseRcvViewHolder {
    CardView cardView;
    TextView tvTitle;
    TextView tvOffStation;
    TextView tvBusNum;
    private static final int TXT_LENGTH_LIMIT = 35;

    public InsWalkBetween2StationViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cvInsWalkBetween);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvOffStation = (TextView) itemView.findViewById(R.id.tvOffStation);
        tvBusNum = (TextView) itemView.findViewById(R.id.tvBusNum);

    }

    @Override
    public void bindItem(final BaseRcvAdapter.OnItemListener listener, final Object item, final int position) {
        WalkInstructionDto walkIns = (WalkInstructionDto) item;
        tvTitle.setText("Đi bộ đến trạm " + walkIns.getEndCoord().getName());
        String mess = "Xuống xe tại trạm " + walkIns.getBeginCoord().getName();
        LayoutUtils.setLinesForTextView(mess, TXT_LENGTH_LIMIT, tvOffStation);
        tvOffStation.setText(mess);
        String mess1 = "Đón tuyến số " + walkIns.getToBus() + " tại trạm " + walkIns.getEndCoord().getName();
        LayoutUtils.setLinesForTextView(mess1, TXT_LENGTH_LIMIT, tvBusNum);
        tvBusNum.setText(mess1);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, item, position);
            }
        });
    }
}
