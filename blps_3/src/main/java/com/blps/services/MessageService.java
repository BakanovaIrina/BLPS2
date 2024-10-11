package com.blps.services;

import com.blps.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MqttMessageSender messageSender;

    public void sendMessage( Message message){
        messageSender.sendMessage("my/topic", message);

    }
}
