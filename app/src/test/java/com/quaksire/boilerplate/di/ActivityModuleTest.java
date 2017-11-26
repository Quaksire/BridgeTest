package com.quaksire.boilerplate.di;

import android.content.Context;

import com.quaksire.boilerplate.interfaces.IMainActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
public class ActivityModuleTest {

    ActivityModule mActivityModule;

    @Mock
    IMainActivity mActivity;

    @Mock
    Context mContext;

    @Before
    public void setUp() {
        this.mActivityModule = new ActivityModule(this.mContext, this.mActivity);
    }

    @After
    public void tearDown() {
        this.mActivityModule = null;
    }

    @Test
    public void canProvidePresenter() {
        Assert.assertNotNull(this.mActivityModule.provideMainPresenter(null));
    }
}
