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

public class InsWalkBeginViewHolder extends BaseRcvViewHolder {
    CardView cardView;
    TextView tvTitle;
    TextView tvStart;
    TextView tvBusNum;
    TextView tvFromStation;
    private static final int TXT_LENGTH_LIMIT = 35;

    public InsWalkBeginViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cvInsWalkBegin);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvStart = (TextView) itemView.findViewById(R.id.tvStart);
        tvBusNum = (TextView) itemView.findViewById(R.id.tvBusNum);
        tvFromStation = (TextView) itemView.findViewById(R.id.tvFromStation);
    }

    @Override
    public void bindItem(final BaseRcvAdapter.OnItemListener listener, final Object item, final int position) {
        WalkInstructionDto walkIns = (WalkInstructionDto) item;
        if (walkIns.getEndType() == 2) {
            tvTitle.setText("Đón tuyến số " + walkIns.getToBus());
            tvBusNum.setText("Đón tuyến số " + walkIns.getToBus());
        }

        String mess = "Xuất phát từ " + walkIns.getBeginCoord().getName();
        LayoutUtils.setLinesForTextView(mess, TXT_LENGTH_LIMIT, tvStart);
        tvStart.setText(mess);

        String mess2 = "Tại trạm " + walkIns.getEndCoord().getName();
        LayoutUtils.setLinesForTextView(mess, TXT_LENGTH_LIMIT, tvFromStation);
        tvFromStation.setText(mess2);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, item, position);
            }
        });
    }
}
