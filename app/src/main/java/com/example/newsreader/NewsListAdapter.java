package com.example.newsreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mTitles;

    NewsListAdapter(Context context, ArrayList<String> titles) {
        mContext = context;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.news_list_item, null);

        TextView textView = itemView.findViewById(R.id.textView);

        textView.setText(mTitles.get(i));

        return itemView;
    }
}
