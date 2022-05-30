package com.example.first;
//p294 调用摄像头拍照

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ActivitygCamera extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;

    private Uri imageUri;

    /**
     * Android 相机应用会对返回 Intent（作为 extra 中的小型 Bitmap 传递给 onActivityResult()，
     * 使用键 "data"）中的照片进行编码。下面的代码会检索此图片，并将其显示在一个 ImageView 中。
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityg_camera);

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        ImageView picture = (ImageView) findViewById(R.id.pictrue);

        takePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, TAKE_PHOTO);
                }
/*                //File用于存储
                File outf = new File(getExternalCacheDir(), "output_image.jpg");
                if (outf.exists()) {
                    outf.delete();
                }
                try {
                    outf.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(ActivitygCamera.this, "com.example.cameraalbumtest.fileprovider", outf);


                } else {
                    imageUri = Uri.fromFile(outf);
                }*/
                //启动相机
            /*    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //android.media.action.IMAGE.CAPTURE
                //MediaStore.ACTION_IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);*/
//                dispatchTakePictureIntent();
            }
        });


    }

    /* @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            //super.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            //显示出拍摄的图片
                            picture.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                default:
                    break;
            }
        }*/
    //另一种方法
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }
}