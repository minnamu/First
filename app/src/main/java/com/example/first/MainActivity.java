package com.example.first;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;

import com.example.first.fragments.FragmentTestActivity;

import com.example.first.testListView.ActivityListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager manager;

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
/**
 * 8.0以上版本
 */
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "First";
            String channelName = "通知消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            manager.createNotificationChannel(channel);
        }

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

                /**
                 * 隐式intent
                 */
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
                Intent intent = new Intent(MainActivity.this, ActivityListView.class);
                startActivity(intent);
            }
        });
        Button btnFragment = (Button) findViewById(R.id.go_to_fregment);
        btnFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FragmentTestActivity.class);
                startActivity(intent);
            }
        });

        Button btnFragmentNews = (Button) findViewById(R.id.go_to_news);
        btnFragmentNews.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsContentActivity.class);
                startActivity(intent);
            }
        });

        //点击创建通知
        Button btnNoti = (Button) findViewById(R.id.go_to_noti);
        btnNoti.setOnClickListener(this);
/*

        btnNoti.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

*//*
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                //  PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                //https://blog.csdn.net/yubo_725/article/details/124413000
                PendingIntent pendingIntent;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    pendingIntent = PendingIntent.getActivity(MainActivity.this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
                } else {
                    pendingIntent = PendingIntent.getActivity(MainActivity.this, 123, intent, PendingIntent.FLAG_ONE_SHOT);
                }

*//*
         *//*
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("通知标题")
                        .setContentText("通知内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.noti)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.noti))
                        //.setContentIntent(pendingIntent)
                        //通知自动取消
                        //.setAutoCancel(true)
                        //震动，毫秒，震动静止再震动，需要注册权限
                        //.setVibrate(new long[]{0, 1000, 1000, 1000})
                        //重要通知，弹出横幅
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                notificationManager.notify(1, notification);

 *//*
         *//**
         * 通知跳转
         * https://developer.android.com/training/notify-user/build-notification
         *//*
         *//* Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);*//*
         *//**
         * Android8.0以上的的通知要设置渠道，否则就无法显示
         * https://blog.csdn.net/weixin_46760781/article/details/105932789
         *//*
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                PendingIntent activity = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                android.app.Notification notification = new NotificationCompat.Builder(this, "First")
                        .setContentTitle("通知标题！")
                        .setContentText("通知内容！")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(activity)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .build();
                manager.notify(1, notification);
                Log.d("my notification", "onClick: 点击了创建通知");
                //跳转到activity2
                //

                //startActivity(intent);
            }
        });*/

        Button btnCamera = (Button) findViewById(R.id.go_to_camera);
        btnCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitygCamera.class);
                startActivity(intent);
            }
        });

        Button btnVideo = (Button) findViewById(R.id.go_to_video);
        btnVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitygCamera.class);
                startActivity(intent);
            }
        });


    }//oncreate

    //参考：https://developer.android.com/training/notify-user/build-notification
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_noti:
                Intent intent = new Intent(this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
                android.app.Notification notification = new NotificationCompat.Builder(this, "First")
                        .setContentTitle("这是一个通知标题")
                        .setContentText("这是通知的内容，点击会跳转到activity2")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.noti)
                        .setContentIntent(activity)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.noti))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);
                break;
            default:
                break;
        }
    }


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
