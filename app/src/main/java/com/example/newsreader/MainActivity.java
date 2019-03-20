package com.example.newsreader;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends ListActivity {
    ArrayList<String> titles;
    NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles = new ArrayList<>();

        adapter = new NewsListAdapter(this, titles);
        setListAdapter(adapter);

        try {
            URL url = new URL("https://udn.com/rssfeed/news/2/6638?ch=news");

            new NewsSAX(url, new ParserListener() {
                @Override
                public void setTitle(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titles.add(s);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(this, titles.get(position), Toast.LENGTH_LONG).show();
    }
}
