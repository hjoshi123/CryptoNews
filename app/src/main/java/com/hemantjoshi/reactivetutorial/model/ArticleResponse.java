package com.hemantjoshi.reactivetutorial.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by HemantJ on 07/01/18.
 */

public class ArticleResponse {
    @SerializedName("articles")
    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles(){
        return articles;
    }
}
