package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Trend extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    //传感器声明
    private Sensor mGyroscope; //旋转速率传感器
    private Sensor mRotation;  //旋转度数传感器
    private Sensor mAccelerate; //线性加速度传感器
    private Sensor mGravity; //重力加速度传感器
    //控件声明
    private TextView gyroscopeView_x;  //旋转速率x
    private TextView gyroscopeView_y;  //旋转速率y
    private TextView gyroscopeView_z;  //旋转速率z

    private TextView rotationView_x;  //旋转度数x
    private TextView rotationView_y;  //旋转度数y
    private TextView rotationView_z;  //旋转度数z

    private TextView accelerateView_x;  //线性加速度x
    private TextView accelerateView_y;  //线性加速度y
    private TextView accelerateView_z;  //线性加速度z

    private TextView gravityView_x;  //重力加速度x
    private TextView gravityView_y;  //重力加速度y
    private TextView gravityView_z;  //重力加速度z

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);

        //获取传感器管理对象
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        //获取传感器类型
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);  //旋转速率传感器
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);  //旋转度数传感器
        mAccelerate = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);  //线性加速度传感器
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);  //重力加速度传感器

        //获得控件
        gyroscopeView_x = (TextView) findViewById(R.id.gyroscope_x);  //旋转速率x控件
        gyroscopeView_y = (TextView) findViewById(R.id.gyroscope_y);  //旋转速率y控件
        gyroscopeView_z = (TextView) findViewById(R.id.gyroscope_z);  //旋转速率z控件

        rotationView_x = (TextView) findViewById(R.id.rotationV_x);  //旋转度数x
        rotationView_y = (TextView) findViewById(R.id.rotationV_y);  //旋转度数y
        rotationView_z = (TextView) findViewById(R.id.rotationV_z);  //旋转度数z

        accelerateView_x = (TextView) findViewById(R.id.accelerateV_x);  //线性加速度x
        accelerateView_y = (TextView) findViewById(R.id.accelerateV_y);  //线性加速度y
        accelerateView_z = (TextView) findViewById(R.id.accelerateV_z);  //线性加速度z

        gravityView_x = (TextView) findViewById(R.id.gravityV_x);  //重力加速度x
        gravityView_y = (TextView) findViewById(R.id.gravityV_y);  //重力加速度y
        gravityView_z = (TextView) findViewById(R.id.gravityV_z);  //重力加速度z
    }

    @Override
    //监听传感器传回的数据
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mGyroscope,SensorManager.SENSOR_DELAY_GAME);  //旋转速率
        mSensorManager.registerListener(this,mRotation,SensorManager.SENSOR_DELAY_GAME);  //旋转度数
        mSensorManager.registerListener(this,mAccelerate,SensorManager.SENSOR_DELAY_GAME);  //线性加速度
        mSensorManager.registerListener(this,mGravity,SensorManager.SENSOR_DELAY_GAME);  //重力加速度
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
        float x, y, z;
        switch (type){
            //旋转速率
            case Sensor.TYPE_GYROSCOPE:
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                gyroscopeView_x.setText("x: "+df.format(x)+"rad/s");
                gyroscopeView_y.setText("y: "+df.format(y)+"rad/s");
                gyroscopeView_z.setText("z: "+df.format(z)+"rad/s");
                break;
            //旋转度数
            case Sensor.TYPE_ROTATION_VECTOR:
                x = -(float)Math.toDegrees(event.values[0]);
                y = -(float)Math.toDegrees(event.values[1]);
                z = -(float)Math.toDegrees(event.values[2]);
                rotationView_x.setText("x: "+df.format(x));
                rotationView_y.setText("y: "+df.format(y));
                rotationView_z.setText("z: "+df.format(z));
                break;
            //线性加速度
            case Sensor.TYPE_LINEAR_ACCELERATION:
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                accelerateView_x.setText("x: "+df.format(x)+" m/s²");
                accelerateView_y.setText("y: "+df.format(y)+" m/s²");
                accelerateView_z.setText("z: "+df.format(z)+" m/s²");
                break;
            //重力加速度
            case Sensor.TYPE_GRAVITY:
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                gravityView_x.setText("x: "+df.format(x)+" m/s²");
                gravityView_y.setText("y: "+df.format(y)+" m/s²");
                gravityView_z.setText("z: "+df.format(z)+" m/s²");
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
