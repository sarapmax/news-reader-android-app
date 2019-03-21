package com.example.newsreader;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsArticleFragment extends Fragment {

    int mCurrentArticle = -1;
    String mCurrentdescription = null;

    public NewsArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_article, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();

        if (bundle != null) {
            updateArticleView(bundle.getInt("NewsId"), bundle.getString("NewsDescription"));
        } else if (mCurrentArticle >= 0) {
            updateArticleView(mCurrentArticle, mCurrentdescription);
        }
    }

    public void updateArticleView(int position, String description) {
        TextView textView = (TextView) getView();

        // Bind HTML tags to TextView.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(description));
        }

        mCurrentArticle = position;
        mCurrentdescription = description;
    }
}
