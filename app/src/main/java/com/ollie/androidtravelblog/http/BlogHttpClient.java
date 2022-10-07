package com.ollie.androidtravelblog.http;

public final class BlogHttpClient {

    public static final BlogHttpClient INSTANCE = new BlogHttpClient();

    private static final String BASE_URL =
            "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/";
    private static final String BLOG_ARTICLES_URL =
            BASE_URL + "8550ef2064bf14fcf3b9ff322287a2e056c7e153/blog_articles.json";

    private BlogHttpClient() {
        // to do
    }
}
