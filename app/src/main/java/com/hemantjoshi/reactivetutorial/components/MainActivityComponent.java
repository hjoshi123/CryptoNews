package com.hemantjoshi.reactivetutorial.components;

import com.hemantjoshi.reactivetutorial.MainActivity;
import com.hemantjoshi.reactivetutorial.modules.AppModule;
import com.hemantjoshi.reactivetutorial.modules.MainActivityModule;

import dagger.Component;

/**
 * Created by HemantJ on 29/03/18.
 */
@Component(modules = {MainActivityModule.class, AppModule.class}, dependencies = NewsApiComponent.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
