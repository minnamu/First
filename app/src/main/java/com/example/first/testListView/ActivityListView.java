package com.example.first.testListView;
//自定义标题栏
//使用ListView（ArrayAdapter）

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first.letters.Letters;
import com.example.first.R;
import com.example.first.testRecyclerView.ActivityUITest;

public class ActivityListView extends AppCompatActivity {
    private String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiactivity4);
        //隐藏自带标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

           // ListView简单用法
           //1. 在布局文件中引入Listview布局。
           //2. 将数据提前准备好。
           //3. 将数据和Listview结合

        //使用ArrayAdapter，将数组中的数据传给listview
        //参数：当前上下文，ListView子项布局的id，数据

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UIActivity4.this, android.R.layout.simple_list_item_1, data);

        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);
    }
*/

    //使用自定义适配器letterAdapter
    private List<Letters> lettersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiactivity4);
        TextView title = findViewById(R.id.title_text);
        title.setText("使用ListView");
        //隐藏自带标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initLetters();
        //参数：当前上下文，ListView子项布局的id，数据
        LetterAdapter adapter = new LetterAdapter(ActivityListView.this, R.layout.letters_item, lettersList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //增加点击事件
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //通过position判断点击的子项
                Letters letters = lettersList.get(position);
                Toast.makeText(ActivityListView.this, "This is the letter " + letters.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btn = (Button) findViewById(R.id.go_to_recyclerview);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityListView.this, ActivityUITest.class);
                startActivity(intent);
            }
        });

    }

    private void initLetters() {
        for (int i = 0; i < 5; i++) {//多次重复
            Letters a = new Letters("a", R.drawable.a, "1个");
            lettersList.add(a);
            Letters b = new Letters("b", R.drawable.b, "2个");
            lettersList.add(b);
            Letters c = new Letters("c", R.drawable.c, "3个");
            lettersList.add(c);
            Letters d = new Letters("d", R.drawable.d, "4个");
            lettersList.add(d);
        }
    }

}