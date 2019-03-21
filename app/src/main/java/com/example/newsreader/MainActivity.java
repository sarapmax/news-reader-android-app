package com.example.newsreader;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements NewsHeadlineFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return;

            NewsHeadlineFragment newsHeadlineFragment = new NewsHeadlineFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, newsHeadlineFragment).commit();
        }
    }

    @Override
    public void onArticleSelected(int position, String description) {
        NewsArticleFragment articleFragment = (NewsArticleFragment) getSupportFragmentManager().findFragmentById(R.id.articleView_fragement);

        if (articleFragment != null) {
            // We're on a large screen device.
            articleFragment.updateArticleView(position, description);
        } else {
            // We're on a small screen device.
            articleFragment = new NewsArticleFragment();

            Bundle args = new Bundle();
            args.putInt("NewsId", position);
            args.putString("NewsDescription", description);
            articleFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, articleFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
