package com.micky.commonproj.presenter.impl;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.micky.commonlib.utils.Constants;
import com.micky.commonproj.BaseApplication;
import com.micky.commonproj.domain.model.WeatherResult;
import com.micky.commonproj.domain.service.response.WeatherResponse;
import com.micky.commonproj.presenter.MainPresenter;
import com.micky.commonproj.ui.view.MainView;
import com.micky.commonproj.R;
import com.micky.commonproj.domain.service.ServiceManager;
import com.micky.commonproj.domain.service.response.GetIpInfoResponse;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Project RetrofitRxAndroidDragger2
 * @Packate com.micky.commonproj.presenter
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2015-12-22 14:33
 * @Version 1.0
 */
public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {
    private static final String TAG = "TAG";
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
    }

    @Override
    public void getWeatherData(String place) {
        if (TextUtils.isEmpty(place)) {
            return;
        }
        mMainView.showProgress();
        ServiceManager.getInstance().getApiService().getWeatherInfo(place, Constants.BAIDU_AK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                        mMainView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage(), e);
                    }

                    @Override
                    public void onNext(WeatherResponse weatherResponse) {
                        mMainView.setupData(weatherResponse);
                    }
                });
    }
}
