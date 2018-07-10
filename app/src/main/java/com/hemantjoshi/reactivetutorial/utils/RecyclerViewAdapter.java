package com.hemantjoshi.reactivetutorial.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hemantjoshi.reactivetutorial.R;
import com.hemantjoshi.reactivetutorial.model.Article;
import com.hemantjoshi.reactivetutorial.model.ArticleFav;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by HemantJ on 09/01/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> mArticles = new ArrayList<>();
    private Activity mActivity;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private ArticleFavDatabase mArticleDatabase;

    public RecyclerViewAdapter(Activity activity, ProgressBar progressBar, ImageView imageView, ArticleFavDatabase articleFavDatabase){
        this.mActivity = activity;
        this.mImageView = imageView;
        this.mProgressBar = progressBar;
        this.mArticleDatabase = articleFavDatabase;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        AtomicReference<List<ArticleFav>> articleFav = null;
        Article article = mArticles.get(position);
        mProgressBar.setVisibility(View.VISIBLE);

        if (position == 0) {
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
            Picasso.with(mActivity)
                    .load(article.getUrlToImage())
                    .into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Timber.d("Some error is thrown");
                            Toast.makeText(mActivity, "Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
            getItemCount();
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            TextView title = holder.title;
            title.setText(article.getTitle());

            ImageView image = holder.image;
            Picasso.with(mActivity)
                .load(article.getUrlToImage())
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Timber.d("Some error is thrown");
                        Toast.makeText(mActivity, "Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            holder.box.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    ArticleFav fav = new ArticleFav();
                    fav.setDescription(article.getDescription());
                    fav.setTitle(article.getTitle());
                    fav.setUrl(article.getUrl());
                    fav.setCheckbox(true);
                    Completable.fromAction(() -> mArticleDatabase.getArticleFavDao().insertArticleIntoFav(fav))
                        .subscribeOn(Schedulers.io())
                        .subscribe();
                } else {
                    Completable.fromAction(() -> mArticleDatabase.getArticleFavDao().delete(article.getTitle()))
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                }
            });
        }

    }

    public void setItems(ArrayList<Article> articles) {
        if (!articles.isEmpty())
            this.mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        CheckBox box;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            box = itemView.findViewById(R.id.favClick);
        }
    }
}
