package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //声明按钮和intent
    Button trendBtn;
    Button environmentBtn;
    Button locationBtn;
    Button compassBtn;
    Button gradienterBtn;
    Button testBtn;

    Intent trendInt;
    Intent environmentInt;
    Intent locationInt;
    Intent compassInt;
    Intent gradienterInt;
    Intent testInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //连接按钮
        //动态传感器
        trendBtn = (Button)findViewById(R.id.trendBtn);
        trendBtn.setOnClickListener(this);
        //环境传感器
        environmentBtn = (Button)findViewById(R.id.environmentBtn);
        environmentBtn.setOnClickListener(this);
        //位置传感器
        locationBtn = (Button)findViewById(R.id.locationBtn);
        locationBtn.setOnClickListener(this);
        //指南针
        compassBtn = (Button)findViewById(R.id.compassBtn);
        compassBtn.setOnClickListener(this);
        //水平仪
        gradienterBtn = (Button)findViewById(R.id.gradienterBtn);
        gradienterBtn.setOnClickListener(this);
        //传感器检测
        testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(this);

        //定义intent
        trendInt = new Intent(MainActivity.this,Trend.class);
        environmentInt = new Intent(MainActivity.this,Environment.class);
        locationInt = new Intent(MainActivity.this,Location.class);
        compassInt = new Intent(MainActivity.this,Compass.class);
        gradienterInt = new Intent(MainActivity.this,Gradienter.class);
        testInt = new Intent(MainActivity.this,Test.class);
    }

    //按钮监听事件
    public void onClick(View v){
        switch (v.getId()){
            case R.id.trendBtn:
                startActivity(trendInt);
                break;
            case R.id.environmentBtn:
                startActivity(environmentInt);
                break;
            case R.id.locationBtn:
                startActivity(locationInt);
                break;
            case R.id.compassBtn:
                startActivity(compassInt);
                break;
            case R.id.gradienterBtn:
                startActivity(gradienterInt);
                break;
            case R.id.testBtn:
                startActivity(testInt);
                break;
            default:
                break;
        }
    }
}
