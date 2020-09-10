package com.Electronica117_MQTT.electronica117_mqtt.Interfaces;

public interface MQTTCallbackListener {
    void connectionLost(String cause);
    void messageArrived(String topic, String message);
}
