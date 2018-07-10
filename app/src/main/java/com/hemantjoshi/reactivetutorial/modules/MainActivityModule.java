package com.hemantjoshi.reactivetutorial.modules;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hemantjoshi.reactivetutorial.MainActivity;
import com.hemantjoshi.reactivetutorial.utils.ArticleFavDatabase;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HemantJ on 29/03/18.
 */

@Module
public class MainActivityModule {
    private final MainActivity mainActivity;
    private final ProgressBar mProgressBar;
    private final ImageView mImageView;
    private final ArticleFavDatabase mArticleFavDatabase;

    public MainActivityModule(MainActivity mainActivity, ProgressBar progressBar, ImageView imageView, ArticleFavDatabase articleFavDatabase) {
        this.mainActivity = mainActivity;
        this.mProgressBar = progressBar;
        this.mImageView = imageView;
        this.mArticleFavDatabase = articleFavDatabase;
    }

    @Provides
    public RecyclerViewAdapter recyclerViewAdapter(){
        return new RecyclerViewAdapter(mainActivity, mProgressBar, mImageView, mArticleFavDatabase);
    }
}
