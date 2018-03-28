package com.hemantjoshi.reactivetutorial.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HemantJ on 28/03/18.
 */

@Module
public class ContextModule {
    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    public Context context(){
        return context.getApplicationContext();
    }
}
