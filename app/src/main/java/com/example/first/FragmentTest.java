package com.example.first;
    /*
    动态添加碎片
    步骤：
        1. 创建添加的碎片实例。
        2. 获取FragmentManager，在活动中可以直接通过调用getSupportFragmentManager()方法得到。
        3. 开启一个事务，通过调用beginTransaction()方法来完成。
        4. 向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例。
        5. 提交事务，调用commit()方法来完成。
     */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        //先把原来的right布局放进来
        replace(new RightFragment());//入栈

        /**
         * 获取碎片实例，获取碎片的方法
         * getSupportFragmentManager().findFragmentById()
         */
        RightFragment rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.another_right_fragment);
        /**
         * 碎片调用活动中的方法
         * 碎片调用getActivity()获取当前活动,见LeftFragment.java
         */


        //点击左侧碎片中的button，将右边替换成anotherright
        Button btn = (Button) findViewById(R.id.left_button);


        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                replace((new AnotherRightFragment()));

                //Toast.makeText(FregmentTest.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 动态加载碎片，点击button之后，调用replace((new AnotherRightFragment()))
     *
     * @param fragment 步骤：
     *                 创建right碎片实例
     *                 使用getSupportFragmentManager（）获取FragmentManager
     *                 使用fragmentManager.beginTransaction()开启一个事务
     *                 使用fragmentTransaction.replace（）向容器内添加或替换碎片，参数：容器id，碎片实例
     *                 使用commit提交事务
     */
    private void replace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.another_right_fragment, fragment);
        /**
         * 将事务添加到返回栈中
         * FragmentTransaction的addToBackStack(null)方法：将一个事务传到返回栈中，接收一个名字，一般传入null
         */
        fragmentTransaction.addToBackStack(null);//使得：back时，栈顶出栈，返回到原来的activity界面
        fragmentTransaction.commit();
    }
}