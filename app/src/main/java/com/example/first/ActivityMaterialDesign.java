package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMaterialDesign extends AppCompatActivity {
    private DrawerLayout mdl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);

        mdl = (DrawerLayout) findViewById(R.id.drid);
        Button button = (Button) findViewById(R.id.menubt);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mdl.openDrawer(GravityCompat.START);//END 右边菜单
            }
        });

    }
}