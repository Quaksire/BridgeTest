package com.quaksire.boilerplate.di;

import android.content.Context;

import com.quaksire.apprepository.IRepository;
import com.quaksire.boilerplate.domain.IPupilsUserCase;
import com.quaksire.boilerplate.domain.PupilsUserCase;
import com.quaksire.boilerplate.interfaces.IDetailActivity;
import com.quaksire.boilerplate.presenter.DetailActivityPresenter;
import com.quaksire.boilerplate.presenter.IDetailActivityPresenter;
import com.quaksire.model.Pupil;
import com.squareup.picasso.Picasso;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Julio.
 */
@Module
public class DetailActivyModule {

    private IDetailActivity mActivity;

    private Context mContext;

    public DetailActivyModule(Context context, IDetailActivity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }

    @Provides
    IDetailActivityPresenter provideMainPresenter(
            IPupilsUserCase pupilsUserCase) {
        return new DetailActivityPresenter(this.mActivity, pupilsUserCase);
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
