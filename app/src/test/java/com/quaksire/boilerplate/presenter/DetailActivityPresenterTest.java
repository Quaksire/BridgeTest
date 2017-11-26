package com.quaksire.boilerplate.presenter;

import com.quaksire.applog.LogManager;
import com.quaksire.boilerplate.domain.IPupilsUserCase;
import com.quaksire.boilerplate.interfaces.IDetailActivity;
import com.quaksire.boilerplate.interfaces.IMainActivity;
import com.quaksire.model.Pupil;

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

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class DetailActivityPresenterTest {

    private IDetailActivity mMainActivity;
    private IPupilsUserCase mPupilsUserCase;
    private IDetailActivityPresenter mPresenter;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(LogManager.class);

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

        // Override RxAndroid schedulers
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        this.mMainActivity = Mockito.mock(IDetailActivity.class);
        this.mPupilsUserCase = Mockito.mock(IPupilsUserCase.class);

        this.mPresenter = new DetailActivityPresenter(
                this.mMainActivity,
                this.mPupilsUserCase);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void canDisplayPupil() {
        List<Pupil> pupils = new ArrayList<>();
        pupils.add(new Pupil());

        rx.Observable<List<Pupil>> listObservable =
                rx.Observable.just(pupils)
                        .subscribeOn(Schedulers.io());

        when(this.mPupilsUserCase.get(anyInt())).thenReturn(listObservable);

        this.mPresenter.loadPupilId(1);

        verify(this.mMainActivity).displayPupil(any(Pupil.class));
    }

    @Test
    public void canUpdatePupil() {
        List<Pupil> pupils = new ArrayList<>();
        pupils.add(new Pupil());

        rx.Observable<List<Pupil>> listObservable =
                rx.Observable.just(pupils)
                        .subscribeOn(Schedulers.io());

        when(this.mPupilsUserCase.get(anyInt())).thenReturn(listObservable);

        this.mPresenter.loadPupilId(1);

        verify(this.mMainActivity).displayPupil(any(Pupil.class));

        this.mPresenter.savePupil("Julio", "Spain");

        verify(this.mPupilsUserCase).update(any(Pupil.class));
    }

    @Test
    public void canDeletePupil() {
        List<Pupil> pupils = new ArrayList<>();
        pupils.add(new Pupil());

        rx.Observable<List<Pupil>> listObservable =
                rx.Observable.just(pupils)
                        .subscribeOn(Schedulers.io());

        when(this.mPupilsUserCase.get(anyInt())).thenReturn(listObservable);

        this.mPresenter.loadPupilId(1);

        verify(this.mMainActivity).displayPupil(any(Pupil.class));

        this.mPresenter.deletePupil();

        verify(this.mPupilsUserCase).remove(any(Pupil.class));
    }
}
