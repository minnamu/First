package com.example.first;

import java.io.File;
import java.lang.reflect.Method;

import android.Manifest;
import android.Manifest.permission;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.first.testListView.ActivityListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager manager;
    private DrawerLayout mdl;


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
            //看是否是选择了更换头像
            case 2:
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    Uri uri = data.getData();
                    if (DocumentsContract.isDocumentUri(MainActivity.this, uri)) {
                        //如果是document类型的uri
                        //通过document id处理
                        String docId = DocumentsContract.getDocumentId(uri);
                        //如果uri的authority是media格式
                        //document id还需要一次解析，需要分割字符串取得后半部分的数字id
                        //取出的id用于构建新的uri和条件语句
                        //再把这些值传入getImagePath获取图片的真实路径
                        if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                            String id = docId.split(":")[1];
                            //MediaStore.Images.Media._ID
                            String selection = Media._ID + "=" + id;
                            imagePath = getImagePath(Media.EXTERNAL_CONTENT_URI, selection);
                        } else if (uri.getAuthority().equals("com.android.providers.download.documents")) {
                            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                            imagePath = getImagePath(contentUri, null);
                        }

                    } else if (uri.getScheme().equals("content")) {
                        //如果是content类型的uri
                        Log.d("", "onActivityResult: content类型");
                        imagePath = getImagePath(uri, null);
                        Log.d(imagePath, "onActivityResult: imagePath");
                    } else if (uri.getScheme().equals("file")) {
                        Log.d("", "onActivityResult: file类型");
                        //如果是file类型的uri
                        imagePath = uri.getPath();

                    }
                    //显示图片
                    displayImage(imagePath);

                }
                break;

            default:
                Toast.makeText(MainActivity.this, "code不是1", Toast.LENGTH_SHORT).show();

        }
    }


    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            CircleImageView picture = findViewById(R.id.circle_pic);
            ImageView nowPic = findViewById(R.id.now_pic);
            nowPic.setImageBitmap(bitmap);
//图片太大，不显示？不是这个原因
//            Matrix matrix = new Matrix();
//            matrix.setScale(0.7f, 0.7f);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            //
//            不显示：参考https://blog.csdn.net/m0_50127633/article/details/119008316
            picture.setImageBitmap(bitmap);
            Toast.makeText(MainActivity.this, "成功更改头像", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "没找到头像图片", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取图片的真实路径
        /**
         * 每个应用程序是可以实现数据共享的，对于每一个应用程序程序都拥有一个contentprovider实例进行存储，而contentresolver则是用于管理所有程序的contentprovider实例，通过contentrescolver可以获得数据，插入数据等……至于getcontentrescolver()就是获取实例
         */
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        //ui
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Intent intent = new Intent(MainActivity.this, News2.class);
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
        Button btnMd = (Button) findViewById(R.id.go_to_md);
        btnMd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityMaterialDesign.class);
                startActivity(intent);
            }
        });

        Button btnOpen = (Button) findViewById(R.id.openleftmenu);
        mdl = (DrawerLayout) findViewById(R.id.left_menu);
        btnOpen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mdl.openDrawer(GravityCompat.START);//END 右边菜单
            }
        });


        Button btnWeather = (Button) findViewById(R.id.go_to_weather);

        btnWeather.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityWeather.class);
                startActivity(intent);
            }
        });

        /**
         * 滑动菜单的点击
         */
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic1:
                        Toast.makeText(MainActivity.this, "点击了更改头像", Toast.LENGTH_SHORT).show();
                        //打开相册
                        //应该先检查是否有权限，再申请，如果有直接打开相册
                        openAlbum();


                        // mdl.closeDrawers();//关闭滑动菜单
                        break;
                    case R.id.ic2:
                        Toast.makeText(MainActivity.this, "点击了call", Toast.LENGTH_SHORT).show();
                        mdl.closeDrawers();//关闭滑动菜单
                        Intent it = new Intent(Intent.ACTION_DIAL);
                        it.setData(Uri.parse("tel:10086"));
                        startActivity(it);
                        break;
                    case R.id.ic3:
                        Toast.makeText(MainActivity.this, "点击了关于", Toast.LENGTH_SHORT).show();
                        mdl.closeDrawers();//关闭滑动菜单
                        break;

                    default:
                        break;

                }

                return false;
            }
        });


    }//oncreate

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }


    //打开相册，选择图片
    private void openAlbum() {
        //先检查是否有权限
        //运行时权限，危险权限 WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        Intent intent2 = new Intent(Intent.ACTION_PICK);
        intent2.setType("image/*");
        startActivityForResult(intent2, 2);


    }


    //参考：https://developer.android.com/training/notify-user/build-notification
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_noti:
                Intent intent = new Intent(this, MainActivity3.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
                android.app.Notification notification = new NotificationCompat.Builder(this, "First")
                        .setContentTitle("这是一个通知标题")
                        .setContentText("这是通知的内容，点击会跳转到有猫猫的界面")
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

    /**
     * 重写才能显示出menu的图标
     */
/*    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //原来的add remove菜单
        //getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
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
            //点击我的
            case R.id.my:
                mdl.openDrawer(GravityCompat.START);//END 右边菜单

                break;
            case R.id.about:

                break;
            default:

        }
        return true;
    }

    /*
    点击左侧菜单栏
     */

 /*   NavigationView navView=(NavigationView) findViewById(R.id.nav_view);

    navView.*/


}
