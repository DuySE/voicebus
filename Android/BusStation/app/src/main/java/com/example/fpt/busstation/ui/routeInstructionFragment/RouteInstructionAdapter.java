package com.example.fpt.busstation.ui.routeInstructionFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fpt.busstation.ui.routeInstructionFragment.instructionFragment.InstructionFragment;
import com.example.fpt.busstation.ui.routeInstructionFragment.routeFragment.RecommendRoutesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vi Nguyen on 24/10/2017.
 */

public class RouteInstructionAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;
    private RecommendRoutesFragment recommendRoutesFragment;
    private InstructionFragment instructionFragment;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();


    public RouteInstructionAdapter(FragmentManager fragmentManager, RecommendRoutesFragment recommendRoutesFragment, InstructionFragment instructionFragment) {
        super(fragmentManager);
        this.mTabCount = 0;
        this.recommendRoutesFragment = recommendRoutesFragment;
        this.instructionFragment = instructionFragment;
    }

    public RouteInstructionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTabCount;

    }

    public void setCount(int mTabCount) {
        this.mTabCount = mTabCount;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String tabTitle) {
        fragmentList.add(fragment);
        fragmentTitleList.add(tabTitle);
    }

}
