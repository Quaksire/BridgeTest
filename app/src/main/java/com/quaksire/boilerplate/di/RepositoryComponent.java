package com.quaksire.boilerplate.di;

import android.content.Context;

import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.module.AppRepositoryModule;
import com.quaksire.model.Pupil;

import javax.inject.Named;

import dagger.Component;

/**
 * Created by Julio.
 */
@Component(
        dependencies = {RetrofitComponent.class},
        modules = {AppRepositoryModule.class})
public interface RepositoryComponent {

    @Named("AppDataStore")
    IRepository<Pupil> repository();
}
