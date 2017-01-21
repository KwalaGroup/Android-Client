package com.kwala.app;

import android.app.Application;

/**
 * @author jacobamuchow@gmail.com
 */
public class KwalaApplication extends Application {
    private static final String TAG = KwalaApplication.class.getSimpleName();

    private static KwalaApplication application;

    public static KwalaApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
