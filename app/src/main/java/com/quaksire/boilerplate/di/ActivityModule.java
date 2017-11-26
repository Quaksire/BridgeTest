package com.quaksire.boilerplate.di;

import android.content.Context;

import com.quaksire.apprepository.IRepository;
import com.quaksire.boilerplate.domain.IPupilsUserCase;
import com.quaksire.boilerplate.domain.PupilsUserCase;
import com.quaksire.boilerplate.interfaces.IMainActivity;
import com.quaksire.boilerplate.presenter.IMainActivityPresenter;
import com.quaksire.boilerplate.presenter.MainActivityPresenter;
import com.quaksire.model.Pupil;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Julio.
 */

@Module
public class ActivityModule {

    private IMainActivity mActivity;

    private Context mContext;

    public ActivityModule(Context context, IMainActivity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }

    @Provides
    IMainActivityPresenter provideMainPresenter(
            IPupilsUserCase pupilsUserCase) {
        return new MainActivityPresenter(this.mActivity, pupilsUserCase);
    }

    @Provides
    IPupilsUserCase providePupilsUserCase(@Named("AppDataStore") IRepository<Pupil> repository) {
        return new PupilsUserCase(repository);
    }

    @Provides
    Picasso providePicasso() {
        return Picasso.with(this.mContext);
    }
}
