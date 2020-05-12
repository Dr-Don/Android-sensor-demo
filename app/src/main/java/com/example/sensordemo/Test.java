package com.example.sensordemo;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Test extends Activity {

    private Button button;
    private TextView show;
    private SensorManager sm;
    private StringBuffer str;
    private List<Sensor> allSensors;
    private Sensor s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.button);
        show = (TextView) findViewById(R.id.show);
        button.setOnClickListener(new ButtonListener());
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        allSensors = sm.getSensorList(Sensor.TYPE_ALL);// 获得传感器列表
    }
    class ButtonListener implements OnClickListener {
        public void onClick(View v) {
            str = new StringBuffer();
            str.append("该手机有" + allSensors.size() + "个传感器,分别是:\n");
            for (int i = 0; i < allSensors.size(); i++) {
                s = allSensors.get(i);
                switch (s.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        str.append(i + "加速度传感器");
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        str.append(i + "陀螺仪传感器");
                        break;
                    case Sensor.TYPE_LIGHT:
                        str.append(i + "环境光线传感器");
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        str.append(i + "电磁场传感器");
                        break;
                    case Sensor.TYPE_ORIENTATION:
                        str.append(i + "方向传感器");
                        break;
                    case Sensor.TYPE_PRESSURE:
                        str.append(i + "压力传感器");
                        break;
                    case Sensor.TYPE_PROXIMITY:
                        str.append(i + "距离传感器");
                        break;
                    case Sensor.TYPE_TEMPERATURE:
                        str.append(i + "手机温度传感器");
                        break;
                    case Sensor.TYPE_AMBIENT_TEMPERATURE:
                        str.append(i + "环境温度传感器");
                        break;
                    case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                        str.append(i + "地磁旋转矢量传感器");
                        break;
                    case Sensor.TYPE_GRAVITY:
                        str.append(i + "重力传感器");
                        break;
                    case Sensor.TYPE_GAME_ROTATION_VECTOR:
                        str.append(i + "游戏动作旋转传感器");
                        break;
                    case Sensor.TYPE_RELATIVE_HUMIDITY:
                        str.append(i + "湿度传感器");
                        break;
                    case Sensor.TYPE_LINEAR_ACCELERATION:
                        str.append(i + "线性加速度传感器");
                        break;
                    case Sensor.TYPE_ROTATION_VECTOR:
                        str.append(i + "旋转矢量传感器");
                        break;
                    case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                        str.append(i + "未校准陀螺仪传感器");
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                        str.append(i + "未校准磁力传感器");
                        break;
                    default:
                        str.append(i + "未知传感器");
                        break;
                }
                str.append("\n");
                str.append("设备名称:" + s.getName() + "\n");
                str.append("设备版本:" + s.getVersion() + "\n");
                str.append("通用类型号:" + s.getType() + "\n");
                str.append("设备商名称:" + s.getVendor() + "\n");
                str.append("传感器功耗:" + s.getPower() + "\n");
                str.append("传感器分辨率:" + s.getResolution() + "\n");
                str.append("传感器最大量程:" + s.getMaximumRange() + "\n\n");
            }
            show.setText(str);
        }
    }
}
