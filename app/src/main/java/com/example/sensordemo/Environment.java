package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Environment extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    //传感器声明
    private Sensor mHumidity; //湿度传感器
    private Sensor mTemperature;//温度传感器
    private Sensor mLight;//光传感器
    private Sensor mPressure;//压力传感器

    //控件声明
    private CircleProgress humidityView;  //湿度
    private TextView temperatureView; //温度
    private TextView lightView;//光强
    private TextView pressureView;//压力

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        //获取传感器管理对象
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        //获取传感器类型
        mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);  //湿度传感器
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);  //温度传感器
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);  //光传感器
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);  //压力传感器
        //获得控件
        humidityView = (CircleProgress) findViewById(R.id.humidity);  //湿度控件
        temperatureView = (TextView) findViewById(R.id.temperature);  //温度控件
        lightView = (TextView) findViewById(R.id.light);  //光强控件
        pressureView = (TextView) findViewById(R.id.pressure);  //压力控件
    }

    @Override
    //监听传感器传回的数据
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mHumidity,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mTemperature,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_GAME);
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

        //获取传感器类型
        int type = event.sensor.getType();
        float h, t, l, p;
        switch (type){
            //湿度
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                h = event.values[0];
                humidityView.setProgress(Math.round(h));
                break;
            //温度
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                t = event.values[0];
                temperatureView.setText(Math.round(t) + "");
                break;
            //光强
            case Sensor.TYPE_LIGHT:
                l = event.values[0];
                lightView.setText(l+"");
                break;
            //压力
            case Sensor.TYPE_PRESSURE:
                p = event.values[0];
                pressureView.setText(p+"");
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
