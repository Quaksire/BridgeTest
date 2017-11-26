package com.quaksire.boilerplate.presenter;

import android.database.Observable;

import com.quaksire.applog.LogManager;
import com.quaksire.boilerplate.domain.IPupilsUserCase;
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

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class MainActivityPresenterTest {

    private IMainActivity mMainActivity;
    private IPupilsUserCase mPupilsUserCase;
    private IMainActivityPresenter mMainActivityPresenter;

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

        this.mMainActivity = Mockito.mock(IMainActivity.class);
        this.mPupilsUserCase = Mockito.mock(IPupilsUserCase.class);

        this.mMainActivityPresenter = new MainActivityPresenter(
                this.mMainActivity,
                this.mPupilsUserCase);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void canGetAll() {
        List<Pupil> pupils = new ArrayList<>();

        rx.Observable<List<Pupil>> listObservable =
                rx.Observable.just(pupils)
                        .subscribeOn(Schedulers.io());

        when(this.mPupilsUserCase.getAll()).thenReturn(listObservable);

        this.mMainActivityPresenter.loadPage(1);

        verify(this.mMainActivity).displayItems(anyList());
    }

    @Test
    public void canDisplayErrorMessage() {
        List<Pupil> pupils = null;

        rx.Observable<List<Pupil>> listObservable =
                rx.Observable.just(pupils)
                        .subscribeOn(Schedulers.io());

        when(this.mPupilsUserCase.getAll()).thenReturn(listObservable);

        this.mMainActivityPresenter.loadPage(1);

        verify(this.mMainActivity).displayError("Cannot find pupils");
    }
}
