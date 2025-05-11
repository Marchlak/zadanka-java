package org.example.resources;

import org.example.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageResource {
    private List<Message> messages = new ArrayList<>();

    public MessageResource() {
        messages.add(new Message(1, "aha60", "Kamil Kowal"));
        messages.add(new Message(2, "aha32", "Dawid Jasper"));
        messages.add(new Message(3, "aha25", "Kamil Amrah"));
    }

    public List<Message> getAllMessages() {
        return messages;
    }
}