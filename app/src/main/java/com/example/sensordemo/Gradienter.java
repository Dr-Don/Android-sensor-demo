package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Gradienter extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;
    //加速度传感器
    Sensor mAccelerometer;
    float[] accelerometerValues = new float[3];
    //地磁传感器
    Sensor mMagnetic;
    float[] magneticValues = new float[3];
    //旋转矩阵
    float[] r = new float[9];
    //模拟方向传感器的数据
    float[] values = new float[3];

    LevelView levelView;
    TextView tvHorz;
    TextView tvVert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradienter);

        levelView = (LevelView)findViewById(R.id.gv_hv);

        tvVert = (TextView)findViewById(R.id.tvv_vertical);
        tvHorz = (TextView)findViewById(R.id.tvv_horz);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onResume(){
        super.onResume();
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //注册监听器
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mMagnetic,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        //取消监听
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop(){
        //停止监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        int type = event.sensor.getType();
        switch (type){
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerValues = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magneticValues = event.values.clone();
                break;
                default:
                    break;
        }

        SensorManager.getRotationMatrix(r,null,accelerometerValues,magneticValues);
        SensorManager.getOrientation(r,values);

        //获取沿着z轴转过的角度
        float azimuth = values[0];

        //获取沿着x轴倾斜时与y轴的夹角
        float pitchAngle = values[1];

        //获取沿着y轴的滚动时与x轴的角度
        float rollAngle = -values[2];

        onAngleChanged(rollAngle,pitchAngle,azimuth);
    }

    //角度变更并显示到页面
    private void onAngleChanged(float roll,float pitch,float azi){
        levelView.setAngle(roll,pitch);
        tvHorz.setText(String.valueOf((int)Math.toDegrees(roll)) + "°");
        tvVert.setText(String.valueOf((int)Math.toDegrees(pitch)) + "°");
    }
}
