package com.quaksire.appnet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by Julio.
 *
 * Retrofit module unit test
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
public class RetrofitModuleTest {

    RetrofitModule mRetrofitModule;

    @Before
    public void setUp() {
        this.mRetrofitModule = new RetrofitModule("http://test");
    }

    @After
    public void tearDown() {
        this.mRetrofitModule = null;
    }

    @Test
    public void canProvideGson() {
        Assert.assertNotNull(this.mRetrofitModule.provideGson());
    }

    @Test
    public void canProvideRxJavaCallAdapterFactory() {
        Assert.assertNotNull(this.mRetrofitModule.provideRxJavaCallAdapterFactory());
    }

    @Test
    public void canProvideGsonConverterFactory() {
        Assert.assertNotNull(this.mRetrofitModule.provideGsonConverterFactory(
                this.mRetrofitModule.provideGson()
        ));
    }

    @Test
    public void canProvideClient() {
        Assert.assertNotNull(this.mRetrofitModule.provideClient());
    }

    @Test
    public void canProvideRetrofit() {
        Assert.assertNotNull(this.mRetrofitModule.provideRetrofit(
                this.mRetrofitModule.provideClient(),
                this.mRetrofitModule.provideGsonConverterFactory(
                        this.mRetrofitModule.provideGson()
                ),
                this.mRetrofitModule.provideRxJavaCallAdapterFactory()
        ));
    }
}
