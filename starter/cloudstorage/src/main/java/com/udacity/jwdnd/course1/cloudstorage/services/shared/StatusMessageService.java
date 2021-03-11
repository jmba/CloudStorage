package com.udacity.jwdnd.course1.cloudstorage.services.shared;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StatusMessageService {
    public enum MessageType {
        NOTES, FILES, CREDENTIALS
    }

    private HashMap<MessageType, List<String>> statusMessages;

    public StatusMessageService() {
        this.statusMessages = new HashMap<>();
        this.statusMessages.put(MessageType.NOTES, new ArrayList<>());
        this.statusMessages.put(MessageType.FILES, new ArrayList<>());
        this.statusMessages.put(MessageType.FILES, new ArrayList<>());
    }

    public void addMessage(MessageType type, String message) {
        this.statusMessages.get(type).add(message);
    }

    public List<String> getStatusMessages(MessageType type) {
        return statusMessages.get(type);
    }
}
