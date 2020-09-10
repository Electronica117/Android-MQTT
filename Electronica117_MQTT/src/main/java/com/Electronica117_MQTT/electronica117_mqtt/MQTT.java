package com.Electronica117_MQTT.electronica117_mqtt;

/**
 * Electronica117 - Curso ESP32
 * Edgar Dominguez
 * 27/08/2020
 */

import com.Electronica117_MQTT.electronica117_mqtt.Interfaces.MQTTCallbackListener;
import com.Electronica117_MQTT.electronica117_mqtt.Interfaces.MQTTListener;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MQTT  {

    private static MqttAndroidClient instanciaClientMQTT;
    private static MQTTListener myListener;
    private static MQTTCallbackListener myCallback;
    private static String serverMQTT = "tcp://broker.hivemq.com";
    private static String puertoMQTT = "1883";
    private static int Qos = 2;

    public MQTT( ) {
    }

    private static MqttAndroidClient getClientMQTT() {
        if (instanciaClientMQTT == null) {
            String clientMQTTId = MqttClient.generateClientId();
            instanciaClientMQTT = new MqttAndroidClient(MyContext.getMyContext(), serverMQTT + ":" + puertoMQTT, clientMQTTId);
        }
        return instanciaClientMQTT;
    }

    public static void ConnectMQTT(MQTTListener listener){
        myListener = listener;
        try {
            if (!getClientMQTT().isConnected()){
                IMqttToken token = getClientMQTT().connect();
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        myListener.onSuccess();
                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        myListener.onFailure();
                    }
                });
            }else{
                myListener.onSuccess();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void Disconect(MQTTListener listener) {
        myListener = listener;
        try {
            if (getClientMQTT().isConnected()){
                IMqttToken token = getClientMQTT().disconnect();
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        myListener.onSuccess();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        myListener.onFailure();
                    }
                });
            }else{
                myListener.onSuccess();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void Suscribe(String Topico, MQTTListener listener){
        myListener = listener;
        try {
            if (getClientMQTT().isConnected()){
                IMqttToken token = getClientMQTT().subscribe(Topico, Qos);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        myListener.onSuccess();
                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        myListener.onFailure();
                    }
                });
            }else{
                myListener.onFailure();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void Suscribe(String[] Topico, MQTTListener listener){
        myListener = listener;
        int[] Qos = new int[Topico.length];
        Arrays.fill(Qos, MQTT.Qos);
        try {
            if (getClientMQTT().isConnected()){
                IMqttToken subToken = getClientMQTT().subscribe(Topico, Qos);
                subToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        myListener.onSuccess();
                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        myListener.onFailure();
                    }
                });
            }else{
                myListener.onFailure();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void UnSuscribe(String Topico, MQTTListener listener){
        myListener = listener;
        try {
            if (getClientMQTT().isConnected()){
                IMqttToken token = getClientMQTT().unsubscribe(Topico);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        myListener.onSuccess();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        myListener.onFailure();
                    }
                });
            }else{
                myListener.onFailure();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void Callback(MQTTCallbackListener Callback){
        myCallback = Callback;
        if (getClientMQTT().isConnected()){
            getClientMQTT().setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    myCallback.connectionLost(cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    myCallback.messageArrived(topic, new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }
    }

    public static void Publish(String Topico, String Mensaje, MQTTListener listener){
        myListener = listener;
        byte[] MensajeByte;
        try {
            if (getClientMQTT().isConnected()){
                MensajeByte = Mensaje.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(MensajeByte);
                IMqttDeliveryToken token = getClientMQTT().publish(Topico, MensajeByte, Qos, false);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        myListener.onSuccess();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        myListener.onFailure();
                    }
                });
            }else{
                myListener.onFailure();
            }
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected(){
        return getClientMQTT().isConnected();
    }

    public static void setServer(String server){
        serverMQTT = server;
    }

    public static String getServer() {
        return serverMQTT;
    }

    public static String getPuerto() {
        return puertoMQTT;
    }

    public static void setPuerto(int puerto) {
        puertoMQTT = String.valueOf(puerto);
    }

    public static void setQos(int qos) {
        if ((qos < 0) | (qos > 2)){
            Qos = 0;
        }else{
            Qos = qos;
        }
    }

    public static int getQos() {
        return Qos;
    }
}
