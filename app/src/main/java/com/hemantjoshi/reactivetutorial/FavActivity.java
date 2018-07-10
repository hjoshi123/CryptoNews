package com.hemantjoshi.reactivetutorial;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hemantjoshi.reactivetutorial.components.DaggerFavActivityComponent;
import com.hemantjoshi.reactivetutorial.components.FavActivityComponent;
import com.hemantjoshi.reactivetutorial.model.ArticleFav;
import com.hemantjoshi.reactivetutorial.modules.FavActivityModule;
import com.hemantjoshi.reactivetutorial.utils.ArticleFavDatabase;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewAdapter;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewFavAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class FavActivity extends AppCompatActivity {
    private ArticleFavDatabase mArticleDatabase;
    @Inject RecyclerViewFavAdapter mAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private List<ArticleFav> fav = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);

        mArticleDatabase = Room.databaseBuilder(getApplicationContext(), ArticleFavDatabase.class, "article-db").build();
        FavActivityComponent favActivityComponent = DaggerFavActivityComponent.builder()
                .favActivityModule(new FavActivityModule(this, mProgressBar, mArticleDatabase))
                .build();

        favActivityComponent.inject(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArticleDatabase.getArticleFavDao().getFavArticles().subscribe(this::handleResponse);
    }

    private void handleResponse(List<ArticleFav> articleFavs) {
        fav = articleFavs;

        mAdapter.setItems(fav);
        runOnUiThread(() -> {
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        });

    }
}
