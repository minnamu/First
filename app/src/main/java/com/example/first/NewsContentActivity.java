package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.first.news.NewsContentFragment;

public class NewsContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String nT, String nC) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_titile_key", nT);
        intent.putExtra("news_content_key", nC);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        String newsT = getIntent().getStringExtra(("news_title_key"));
        String newsC = getIntent().getStringExtra("news_content_key");
        //获取这个fragment
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.newsf);
        //刷新界面
        newsContentFragment.refresh(newsT, newsC);


    }
}