package com.example.fpt.busstation.ui.routeInstructionFragment.instructionFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseFragment;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;

import java.util.List;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class InstructionFragment extends BaseFragment implements BaseRcvAdapter.OnItemListener {
    RecyclerView recyclerView;
    InstructionAdapter adapter;
    List<Object> listDto;

    public List<Object> getListDto() {
        return listDto;
    }

    public void setListDto(List<Object> listDto) {
        this.listDto = listDto;
    }

    public InstructionFragment() {
    }

    @Override
    protected int getContentViewResource() {
        return R.layout.rcv_route_instruction_fragment;
    }

    @Override
    protected void onInit(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_instruction);
        adapter = new InstructionAdapter(listDto, this.getContext());
        adapter.setmListener(this);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Log.d("OnItemClick", "onItemCLick");
    }

    public void changeInstruction(List<Object> dtos){
        adapter.changeItems(dtos);
    }
}

