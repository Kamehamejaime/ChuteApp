package com.example.chuteapp;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class Communications {
    private static String mqttHost = "";
    private static String userId = "";
    private static String topic = "";
    private static String user = "";
    private static String pass = "";

    public MqttClient mqttClient;

    public Communications() {
        try{
            mqttClient = new MqttClient(mqttHost, userId, null);

        }
    }
}