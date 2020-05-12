package com.example.sensordemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Location extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    //传感器声明
    private Sensor mRotation; //旋转速度传感器
    private Sensor mMagnetic;  //地磁场强度传感器
    private Sensor mDistance; //距离传感器

    //控件声明
    private TextView rotationView_x;  //旋转速率x
    private TextView rotationView_y;  //旋转速率y
    private TextView rotationView_z;  //旋转速率z

    private TextView magneticView_x;  //旋转度数x
    private TextView magneticView_y;  //旋转度数y
    private TextView magneticView_z;  //旋转度数z

    private TextView distanceView; //距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //获取传感器管理对象
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        //获取传感器类型
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);  //地磁场强度传感器
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);  //旋转度数传感器
        mDistance = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);  //距离传感器

        //获得控件
        magneticView_x = (TextView) findViewById(R.id.magnetic_x);  //地磁场强度x控件
        magneticView_y = (TextView) findViewById(R.id.magnetic_y);  //地磁场强度y控件
        magneticView_z = (TextView) findViewById(R.id.magnetic_z);  //地磁场强度z控件

        rotationView_x = (TextView) findViewById(R.id.rotation_x);  //旋转度数x
        rotationView_y = (TextView) findViewById(R.id.rotation_y);  //旋转度数y
        rotationView_z = (TextView) findViewById(R.id.rotation_z);  //旋转度数z

        distanceView = (TextView) findViewById(R.id.distance);  //距离控件
    }

    @Override
    //监听传感器传回的数据
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mMagnetic,SensorManager.SENSOR_DELAY_GAME);  //地磁场强度
        mSensorManager.registerListener(this,mRotation,SensorManager.SENSOR_DELAY_GAME);  //旋转度数
        mSensorManager.registerListener(this,mDistance,SensorManager.SENSOR_DELAY_GAME);  //旋转度数
    }

    @Override
    //取消监听
    protected void onStop(){
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    //当传感器的值改变时的回调方法
    public void onSensorChanged(SensorEvent event){
        float[] values = event.values;
        DecimalFormat df = new DecimalFormat("######0.00");

        //获取传感器类型
        int type = event.sensor.getType();
        float x, y, z, d;
        switch (type){
            //旋转速率
            case Sensor.TYPE_MAGNETIC_FIELD:
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                magneticView_x.setText("x: "+df.format(x)+"μT");
                magneticView_y.setText("y: "+df.format(y)+"μT");
                magneticView_z.setText("z: "+df.format(z)+"μT");
                break;
            //旋转度数
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                x = (float)Math.toDegrees(event.values[0]);
                y = (float)Math.toDegrees(event.values[1]);
                z = (float)Math.toDegrees(event.values[2]);
                rotationView_x.setText("x: "+df.format(x)+"°");
                rotationView_y.setText("y: "+df.format(y)+"°");
                rotationView_z.setText("z: "+df.format(z)+"°");
                break;
            //距离
            case Sensor.TYPE_PROXIMITY:
                d = event.values[0];
                distanceView.setText(d+" cm");
                break;
            default:
                break;
        }
    }

    @Override
    //当传感器精度发生改变时的回调方法
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }
}

