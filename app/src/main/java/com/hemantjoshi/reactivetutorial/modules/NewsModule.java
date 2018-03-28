package com.hemantjoshi.reactivetutorial.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hemantjoshi.reactivetutorial.utils.RetrofitServiceApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HemantJ on 28/03/18.
 */

@Module(includes = OkHttpClientModule.class)
public class NewsModule {

    @Provides
    public RetrofitServiceApi retrofitServiceApi(Retrofit retrofit){
        return retrofit.create(RetrofitServiceApi.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                             GsonConverterFactory gsonConverterFactory, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public RxJava2CallAdapterFactory rxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }
}
