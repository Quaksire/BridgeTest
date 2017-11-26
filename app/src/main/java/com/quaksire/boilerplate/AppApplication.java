package com.quaksire.boilerplate;

import android.app.Application;

import com.quaksire.appnet.EndPoint;
import com.quaksire.appnet.RetrofitModule;
import com.quaksire.apprepository.module.AppRepositoryModule;
import com.quaksire.boilerplate.di.ActivityComponent;
import com.quaksire.boilerplate.di.ActivityModule;
import com.quaksire.boilerplate.di.DaggerActivityComponent;
import com.quaksire.boilerplate.di.DaggerDetailComponent;
import com.quaksire.boilerplate.di.DaggerRepositoryComponent;
import com.quaksire.boilerplate.di.DaggerRetrofitComponent;
import com.quaksire.boilerplate.di.DetailActivyModule;
import com.quaksire.boilerplate.di.DetailComponent;
import com.quaksire.boilerplate.di.RepositoryComponent;
import com.quaksire.boilerplate.di.RetrofitComponent;
import com.quaksire.boilerplate.interfaces.IDetailActivity;
import com.quaksire.boilerplate.interfaces.IMainActivity;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Julio.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initMemoryLeakDetection();
    }

    private void initMemoryLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public ActivityComponent getActivityComponent(IMainActivity activity) {
        RetrofitComponent retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(EndPoint.baseUrl))
                .build();

        RepositoryComponent repositoryComponent = DaggerRepositoryComponent
                .builder()
                .appRepositoryModule(new AppRepositoryModule(this))
                .retrofitComponent(retrofitComponent)
                .build();

        return DaggerActivityComponent
                .builder()
                .activityModule(new ActivityModule(this, activity))
                .repositoryComponent(repositoryComponent)
                .retrofitComponent(retrofitComponent)
                .build();
    }

    public DetailComponent getDetailComponent(IDetailActivity activity) {
        RetrofitComponent retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(EndPoint.baseUrl))
                .build();

        RepositoryComponent repositoryComponent = DaggerRepositoryComponent
                .builder()
                .appRepositoryModule(new AppRepositoryModule(this))
                .retrofitComponent(retrofitComponent)
                .build();

        return DaggerDetailComponent
                .builder()
                .detailActivyModule(new DetailActivyModule(this, activity))
                .repositoryComponent(repositoryComponent)
                .retrofitComponent(retrofitComponent)
                .build();
    }
}
