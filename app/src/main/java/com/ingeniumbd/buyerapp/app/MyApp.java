package com.ingeniumbd.buyerapp.app;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Princess on 1/15/2018.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
