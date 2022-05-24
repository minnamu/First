package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("data_return", "hello no1");//key,value
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //显示intent中的extra data
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");//用key
        //Toast.makeText(MainActivity2.this, "获取到activity1传来的：" + data, Toast.LENGTH_SHORT).show();
        TextView textView = (TextView) findViewById(R.id.get_data_from1);
        textView.setText("获取到activity1传来的：" + data);
        Log.d("send to activity2", data);

        //返回数据给上一个活动
        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return", "hello no1");//key,value
                setResult(RESULT_OK, intent);//返回数据给上一个活动,
                //第一个参数用于向上一个活动返回处理结果。
                //第二个参数把带有数据的Intent传递回去。
                //使用startActivityForResult()方法启动活动的，在活动被销毁之后会回调上一个活动的onActivityResult()方法
                // 因此需要在上一个方法中重写onActivityResult()方法。
                finish();
            }
        });

        Button btnProgress = (Button) findViewById(R.id.button_progress);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnProgress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //不可见就显示进度条，可见就隐藏
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        //add水平进度条
        Button btnAdd = (Button) findViewById(R.id.add_progress);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = progressBar.getProgress();
                p += 10;
                progressBar.setProgress(p);

            }
        });
    }


}