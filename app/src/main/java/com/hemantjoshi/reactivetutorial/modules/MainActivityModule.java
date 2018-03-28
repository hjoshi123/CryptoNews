package com.hemantjoshi.reactivetutorial.modules;

import com.hemantjoshi.reactivetutorial.MainActivity;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HemantJ on 29/03/18.
 */

@Module
public class MainActivityModule {
    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    public RecyclerViewAdapter recyclerViewAdapter(){
        return new RecyclerViewAdapter(mainActivity);
    }
}
