package com.quaksire.boilerplate.presenter;

import com.quaksire.applog.LogManager;
import com.quaksire.boilerplate.domain.IPupilsUserCase;
import com.quaksire.boilerplate.interfaces.IMainActivity;
import com.quaksire.model.Pupil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Julio.
 */

public class MainActivityPresenter
        extends Subscriber<List<Pupil>>
        implements IMainActivityPresenter {

    private final String TAG = "APP-MainActivityPresenter";

    /**
     * Retrofit client
     */
    private final IPupilsUserCase mPupilsUserCase;

    /**
     * Activity interface
     */
    private final IMainActivity mActivity;

    @Inject
    public MainActivityPresenter(
            IMainActivity mainActivity,
            IPupilsUserCase pupilsUserCase) {
        LogManager.i(TAG, "Constructor()");
        this.mActivity = mainActivity;
        this.mPupilsUserCase = pupilsUserCase;
    }

    ///=========================================================
    /// IMainActivityPresenter implementation
    ///=========================================================

    @Override
    public void loadPage(int page) {
        LogManager.i(TAG, "loadPage()");
        LogManager.d(TAG, "loadPage() - Page: " + page);
        this.mPupilsUserCase.getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    ///=========================================================
    /// Subscriber implementation
    ///=========================================================

    @Override
    public void onCompleted() {
        LogManager.i(TAG, "onCompleted()");
    }

    @Override
    public void onNext(List<Pupil> pupils)  {
        LogManager.i(TAG, "onNext()");
        if(pupils == null) {
            LogManager.d(TAG, "onNext() - Pupils list == null");
            this.mActivity.displayError("Cannot find pupils");
        } else {
            LogManager.d(TAG, "onNext() - Pupils list != null");
            this.mActivity.displayItems(pupils);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogManager.i(TAG, "onError");
        LogManager.e(TAG, e);
        this.mActivity.displayError(e.getMessage());
    }
}
