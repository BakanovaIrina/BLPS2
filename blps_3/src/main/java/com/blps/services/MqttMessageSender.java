package com.blps.services;

import com.blps.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MqttMessageSender {

    @Autowired
    private MqttClient mqttClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(String topic, Message message) {
        /*
        try {
            String payload = objectMapper.writeValueAsString(message);
            MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException | IOException e) {
            e.printStackTrace();
        }

         */

        try {
            MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.connect();

            String message1 = "ex";
            MqttMessage mqttMessage = new MqttMessage(message1.getBytes());
            mqttMessage.setQos(2); // Уровень качества обслуживания (QoS)
            client.publish(topic, mqttMessage);

            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}


