package com.example.fpt.busstation.ui.base;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by cuong on 9/11/2017.
 */

public interface BaseMvpView {
    void showLoading();

    void hideLoading();

    void onError(String message);

    void showMessage(String message);

    boolean isNetworkConnected();

    boolean isServiceRunning(Class<?> serviceClass);

    void requestPermissionsSafely(String[] permissions, int requestCode);

    boolean hasPermission(String permission);
    void hideKeyboard();

}
