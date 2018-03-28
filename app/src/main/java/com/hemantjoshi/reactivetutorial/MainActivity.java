package com.hemantjoshi.reactivetutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hemantjoshi.reactivetutorial.components.DaggerMainActivityComponent;
import com.hemantjoshi.reactivetutorial.components.MainActivityComponent;
import com.hemantjoshi.reactivetutorial.model.Article;
import com.hemantjoshi.reactivetutorial.model.ArticleResponse;
import com.hemantjoshi.reactivetutorial.modules.MainActivityModule;
import com.hemantjoshi.reactivetutorial.utils.NewsApiApplication;
import com.hemantjoshi.reactivetutorial.utils.RecyclerViewAdapter;
import com.hemantjoshi.reactivetutorial.utils.RetrofitServiceApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private List<Article> articleList = new ArrayList<>();
    private CompositeDisposable compositeDisposable;
    @Inject RetrofitServiceApi mService;
    @Inject RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        compositeDisposable = new CompositeDisposable();
        Timber.plant(new Timber.DebugTree());
        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .newsApiComponent(NewsApiApplication.get(this).getmNewsApiApplicationComponent())
                .build();

        mainActivityComponent.inject(this);

        loadJSON();
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (articleList.isEmpty())
            loadJSON();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void loadJSON() {
        compositeDisposable.add(mService.queryReddit("reddit-r-all")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(ArticleResponse articleResponse) {
        articleList = articleResponse.getArticles();
        Log.d("MainAc",String.valueOf(articleList.size()));
        mAdapter.setItems((ArrayList<Article>) articleList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void handleError(Throwable throwable) {
        Log.d("MainActivity",throwable.getMessage());
        Toast.makeText(this,"Error in accessing JSON" + throwable.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
    }
}
