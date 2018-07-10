package com.hemantjoshi.reactivetutorial.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HemantJ on 07/01/18.
 */

public class ArticleResponse {
    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles(){
        return articles;
    }
}
