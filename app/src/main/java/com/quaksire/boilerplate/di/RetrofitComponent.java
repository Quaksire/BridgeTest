package com.quaksire.boilerplate.di;

import com.quaksire.appnet.RetrofitModule;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Julio.
 */

@Component(modules = {RetrofitModule.class})
public interface RetrofitComponent {

    Retrofit retrofit();
}
