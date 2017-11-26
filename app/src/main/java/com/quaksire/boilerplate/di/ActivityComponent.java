package com.quaksire.boilerplate.di;

import com.quaksire.boilerplate.MainActivity;

import dagger.Component;

/**
 * Created by Julio.
 */

@Component(
        dependencies = {RetrofitComponent.class, RepositoryComponent.class},
        modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
