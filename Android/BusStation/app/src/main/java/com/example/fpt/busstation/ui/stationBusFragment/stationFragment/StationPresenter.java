package com.example.fpt.busstation.ui.stationBusFragment.stationFragment;

import com.example.fpt.busstation.ui.base.BasePresenter;

/**
 * Created by Vi Nguyen 31/10/2017.
 */

public class StationPresenter <T extends StationMvpView> extends BasePresenter<T> implements StationMvpPresenter <T> {
    public StationPresenter() {
        super();
    }
}
