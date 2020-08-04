package com.example.instagramclone;
import com.parse.Parse;
import com.parse.ParseACL;
import android.app.Application;


// This class connects to the parse server
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                // if desired
                .clientKey("HDPmVioEP5d5")
                .server("http://13.244.74.78/parse/")
                .build()
        );

        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}