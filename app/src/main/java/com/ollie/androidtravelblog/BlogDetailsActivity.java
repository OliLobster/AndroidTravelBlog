package com.ollie.androidtravelblog;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ollie.androidtravelblog.http.Blog;
import com.ollie.androidtravelblog.http.BlogArticlesCallback;
import com.ollie.androidtravelblog.http.BlogHttpClient;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textDate;
    private TextView textAuthor;
    private TextView textRating;
    private TextView textDescription;
    private TextView textViews;
    private RatingBar ratingBar;
    private ImageView imageAvatar;
    private ImageView imageMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        imageMain = findViewById(R.id.imageMain);
        imageAvatar = findViewById(R.id.imageAvatar);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> finish());

        textTitle = findViewById(R.id.textTitle);
        textDate = findViewById(R.id.textDate);
        textAuthor = findViewById(R.id.textAuthor);
        textRating = findViewById(R.id.textRating);
        textViews = findViewById(R.id.textViews);
        textDescription = findViewById(R.id.textDescription);
        ratingBar = findViewById(R.id.ratingBar);

        // start data loading
        loadData();
    }
    // interface with new keyword explained
    // https://stackoverflow.com/questions/9157784/java-interface-with-new-keyword-how-is-that-possible
    /*  (1) get the instance of HTTP client using BlogHttpClient.INSTANCE method
        (2) call loadBlogArticles method and pass BlogArticlesCallback
        (3) inside the onSuccess method we are still on the background thread, use runOnUiThread to switch to the UI thread
        (4) call showData method and pass a first blog article*/
    private void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() { // 1, 2
            @Override
            public void onSuccess(List<Blog> blogList) { // 3
                runOnUiThread(() -> showData(blogList.get(0))); // 4
            }

            @Override
            public void onError() {
                // handle error
            }
        });
    }

    private void showData(Blog blog) {
        textTitle.setText(blog.getTitle());
        textDate.setText(blog.getDate());
        textAuthor.setText(blog.getAuthor().getName());
        textRating.setText(String.valueOf(blog.getRating()));
        textViews.setText(String.format("(%d views)", blog.getViews()));
        textDescription.setText(blog.getDescription());
        ratingBar.setRating(blog.getRating());

        Glide.with(this)
                .load(blog.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blog.getAuthor().getAvatar())
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }
}
