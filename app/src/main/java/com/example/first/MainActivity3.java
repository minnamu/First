package com.example.first;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private ImageView imageView;
    private int pic = 1;

    /**
     * 1。判定是否要关闭软键盘
     */
    private boolean isHideSoftKeyboard(View view, MotionEvent event) {
        if (view instanceof EditText) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            return !(event.getX() > left) || !(event.getX() < right) || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    /**
     * 2。关闭软键盘
     */
    private void HideSoftKeyboard(IBinder binder) {
        if (binder != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 3。触摸事件分发方法，则可以触碰其他地方关闭键盘
     * 需要增：
     * vandroid:focusable="true"
     * android:focusableInTouchMode="true"
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideSoftKeyboard(view, event)) {
                HideSoftKeyboard(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //接口方式
        Button btn5 = findViewById(R.id.button5);
        editText = (EditText) findViewById(R.id.edit_text);
        btn5.setOnClickListener(this);

/*
        //键盘
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //打开键盘

                } else {
                    //关闭键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }

            }
        });

 */

        //更改图片
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
                        //editText.setInputType(InputType.TYPE_NULL); // 关闭软键盘？

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