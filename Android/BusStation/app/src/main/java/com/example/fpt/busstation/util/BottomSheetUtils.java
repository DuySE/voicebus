package com.example.fpt.busstation.util;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.example.fpt.busstation.service.AnchorSheetBehavior;

/**
 * Created by cuong on 10/5/2017.
 */

public final class BottomSheetUtils {
    public static void setupViewPager(ViewPager viewPager,View BottomSheet) {

        viewPager.addOnPageChangeListener(new BottomSheetViewPagerListener(viewPager, BottomSheet));
    }

    private static class BottomSheetViewPagerListener extends ViewPager.SimpleOnPageChangeListener {
        private final ViewPager viewPager;
        private final AnchorSheetBehavior<View> behavior;

        private BottomSheetViewPagerListener(ViewPager viewPager, View bottomSheetParent) {
            this.viewPager = viewPager;
            this.behavior = AnchorSheetBehavior.from(bottomSheetParent);
        }

        @Override
        public void onPageSelected(int position) {
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    behavior.invalidateScrollingChild();
                }
            });
        }
    }


}
