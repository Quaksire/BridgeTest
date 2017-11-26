package com.quaksire.appnet;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Julio.
 */
@Module
public class RetrofitModule {

    /**
     * Server url
     */
    private final String mUrl;

    public RetrofitModule(String url) {
        this.mUrl = url;
    }

    /**
     * Create new Retrofit client
     * @param client Http client
     * @param gsonConverterFactory Json converter to model
     * @param rxJavaCallAdapterFactory rxJava adapter
     * @return Retrofit client
     */
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory gsonConverterFactory,
                                    RxJavaCallAdapterFactory rxJavaCallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(this.mUrl)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();
    }

    /**
     * Create Http client with the next specifications
     * Connection timeout: 30 seconds
     * Read timeout: 5 seconds
     * Write timeout: 5 seconds
     * @return Http client
     */
    @Provides
    public OkHttpClient provideClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Create new RxJavaCallAdapter
     * @return RxJavaCallAdapter instance
     */
    @Provides
    public RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    /**
     * Create new GsonConverterFactory
     * @param gson Gson instance
     * @return new GsonConverterFactory
     */
    @Provides
    public GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    /**
     * Create new Gson object to be injected
     * @return new Gson instance
     */
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}
