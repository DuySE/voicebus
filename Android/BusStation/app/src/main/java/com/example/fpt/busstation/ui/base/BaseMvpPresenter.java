package com.example.fpt.busstation.ui.base;

/**
 * Created by cuong on 9/11/2017.
 */

public interface BaseMvpPresenter<T extends BaseMvpView> {
    void onAttach(T mvpView);
    void onDetach();
}
