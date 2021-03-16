package com.udacity.jwdnd.course1.cloudstorage.services.shared;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StatusMessageService {
    public enum MessageType {
        SUCCESS, ERROR
    }

    private final HashMap<MessageType, List<String>> statusMessages;

    public StatusMessageService() {
        this.statusMessages = new HashMap<>();
        this.statusMessages.put(MessageType.SUCCESS, new ArrayList<>());
        this.statusMessages.put(MessageType.ERROR, new ArrayList<>());
    }

    public void addMessage(MessageType type, String message) {
        this.statusMessages.get(type).add(message);
    }

    public List<String> getStatusMessages(MessageType type) {
        List<String> returnList = new ArrayList<>(statusMessages.get(type));
        this.statusMessages.put(type, new ArrayList<>());
        return returnList;
    }
}
