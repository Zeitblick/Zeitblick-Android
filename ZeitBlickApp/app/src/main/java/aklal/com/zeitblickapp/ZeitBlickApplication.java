package aklal.com.zeitblickapp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


/**
 * Created by Aklal on 05.11.16.
 */

public class ZeitBlickApplication extends Application {
    // Updated your class body:
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}