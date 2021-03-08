package com.udacity.jwdnd.course1.cloudstorage.services.shared;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusMessageService {
    private List<String> statusMessages;

    public StatusMessageService() {
        this.statusMessages = new ArrayList<>();
    }

    public void addMessage(String message) {
        this.statusMessages.add(message);
    }

    public List<String> getStatusMessages() {
        return statusMessages;
    }
}
