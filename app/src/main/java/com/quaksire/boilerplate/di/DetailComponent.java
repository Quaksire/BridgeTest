package com.quaksire.boilerplate.di;

import com.quaksire.boilerplate.DetailActivity;

import dagger.Component;

/**
 * Created by Julio.
 */

@Component(
        dependencies = {RetrofitComponent.class, RepositoryComponent.class},
        modules = DetailActivyModule.class)
public interface DetailComponent {

    void inject(DetailActivity mainActivity);
}
