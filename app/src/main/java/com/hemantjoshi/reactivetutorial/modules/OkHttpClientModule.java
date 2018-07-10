package com.hemantjoshi.reactivetutorial.modules;

import android.content.Context;

import com.hemantjoshi.reactivetutorial.BuildConfig;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module(includes = ContextModule.class)
public class OkHttpClientModule {

    @Provides
    public OkHttpClient okHttpClient(Cache cache,
                                     HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(chain -> {
                    final Request originalRequest = chain.request();
                    final HttpUrl originalUrl = originalRequest.url();

                    final HttpUrl url = originalUrl.newBuilder()
                            .addQueryParameter("apiKey", BuildConfig.NewsApiKey)
                            .build();
                    final Request.Builder requestBuilder = originalRequest.newBuilder().url(url);
                    final Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @Provides
    public File file(Context context){
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.d(message));

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
