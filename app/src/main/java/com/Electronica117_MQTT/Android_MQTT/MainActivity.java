package com.Electronica117_MQTT.Android_MQTT;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.Electronica117_MQTT.electronica117_mqtt.Interfaces.MQTTListener;
import com.Electronica117_MQTT.electronica117_mqtt.MQTT;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}