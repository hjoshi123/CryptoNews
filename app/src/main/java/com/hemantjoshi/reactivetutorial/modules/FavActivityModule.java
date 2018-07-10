package com.hemantjoshi.reactivetutorial.modules;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hemantjoshi.reactivetutorial.FavActivity;
import com.hemantjoshi.reactivetutorial.utils.ArticleFavDatabase;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewAdapter;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewFavAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class FavActivityModule {
    private final FavActivity mFavActivity;
    private final ProgressBar mProgressBar;
    private final ArticleFavDatabase mArticleFavDatabase;

    public FavActivityModule(FavActivity favActivity, ProgressBar progressBar, ArticleFavDatabase articleFavDatabase) {
        this.mFavActivity = favActivity;
        this.mProgressBar = progressBar;
        this.mArticleFavDatabase = articleFavDatabase;
    }

    @Provides
    public RecyclerViewFavAdapter recyclerViewFavAdapter(){
        return new RecyclerViewFavAdapter(mFavActivity, mProgressBar, mArticleFavDatabase);
    }
}
