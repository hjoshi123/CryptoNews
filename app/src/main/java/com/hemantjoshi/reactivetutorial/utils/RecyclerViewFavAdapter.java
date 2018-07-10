package com.hemantjoshi.reactivetutorial.utils;

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
import com.hemantjoshi.reactivetutorial.model.ArticleFav;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecyclerViewFavAdapter extends RecyclerView.Adapter<RecyclerViewFavAdapter.ViewHolder> {
    private List<ArticleFav> mArticles = new ArrayList<>();
    private Activity mActivity;
    private ProgressBar mProgressBar;
    private ArticleFavDatabase mArticleDatabase;

    public RecyclerViewFavAdapter(Activity activity, ProgressBar progressBar, ArticleFavDatabase articleFavDatabase){
        this.mActivity = activity;
        this.mProgressBar = progressBar;
        this.mArticleDatabase = articleFavDatabase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleFav article = mArticles.get(position);

        mProgressBar.setVisibility(View.VISIBLE);
        TextView title = holder.title;
        title.setText(article.getTitle());

        ImageView image = holder.image;
        image.setImageResource(R.drawable.ic_signal_wifi_off_white_36dp);
        holder.box.setChecked(article.getCheckbox());
        holder.box.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Completable.fromAction(() -> mArticleDatabase.getArticleFavDao().delete(article.getTitle()))
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        });
    }

    public void setItems(List<ArticleFav> articles) {
        if (!articles.isEmpty())
            this.mArticles = articles;
        mActivity.runOnUiThread(this::notifyDataSetChanged);
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
