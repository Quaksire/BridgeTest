package com.quaksire.boilerplate;

import android.support.test.espresso.IdlingPolicies;

import com.quaksire.appnet.EndPoint;
import com.quaksire.apprepository.db.AppDatabase;
import com.quaksire.boilerplate.util.RestServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by Julio.
 */

public class BaseScenario {

    public static final String LIST_PUPILS_200 = "response_200.json";

    public static final String RESPONSE_EMPTY = "response_empty.json";

    /**
     * MockWebServer - Test will request all information from a mock server
     */
    private MockWebServer server;

    @Before
    public void setUp() throws IOException {


        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);

        this.server = new MockWebServer();
        this.server.start();
        EndPoint.baseUrl = this.server.url("/").toString();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    //=======================================================
    // Server methods
    //=======================================================

    protected void setResponse200ToServer(String filename, int code) throws Exception {
        this.server.enqueue(new MockResponse()
                .setResponseCode(code)
                .setBody(RestServiceTestHelper.getStringFromFile(
                        getInstrumentation().getContext(),
                        filename)));
    }
}
