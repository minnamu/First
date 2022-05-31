package com.example.first;
//练习RecyclerView

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.first.letters.Letters;
import com.example.first.R;
import com.example.first.testRecyclerView.LetterRecyclerAdapter;

public class ActivityUITest extends AppCompatActivity {
    private List<Letters> lettersList = new ArrayList<>();

    private void initLetters() {
        for (int i = 0; i < 5; i++) {//多次重复
            Letters a = new Letters("a", R.drawable.a, "");
            lettersList.add(a);
            Letters b = new Letters("b", R.drawable.b, "");
            lettersList.add(b);
            Letters c = new Letters("c", R.drawable.c, "");
            lettersList.add(c);
            Letters d = new Letters("d", R.drawable.d, "");
            lettersList.add(d);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitest);
        //ui
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        initLetters();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //创建一个LinearLayoutManager对象，并设置到RecyclerView中
        //这个滚动控件是线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置水平
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); //不设置默认垂直

        recyclerView.setLayoutManager(linearLayoutManager);
        LetterRecyclerAdapter adapter = new LetterRecyclerAdapter(lettersList);
        recyclerView.setAdapter(adapter);

    }

}