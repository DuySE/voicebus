package com.example.fpt.busstation.ui.routeInstructionFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.service.CustomViewPager;
import com.example.fpt.busstation.service.MainActivityCallbacks;
import com.example.fpt.busstation.ui.base.BaseFragment;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.ui.routeInstructionFragment.instructionFragment.InstructionFragment;
import com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment.RecommendRoutesFragment;
import com.example.fpt.busstation.ui.stationBusFragment.StationBusAdapter;
import com.example.fpt.busstation.util.BottomSheetUtils;

import java.util.List;

/**
 * Created by Vi Nguyen on 24/10/2017.
 */

public class RouteInstructionFragment extends BaseFragment implements RecommendRoutesFragment.Callback {
    CustomViewPager viewPager;
    RouteInstructionAdapter pagerAdapter;
    TabLayout tabLayout;
    RecommendRoutesFragment recommendRoutesFragment;
    InstructionFragment instructionFragment;
    List<RecommendRoutesDto> recommendRoutes;

    public MainActivityCallbacks getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(MainActivityCallbacks callback) {
        this.callbacks = callback;
    }

    MainActivityCallbacks callbacks;

    private static final int NUMBER_TAB = 2;
    private static final int[] tabIcons = {R.drawable.ic_tab_icon_route, R.drawable.ic_tab_icon_direction};


    public List<RecommendRoutesDto> getRecommendRoutes() {
        return recommendRoutes;
    }

    public void setRecommendRoutes(List<RecommendRoutesDto> recommendRoutes) {
        this.recommendRoutes = recommendRoutes;
    }

    public RouteInstructionFragment() {
        // Required empty public constructor
    }

    public RouteInstructionFragment(List<RecommendRoutesDto> list) {
        // Required empty public constructor
        this.recommendRoutes = list;
    }

    @Override
    protected int getContentViewResource() {
        return R.layout.parent_viewpager_route_instruction;
    }

    @Override
    protected void onInit(View view) {
        recommendRoutesFragment = new RecommendRoutesFragment();
        recommendRoutesFragment.setList(getRecommendRoutes());
        recommendRoutesFragment.setCallback(this);

        instructionFragment = new InstructionFragment();
        instructionFragment.setListDto(getRecommendRoutes().get(0).getInstruction());

        viewPager = (CustomViewPager) view.findViewById(R.id.vpRouteInstruction);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tlRouteInstruction);
        tabLayout.setupWithViewPager(viewPager);
        setupTabAttribute(viewPager, tabLayout);


        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        BottomSheetUtils.setupViewPager(viewPager, getBaseActivity().findViewById(R.id.bottom_sheet));

    }

    private void setupViewPager(ViewPager viewPager) {
        StationBusAdapter adapter = new StationBusAdapter(getChildFragmentManager());
        adapter.addFragment(recommendRoutesFragment, "Lộ trình");
        adapter.addFragment(instructionFragment, "Cách đi");
        adapter.setTabCount(NUMBER_TAB);
        viewPager.setAdapter(adapter);
    }

    private void setupTabAttribute(final ViewPager viewPager, final TabLayout tabLayout) {
        for (int i = 0; i < NUMBER_TAB; i++) {
            TextView tab = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.custom_tab, null);

            tab.setText(viewPager.getAdapter().getPageTitle(i));
            tab.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);
            tabLayout.getTabAt(i).setCustomView(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                callbacks.showBottomSheetCB();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.getChildAt(1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void changeInstruction(int position) {
        callbacks.drawRouteCB(getRecommendRoutes().get(position).getInstruction());
        instructionFragment.changeInstruction(getRecommendRoutes().get(position).getInstruction());
        viewPager.setCurrentItem(1);
        callbacks.showBottomSheetCB();
    }


    public void changeToInstructionTab() {
        viewPager.setCurrentItem(1);
    }
}
