package com.quaksire.applog;

import android.util.Log;

/**
 * Created by Julio Domingo
 */

public class LogManager {


    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, e.getMessage(), e);
        }
    }

}
