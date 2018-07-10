package com.hemantjoshi.reactivetutorial.components;

import com.hemantjoshi.reactivetutorial.FavActivity;
import com.hemantjoshi.reactivetutorial.modules.AppModule;
import com.hemantjoshi.reactivetutorial.modules.FavActivityModule;

import dagger.Component;

@Component(modules = {FavActivityModule.class, AppModule.class})
public interface FavActivityComponent {
    void inject(FavActivity favActivity);
}
