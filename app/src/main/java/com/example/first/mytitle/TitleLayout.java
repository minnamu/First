package com.example.first.mytitle;
/*
    创建自定义控件
    步骤：
        1. 在java包中创建控件类。
        2. 在控件类中写自己想要的。
        3. 在主布局xml中引入控件类。
 */

import java.text.AttributedCharacterIterator.Attribute;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.first.R;

//自定义控件
public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //from()构建出一个layoutinflater对象
        //inflate()动态加载一个布局文件
        //参数1：布局文件   参数2：给加载好的布局添加一个父布局(这里为TitleLayout)
        LayoutInflater.from(context).inflate(R.layout.title, this);


        Button btnBack = (Button) findViewById(R.id.title_back);
        Button btnEdit = (Button) findViewById(R.id.title_edit);

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁当前活动
                ((Activity) getContext()).finish();
            }
        });

        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "你点击了edit", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
