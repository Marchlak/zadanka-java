package org.example.demo.resources;

import org.example.demo.model.Comment;
import org.example.demo.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageService {
    private static final MessageService INSTANCE = new MessageService();
    private final Map<Long, Message> messages = new HashMap<>();
    private final Map<Long, Message> securedMessages = new HashMap<>();
    private final Map<Long, List<Comment>> comments = new HashMap<>();
    private long messId = 4;

    public MessageService() {
        messages.put(1L, new Message(1L, "Pierwsza wiadomość", "Mateusz"));
        messages.put(2L, new Message(2L, "Druga wiadomość", "Marek"));
        messages.put(3L, new Message(3L, "Trzecia wiadomość", "Ewa"));
        messages.put(4L, new Message(4L, "Tradycjna wiadomość", "Jakub"));

        securedMessages.put(1L, new Message(5L, "Zabezpieczona wiadomość", "Mateusz"));

        comments.put(1L, List.of(
                new Comment(1L, "Pierwszy komentarz", "Anna"),
                new Comment(2L, "Drugi komentarz", "Jan")));
        comments.put(2L, List.of(
                new Comment(1L, "Nowy komentarz", "Kuba"),
                new Comment(2L, "Kolejny komentarz", "Kazio")));
    }

    public List<Message> getList() {
        return new ArrayList<>(messages.values());
    }

    public Message getMessage(long id) {
        return messages.get(id);
    }

    public Message createMessage(Message message) {
        message.setId(++messId);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message updatedMessage) {
        if (updatedMessage == null) {
            return null;
        }

        Long id = updatedMessage.getId();
        Message existingMessage = messages.get(id);

        if (existingMessage != null) {
            existingMessage.setMessage(updatedMessage.getMessage());
            existingMessage.setAuthor(updatedMessage.getAuthor());
            return existingMessage;
        }
        return null;
    }

    public Message deleteMessage(long id) {
        return messages.remove(id);
    }

    public List<Message> getAllMessagesStartingWith(String prefix) {
        String lowerPrefix = prefix.toLowerCase();
        return messages.values().stream()
                .filter(msg -> msg.getMessage().toLowerCase().startsWith(lowerPrefix))
                .collect(Collectors.toList());
    }

    public List<Comment> getComments(long messageId) {
        return comments.getOrDefault(messageId, new ArrayList<>());
    }

    public List<Message> getSecuredMessages() {
        return new ArrayList<>(securedMessages.values());
    }

    public static MessageService getInstance() {
        return INSTANCE;
    }
}
