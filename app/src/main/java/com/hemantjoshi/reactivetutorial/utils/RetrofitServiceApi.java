package com.hemantjoshi.reactivetutorial.utils;

import com.hemantjoshi.reactivetutorial.model.Article;
import com.hemantjoshi.reactivetutorial.model.ArticleResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HemantJ on 06/01/18.
 */

public interface RetrofitServiceApi {

    @GET("top-headlines")
    Flowable<ArticleResponse> queryReddit(@Query("sources") String sources);
}
