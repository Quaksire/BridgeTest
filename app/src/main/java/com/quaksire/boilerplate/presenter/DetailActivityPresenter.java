package com.quaksire.boilerplate.presenter;

import com.quaksire.applog.LogManager;
import com.quaksire.boilerplate.domain.IPupilsUserCase;
import com.quaksire.boilerplate.interfaces.IDetailActivity;
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

public class DetailActivityPresenter implements IDetailActivityPresenter {

    private final String TAG = "APP-DetailActivityPresenter";

    /**
     * Retrofit client
     */
    private final IPupilsUserCase mPupilsUserCase;

    /**
     * Activity interface
     */
    private final IDetailActivity mActivity;

    /**
     * Pupil displayed on the activity
     */
    private Pupil mPupil;

    @Inject
    public DetailActivityPresenter(
            IDetailActivity activity,
            IPupilsUserCase pupilsUserCase) {
        LogManager.i(TAG, "Constructor()");
        this.mActivity = activity;
        this.mPupilsUserCase = pupilsUserCase;
    }

    @Override
    public void loadPupilId(int id) {
        this.mPupilsUserCase.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Pupil>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<Pupil> pupils) {
                    if(pupils == null || pupils.isEmpty()) {
                        mActivity.displayError("Error loading pupil");
                    } else {
                        mPupil = pupils.get(0);
                        mActivity.displayPupil(mPupil);
                    }
                }
            });
    }

    @Override
    public void deletePupil() {
        this.mPupilsUserCase.remove(this.mPupil);
    }

    @Override
    public void savePupil(String name, String country) {
        this.mPupil.name = name;
        this.mPupil.country = country;

        this.mPupilsUserCase.update(this.mPupil);
    }
}
