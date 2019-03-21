package com.example.newsreader;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsHeadlineFragment extends ListFragment {
    ArrayList<String> titles;
    ArrayList<String> descriptions;
    NewsListAdapter adapter;
    OnHeadlineSelectedListener mListener;

    public NewsHeadlineFragment() {
        // Required empty public constructor
    }

    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position, String description);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles = new ArrayList<>();
        descriptions = new ArrayList<>();

        adapter = new NewsListAdapter(getActivity().getApplicationContext(), titles);
        setListAdapter(adapter);

        try {
            URL url = new URL("https://udn.com/rssfeed/news/2/6638?ch=news");

            new NewsSAX(url, new ParserListener() {
                @Override
                public void setTitle(final String s) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titles.add(s);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void setDescription(final String description) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            descriptions.add(description);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Highlight the item selected by the user.
        getListView().setItemChecked(position, true);

        mListener.onArticleSelected(position, descriptions.get(position));
    }
}
