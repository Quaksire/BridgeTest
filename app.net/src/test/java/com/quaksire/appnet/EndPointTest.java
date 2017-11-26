package com.quaksire.appnet;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Julio.
 */

public class EndPointTest {

    @Test
    public void isEndPointCorrect() {
        Assert.assertEquals("https://bridgetechnicaltest.azurewebsites.net", EndPoint.baseUrl);
    }
}
