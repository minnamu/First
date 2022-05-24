package com.example.first;
//自定义标题栏
//使用ListView（ArrayAdapter）
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UIActivity4 extends AppCompatActivity {
    private String[] data = {"a", "b", "c", "d", "e", "f", "g","h", "i", "j", "k"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiactivity4);
        //隐藏自带标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //使用ArrayAdapter，将数组中的数据传给listview
        //参数：当前上下文，ListView子项布局的id，数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UIActivity4.this, android.R.layout.simple_list_item_1, data);

        ListView listview = (ListView)findViewById(R.id.list_view);
        listview.setAdapter(adapter);


    }
}