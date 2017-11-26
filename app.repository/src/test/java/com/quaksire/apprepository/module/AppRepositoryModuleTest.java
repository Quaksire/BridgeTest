package com.quaksire.apprepository.module;

import android.content.Context;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.db.AppDatabase;
import com.quaksire.apprepository.reposotory.AppLocalDataStore;
import com.quaksire.apprepository.reposotory.AppRemoteDataStore;
import com.quaksire.model.Pupil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import retrofit2.Retrofit;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogManager.class, Retrofit.class})
public class AppRepositoryModuleTest {

    private AppRepositoryModule mModule;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.mockStatic(Retrofit.class);
        Context context = Mockito.mock(Context.class);
        this.mModule = new AppRepositoryModule(context);
    }

    @Test
    public void canProvideDatabase() {
        AppDatabase db = this.mModule.provideAppDatabase();

        Assert.assertNotNull(db);
    }

    @Test
    public void canProvideAppLocalDataStore() {
        AppDatabase db = this.mModule.provideAppDatabase();

        IRepository<Pupil> localDataStore = this.mModule.provideAppLocalDataStore(db);

        Assert.assertNotNull(localDataStore);
    }

    @Test
    public void canProvideAppRemoteDataStore() {
        Retrofit retrofit = Mockito.mock(Retrofit.class);

        Assert.assertNotNull(this.mModule.provideAppRemoteDataStore(retrofit));
    }

    @Test
    public void canProvideRepository() {
        IRepository<Pupil> localDataStore = Mockito.mock(AppLocalDataStore.class);
        IRepository<Pupil> remoteDataStore = Mockito.mock(AppRemoteDataStore.class);

        Assert.assertNotNull(this.mModule.provideRepository(localDataStore, remoteDataStore));


    }

}
