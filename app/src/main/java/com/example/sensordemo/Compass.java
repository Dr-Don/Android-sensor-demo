package com.example.sensordemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Compass活动，指南针的实现
 * */
public class Compass extends AppCompatActivity implements SensorEventListener {

    //指南针图片
    ImageView compassImg;
    //sensor管理器
    SensorManager mSensorManager;
    //图片转过的角度
    float currentDegree = 0f;
    //加速度传感器
    Sensor mAccelerometer;
    float[] accelerometerValues = new float[3];
    //地磁传感器
    Sensor mMagnetic;
    float[] magneticValues = new float[3];
    //位置服务
    GPSLocationManager gpsLocationManager;
    //纬度控件
    TextView latitudeText;
    //经度控件
    TextView longitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        //获取图片
        compassImg = (ImageView) findViewById(R.id.compassImg);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取文字控件
        latitudeText = (TextView) findViewById(R.id.latitude);
        longitudeText = (TextView) findViewById(R.id.longitude);
        //定义传感器
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //初始化并开启定位
        gpsLocationManager = GPSLocationManager.getInstances(Compass.this);
        gpsLocationManager.start(new MyListener());
    }

    class MyListener implements GPSLocationListener {
        @Override
        public void UpdateLocation(Location location) {
            if (location != null) {
                //显示经纬度
                showGPS(location.getLatitude(),location.getLongitude());
            }
        }

        @Override
        public void UpdateStatus(String provider, int status, Bundle extras) {
            if ("gps".equals(provider)) {
                Toast.makeText(Compass.this, "定位类型：" + provider, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void UpdateGPSProviderStatus(int gpsStatus) {
            switch (gpsStatus) {
                case GPSProviderStatus.GPS_ENABLED:
                    Toast.makeText(Compass.this, "GPS开启", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_DISABLED:
                    Toast.makeText(Compass.this, "GPS关闭", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_OUT_OF_SERVICE:
                    Toast.makeText(Compass.this, "GPS不可用", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(Compass.this, "GPS暂时不可用", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_AVAILABLE:
                    Toast.makeText(Compass.this, "GPS可用啦", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 显示经纬度到控件
     * @param lat 纬度
     * @param lon 经度
     * */
    private void showGPS(double lat, double lon) {
        DecimalFormat df = new DecimalFormat("######0.0");
        latitudeText.setText(df.format(lat) + "");
        longitudeText.setText(df.format(lon) + "");
    }

    @Override
    /**
    监听传感器传回的数据
    * */
    protected void onResume(){
        super.onResume();
        //注册传感器
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    /**
     * 取消监听
     */
    protected void onStop(){
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    /**
     * 计算方向
     * */
    private float calculateOrientation(){
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R,null,accelerometerValues,magneticValues);
        SensorManager.getOrientation(R,values);
        values[0] = (float)Math.toDegrees(values[0]);
        return -values[0];
    }

    @Override
    /**
     * 当传感器的值改变时的回调方法
     * */
    public void onSensorChanged(SensorEvent event){
        //获取传感器类型
        int type = event.sensor.getType();
        switch (type){
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerValues = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magneticValues = event.values;
                break;
                default:
                    break;
        }
        //获得旋转度数
        float degree = calculateOrientation();
        //创建旋转动画
        RotateAnimation ra = new RotateAnimation(currentDegree, degree,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //动画持续时间
        ra.setDuration(200);
        //运行动画
        compassImg.startAnimation(ra);
        currentDegree = degree;
    }

    @Override
    /**
     * 当传感器精度发生改变时的回调方法
     * */
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    protected void onPause(){
        super.onPause();
        //终止定位
        gpsLocationManager.stop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
