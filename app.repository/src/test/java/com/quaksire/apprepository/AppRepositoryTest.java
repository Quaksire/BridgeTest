package com.quaksire.apprepository;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.entity.PupilEntity;
import com.quaksire.apprepository.reposotory.AppLocalDataStore;
import com.quaksire.apprepository.reposotory.AppRemoteDataStore;
import com.quaksire.apprepository.specification.ISpecification;
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

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class AppRepositoryTest {

    private IRepository<Pupil> mLocalRepository;
    private IRepository<Pupil> mRemoteRepository;

    private AppRepository mRepository;

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

        this.mLocalRepository = Mockito.mock(AppLocalDataStore.class);
        this.mRemoteRepository = Mockito.mock(AppRemoteDataStore.class);

        PowerMockito.mockStatic(LogManager.class);

        this.mRepository = new AppRepository(this.mLocalRepository, this.mRemoteRepository);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
    }

    @Test
    public void canInstantiate() {
        Assert.assertNotNull(this.mRepository);
    }

    @Test
    public void canAddNewPupil() {
        this.mRepository.add(new Pupil());

        verify(this.mLocalRepository).add(any(Pupil.class));
    }

    @Test
    public void canAddNewListOfPupil() {
        List<Pupil> pupilList = new ArrayList<>();
        pupilList.add(new Pupil());
        this.mRepository.add(pupilList);

        verify(this.mLocalRepository).add(pupilList);
    }

    @Test
    public void canUpdatePupil() {
        this.mRepository.update(new Pupil());

        verify(this.mLocalRepository).update(any(Pupil.class));
        verify(this.mRemoteRepository).update(any(Pupil.class));
    }

    @Test
    public void canDeletePupil() {
        this.mRepository.remove(new Pupil());

        verify(this.mLocalRepository).remove(any(Pupil.class));
        verify(this.mRemoteRepository).remove(any(Pupil.class));
    }

    @Test
    public void canDeleteQueryPupil() throws Exception {
        this.mRepository.remove(new ISpecification() {
            @Override
            public String whereClause() {
                return "";
            }

            @Override
            public int id() {
                return 0;
            }
        });

        verify(this.mLocalRepository).remove(any(ISpecification.class));
        verify(this.mRemoteRepository).remove(any(ISpecification.class));
    }

    @Test
    public void canQueryEmptyPupils() {
        List<Pupil> pupils = new ArrayList<>();

        when(this.mRemoteRepository.query(any(ISpecification.class))).thenReturn(
            Observable.just(pupils).first()
        );

        ISpecification specification = new ISpecification() {
            @Override
            public String whereClause() {
                return ISpecification.ALL;
            }

            @Override
            public int id() {
                return 0;
            }
        };

        this.mRepository.query(specification);

        verify(this.mLocalRepository).query(specification);
        verify(this.mRemoteRepository, atLeast(2)).query(specification);
    }
}
