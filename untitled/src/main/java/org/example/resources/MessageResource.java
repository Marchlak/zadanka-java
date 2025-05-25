package org.example.resources;

import org.example.model.Message;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MessageResource {
    private static Map<Long, Message> messages = new HashMap<>();
    private static AtomicLong nextId = new AtomicLong(4);

    static {
        messages.put(1L, new Message(1, "aha60", "Kamil Kowal"));
        messages.put(2L, new Message(2, "aha32", "Dawid Jasper"));
        messages.put(3L, new Message(3, "aha25", "Kamil Amrah"));
    }

    public Map<Long, Message> getMessages() {
        return messages;
    }

    public Message getMessage(Long id) {
        return messages.get(id);
    }

    public Message createMessage(Message message) {
        long id = nextId.getAndIncrement();
        message.setId(id);
        message.setCreated(new Date());
        messages.put(id, message);
        return message;
    }

    public Message updateMessage(Message message) {
        messages.put(message.getId(), message);
        return message;
    }

    public Message deleteMessage(Long id) {
        return messages.remove(id);
    }


    public List<Message> getAllMessagesStartingWith(String prefix) {
        return messages.values().stream()
            .filter(m -> m.getMessage().toLowerCase().startsWith(prefix.toLowerCase()))
            .collect(Collectors.toList());
    }


}
