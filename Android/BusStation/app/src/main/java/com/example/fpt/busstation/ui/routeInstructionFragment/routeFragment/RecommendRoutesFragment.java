package com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.ui.base.BaseFragment;
import com.example.fpt.busstation.ui.base.BaseRcvAdapter;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;

import java.util.List;

/**
 * Created by Vi Nguyen on 21/10/2017.
 */

public class RecommendRoutesFragment extends BaseFragment implements BaseRcvAdapter.OnItemListener {
    RecyclerView recyclerView;
    RecommendRoutesAdapter adapter;
    Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public List<RecommendRoutesDto> getList() {
        return list;
    }

    public void setList(List<RecommendRoutesDto> list) {
        this.list = list;
    }

    List<RecommendRoutesDto> list;


    @Override
    protected int getContentViewResource() {
        return R.layout.rcv_route_recommend_fragment;
    }

    @Override
    protected void onInit(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_route_recommend);

        adapter = new RecommendRoutesAdapter(list, this.getContext());
        adapter.setmListener(this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);

    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        callback.changeInstruction(position);
    }
    public interface Callback {
        void changeInstruction(int position);
    }
}
