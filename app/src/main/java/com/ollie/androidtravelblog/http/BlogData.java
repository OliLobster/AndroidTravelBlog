package com.ollie.androidtravelblog.http;

import java.util.ArrayList;
import java.util.List;

// to hold an array of blog articles
public class BlogData {

    private List<Blog> data;

    public List<Blog> getData() {
        return data != null ? data : new ArrayList<>();
    }
}
