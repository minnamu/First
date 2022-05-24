package com.example.first;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //activity2销毁后，会回调这个函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1://看是否是从2传回来的数据
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");

                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText("获取到从activity2中返回的数据：" + returnData);

                    Log.d("return to the activity1", returnData);
                }
                break;
            default:
                Toast.makeText(MainActivity.this, "code不是1", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        Button btn1 = (Button) findViewById(R.id.button1);
        TextView tv1 = (TextView) findViewById(R.id.textView);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn1按钮点击触发的事件
                //System.out.println("点击了btn1");
                if (tv1.getText().equals("hello")) {
                    tv1.setText("change");
                } else {
                    tv1.setText("hello");
                }
                Toast.makeText(MainActivity.this, "点击了btn1", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();//销毁活动

                //显式intent
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                //向下一个活动传递数据
                String data = "hello no2";
                intent.putExtra("extra_data", data);//key,value
                //startActivity(intent);

                startActivityForResult(intent, 1);

                //隐式intent
                //Intent intent=new Intent("com.example.activitytest.ACTION_START");//表明想要启动能够响应""的activity
                // intent.addCategory("android.intent.category.MY_CATEGORY");//每个Intent中只能指定一个action，但却能指定多个category。
                // startActivity(intent);

            }
        });


        Button btnBaidu = (Button) findViewById(R.id.button4);
        btnBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();//销毁活动

                //显式intent
                //Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                //startActivity(intent);

                //隐式intent
                Intent intent = new Intent(Intent.ACTION_VIEW);//指定action
                //Intent intent=new Intent(Intent.ACTION_DIAL);//指定action
                //Intent intent=new Intent("android.intent.action.VIEW");//指定action

                intent.setData(Uri.parse("http://www.baidu.com"));//字符串解析成uri对象
                //intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);

            }
        });

        Button btnUI = (Button) findViewById(R.id.button_to_activity3);
        btnUI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
        Button btnTo4 = (Button) findViewById(R.id.button_to_activity4);
        btnTo4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UIActivity4.class);
                startActivity(intent);
            }
        });


    }//oncreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//判断点击哪个菜单并弹出一个toast
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(MainActivity.this, "点击了菜单add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(MainActivity.this, "点击了菜单remove", Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return true;
    }


}
