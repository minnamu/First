package com.example.first;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private ImageView imageView;
    private int pic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //接口方式
        Button btn5 = findViewById(R.id.button5);
        editText = (EditText) findViewById(R.id.edit_text);
        btn5.setOnClickListener(this);

        Button btn7 = (Button) findViewById(R.id.change_image);
        imageView = (ImageView) findViewById(R.id.image_view);
        btn7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pic == 1) {
                    imageView.setImageResource(R.drawable.cat2);
                    pic = 2;
                } else {
                    imageView.setImageResource(R.drawable.cat1);
                    pic = 1;
                }

            }
        });
        Button btnProgressDialog = (Button) findViewById(R.id.btn_pro_dialog);
        btnProgressDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity3.this);
                progressDialog.setTitle("这是一个progressDilog");
                progressDialog.setMessage("加载中...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
                //弹出alertdialog
                AlertDialog.Builder dialog = new Builder(MainActivity3.this);
                dialog.setTitle("this is a dialog");
                dialog.setMessage("是否确认？");
                //不能back
                dialog.setCancelable(false);
                //确认的点击事件
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取输入的值
                        String inputText = editText.getText().toString();
                        Toast.makeText(MainActivity3.this, "输入的值为：" + inputText, Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

                break;
            default:
                break;

        }

    }

}