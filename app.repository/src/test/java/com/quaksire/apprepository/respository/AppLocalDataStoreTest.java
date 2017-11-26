package com.quaksire.apprepository.respository;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.dao.PupilDAO;
import com.quaksire.apprepository.db.AppDatabase;
import com.quaksire.apprepository.entity.PupilEntity;
import com.quaksire.apprepository.reposotory.AppLocalDataStore;
import com.quaksire.model.Pupil;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class AppLocalDataStoreTest {

    private AppDatabase db;

    private AppLocalDataStore mLocalRepository;

    @Before
    public void setUp() {
        //RxJava schedulers
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        RxJavaHooks.setOnComputationScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        RxJavaHooks.setOnNewThreadScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        PupilDAO pupilDAO = Mockito.mock(PupilDAO.class);
        this.db = Mockito.mock(AppDatabase.class);
        when(this.db.pupilDAO()).thenReturn(pupilDAO);

        PowerMockito.mockStatic(LogManager.class);

        this.mLocalRepository = new AppLocalDataStore(this.db);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
    }

    @Test
    public void canInstantiate() {
        Assert.assertNotNull(this.mLocalRepository);
    }

    @Test
    public void canAdd() {
        this.mLocalRepository.add(new Pupil());

        verify(this.db.pupilDAO()).insert(any(PupilEntity.class));
    }

    @Test
    public void canUpdate() {
        this.mLocalRepository.update(new Pupil());
        verify(this.db.pupilDAO()).update(any(PupilEntity.class));
    }

    @Test
    public void canDelete() {
        this.mLocalRepository.remove(new Pupil());
        verify(this.db.pupilDAO()).delete(any(PupilEntity.class));
    }
}
