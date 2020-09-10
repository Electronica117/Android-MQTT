package com.Electronica117_MQTT.Android_MQTT;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.Electronica117_MQTT.electronica117_mqtt.Interfaces.MQTTListener;
import com.Electronica117_MQTT.electronica117_mqtt.MQTT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MQTT.ConnectMQTT(new MQTTListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "NO Conectado", Toast.LENGTH_SHORT).show();
            }
        });

    }
}