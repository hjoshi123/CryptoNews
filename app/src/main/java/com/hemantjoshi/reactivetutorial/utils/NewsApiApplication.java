package com.hemantjoshi.reactivetutorial.utils;

import android.app.Activity;
import android.app.Application;

import com.hemantjoshi.reactivetutorial.components.DaggerNewsApiComponent;
import com.hemantjoshi.reactivetutorial.components.NewsApiComponent;
import com.hemantjoshi.reactivetutorial.modules.ContextModule;

import timber.log.Timber;

/**
 * Created by HemantJ on 29/03/18.
 */

public class NewsApiApplication extends Application {
    private NewsApiComponent mNewsApiApplicationComponent;

    public static NewsApiApplication get(Activity activity){
        return (NewsApiApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        mNewsApiApplicationComponent = DaggerNewsApiComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public NewsApiComponent getmNewsApiApplicationComponent(){
        return mNewsApiApplicationComponent;
    }
}
