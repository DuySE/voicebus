package com.example.fpt.busstation.ui.base;

/**
 * Created by cuong on 9/11/2017.
 */

public class BasePresenter<T extends BaseMvpView> implements BaseMvpPresenter<T> {
    private static final String TAG = "BasePresenter";
    private T mMvpView;
    public BasePresenter(){

    }
    @Override
    public void onAttach(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
