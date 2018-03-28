package com.hemantjoshi.reactivetutorial.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hemantjoshi.reactivetutorial.MainActivity;
import com.hemantjoshi.reactivetutorial.R;
import com.hemantjoshi.reactivetutorial.model.Article;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by HemantJ on 09/01/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> mArticles = new ArrayList<>();
    private Activity mActivity;

    public RecyclerViewAdapter(Activity activity){
        this.mActivity = activity;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        TextView title = holder.title;
        title.setText(article.getTitle());

        TextView desc = holder.description;
        desc.setText(article.getDescription());
    }

    public void setItems(ArrayList<Article> articles){
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, description;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
        }
    }
}
