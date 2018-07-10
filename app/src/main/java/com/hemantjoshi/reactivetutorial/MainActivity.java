package com.hemantjoshi.reactivetutorial;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hemantjoshi.reactivetutorial.components.DaggerMainActivityComponent;
import com.hemantjoshi.reactivetutorial.components.MainActivityComponent;
import com.hemantjoshi.reactivetutorial.model.Article;
import com.hemantjoshi.reactivetutorial.model.ArticleFav;
import com.hemantjoshi.reactivetutorial.model.ArticleResponse;
import com.hemantjoshi.reactivetutorial.modules.AppModule;
import com.hemantjoshi.reactivetutorial.modules.MainActivityModule;
import com.hemantjoshi.reactivetutorial.utils.ArticleFavDatabase;
import com.hemantjoshi.reactivetutorial.utils.NewsApiApplication;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewAdapter;
import com.hemantjoshi.reactivetutorial.utils.RetrofitServiceApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private List<Article> mArticleList = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    @Inject RetrofitServiceApi mService;
    @Inject RecyclerViewAdapter mAdapter;
    private ArticleFavDatabase mArticleFavDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mToolbar = findViewById(R.id.toolbar);
        mProgressBar = findViewById(R.id.progressBar);
        mImageView = findViewById(R.id.imageView);
        mArticleFavDatabase = Room.databaseBuilder(getApplicationContext(), ArticleFavDatabase.class, "article-db").build();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("CryptoCoin");
        initViews();
        mCompositeDisposable = new CompositeDisposable();
        Timber.plant(new Timber.DebugTree());
        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this, mProgressBar, mImageView, mArticleFavDatabase))
                .appModule(new AppModule(getApplication()))
                .newsApiComponent(NewsApiApplication.get(this).getmNewsApiApplicationComponent())
                .build();

        mainActivityComponent.inject(this);

        loadJSON();
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadJSON() {
        mCompositeDisposable.add(mService.queryReddit("crypto-coins-news")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
    }

    @SuppressLint("CheckResult")
    private void handleResponse(ArticleResponse articleResponse) {
        AtomicReference<List<ArticleFav>> articleFav = null;
        mArticleList = articleResponse.getArticles();
        Timber.d(String.valueOf(mArticleList.size()));
        mAdapter.setItems((ArrayList<Article>) mArticleList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();
    }

    private void handleError(Throwable throwable) {
        Timber.d(throwable.getLocalizedMessage());
        Toast.makeText(this,"Error in accessing JSON" + throwable.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fav) {
            startActivity(new Intent(MainActivity.this, FavActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mArticleList.isEmpty())
            loadJSON();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }
}
