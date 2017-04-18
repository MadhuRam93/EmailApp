package com.example.madhu.emailapp_original;

import android.app.Application;
import com.firebase.client.Firebase;

/**
 * Created by madhu on 15-04-2017.
 * Creates only one Database Context
 */

public class EmailApp_Original extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
