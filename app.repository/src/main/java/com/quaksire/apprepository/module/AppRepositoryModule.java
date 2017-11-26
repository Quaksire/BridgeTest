package com.quaksire.apprepository.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.quaksire.apprepository.AppRepository;
import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.db.AppDatabase;
import com.quaksire.apprepository.reposotory.AppLocalDataStore;
import com.quaksire.apprepository.reposotory.AppRemoteDataStore;
import com.quaksire.model.Pupil;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Julio.
 */
@Module
public class AppRepositoryModule {

    private Context mContext;

    public AppRepositoryModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(this.mContext,
                AppDatabase.class,
                "app.database").build();
    }

    @Provides
    @Named("AppLocalDataStore")
    public IRepository<Pupil> provideAppLocalDataStore(AppDatabase appDatabase) {
        return new AppLocalDataStore(appDatabase);
    }

    @Provides
    @Named("AppRemoteDataStore")
    public IRepository<Pupil> provideAppRemoteDataStore(Retrofit retrofit) {
        return new AppRemoteDataStore(retrofit);
    }

    @Provides
    @Named("AppDataStore")
    public IRepository<Pupil> provideRepository(
            @Named("AppLocalDataStore") IRepository<Pupil> appLocalDataStore,
            @Named("AppRemoteDataStore") IRepository<Pupil> appRemoteDataStore) {
        return new AppRepository(appLocalDataStore, appRemoteDataStore);
    }

}
