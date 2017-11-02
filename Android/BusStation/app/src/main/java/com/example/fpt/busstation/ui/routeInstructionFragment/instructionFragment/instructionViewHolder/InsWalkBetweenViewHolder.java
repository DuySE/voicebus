package com.example.fpt.busstation.ui.routeInstructionFragment.instructionFragment.instructionViewHolder;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.data.db.BusTransferInstructionDto;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.ui.base.BaseRcvViewHolder;
import com.example.fpt.busstation.data.db.WalkInstructionDto;
import com.example.fpt.busstation.util.LayoutUtils;

/**
 * Created by Vi Nguyen on 22/10/2017.
 */

public class InsWalkBetweenViewHolder extends BaseRcvViewHolder {
    CardView cardView;
    TextView tvTitle;
    TextView tvOffStation;
    TextView tvBusNum;
    private static final int TXT_LENGTH_LIMIT = 35;

    public InsWalkBetweenViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cvInsWalkBetween);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvOffStation = (TextView) itemView.findViewById(R.id.tvOffStation);
        tvBusNum = (TextView) itemView.findViewById(R.id.tvBusNum);

    }

    @Override
    public void bindItem(final BaseRcvAdapter.OnItemListener listener, final Object item, final int position) {
        BusTransferInstructionDto transferDto = (BusTransferInstructionDto) item;
        tvTitle.setText("Chuyển từ tuyến " + transferDto.getFromBus() + " sang tuyến " + transferDto.getToBus());
        tvBusNum.setText("Đón tuyến số " + transferDto.getToBus());
        String mess = "Xuống xe tại trạm " + transferDto.getChangeCoord().getName();
        int lines = LayoutUtils.setLinesForTextView(mess, TXT_LENGTH_LIMIT, tvOffStation);
//        tvOffStation.setLines(lines);
//        tvOffStation.setMaxLines(lines);
        tvOffStation.setText(mess);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, item, TXT_LENGTH_LIMIT);
            }
        });
    }
}
