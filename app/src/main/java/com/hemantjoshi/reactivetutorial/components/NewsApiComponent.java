package com.hemantjoshi.reactivetutorial.components;

import com.hemantjoshi.reactivetutorial.modules.NewsModule;
import com.hemantjoshi.reactivetutorial.utils.RetrofitServiceApi;

import dagger.Component;

/**
 * Created by HemantJ on 28/03/18.
 */

@Component(modules = {NewsModule.class})
public interface NewsApiComponent {
    RetrofitServiceApi getRetrofitServiceApi();
}
